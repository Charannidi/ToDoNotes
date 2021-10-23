package app.iagri.todonotes.view

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import app.iagri.todonotes.NotesApp
import app.iagri.todonotes.R
import app.iagri.todonotes.adapter.NotesAdapter
import app.iagri.todonotes.clicklistener.ItemClickListener
import app.iagri.todonotes.db.Notes
import app.iagri.todonotes.workmanager.MyWorker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.concurrent.TimeUnit

class NotesActivity : AppCompatActivity() {


    lateinit var sharedPreferences: SharedPreferences
    lateinit var recyclerViewNotes: RecyclerView
    lateinit var fabAddNotes: FloatingActionButton
    var noteList = ArrayList<Notes>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        bindView()
        setUpSharedPreference()
        saveIntents()
        getDataFromDatabase()
        setUpRecyclerView()
        setupWorkManger()
        fabAddNotes.setOnClickListener {
            openYourActivity()
        }

    }

    private fun setupWorkManger() {
        val constraint = Constraints.Builder()
                .build()
        val request = PeriodicWorkRequest
                .Builder(MyWorker::class.java, 1, TimeUnit.MINUTES)
                .setConstraints(constraint)
                .build()
        WorkManager.getInstance().enqueue(request)
    }

    private fun openYourActivity() {
        val intent = Intent(this@NotesActivity, AddNotesActivity::class.java)
        getResult.launch(intent)
    }


    private fun getDataFromDatabase() {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        noteList.addAll(notesDao.getAll())
    }

    private fun addNotesToDb(notes: Notes) {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesDao.insert(notes)
    }

    private fun setUpRecyclerView() {
        val itemClickListener = object : ItemClickListener {
            override fun onClick(notes: Notes) {
                val intent = Intent(this@NotesActivity, DetailedActivity::class.java)
                intent.putExtra("title", notes.title)
                intent.putExtra("description", notes.description)
                startActivity(intent)
            }

            override fun onUpdate(notes: Notes) {
                val notesApp = applicationContext as NotesApp
                val notesDao = notesApp.getNotesDb().notesDao()
                notesDao.updateNotes(notes)
            }

        }
        val notesAdapter = NotesAdapter(noteList, itemClickListener)
        val linearLayoutManager = LinearLayoutManager(this@NotesActivity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewNotes.layoutManager = linearLayoutManager
        recyclerViewNotes.adapter = notesAdapter
    }

    private fun saveIntents() {
        var userName = ""
        val intent = intent
        if (intent.hasExtra("userName")) {
            userName = intent.getStringExtra("userName").toString()
        }

        if (userName.isEmpty()) {
            userName = sharedPreferences.getString("userName", "xyz").toString()
        }
        supportActionBar?.title = userName
    }

    private fun setUpSharedPreference() {
        sharedPreferences = getSharedPreferences("Charan", MODE_PRIVATE)
    }

    private fun bindView() {
        fabAddNotes = findViewById(R.id.fabAddNotes)
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes)
    }

    private val getResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val titleResult = it.data?.getStringExtra("title")
                    val descriptionResult = it.data?.getStringExtra("description")
                    val imageResult = it.data?.getStringExtra("imagepath")
                    Log.i("findImage", "Image is Found:" + imageResult)
                    val notesApp = applicationContext as NotesApp
                    val notesDao = notesApp.getNotesDb().notesDao()
                    val note = Notes(title = titleResult!!, description = descriptionResult!!, imagePath = imageResult!!)

                    noteList.add(note)
                    notesDao.insert(note)
                    recyclerViewNotes.adapter?.notifyItemChanged(noteList.size - 1)
                }
            }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.blog) {
            Log.d("Click", "Click action done")
            val intent = Intent(this@NotesActivity, BlogActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}