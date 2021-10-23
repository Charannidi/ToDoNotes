package app.iagri.todonotes.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import app.iagri.todonotes.R

class DetailedActivity : AppCompatActivity() {

    lateinit var textViewTitle: TextView
    lateinit var textViewDescription: TextView
    lateinit var title: String
    lateinit var description: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)
        bindView()
        setUpIntent()
    }

    private fun setUpIntent() {
        val intent = intent
        title = intent.getStringExtra("title").toString()
        description = intent.getStringExtra("description").toString()
        textViewTitle.text = title
        textViewDescription.text = description
    }

    private fun bindView() {
        textViewTitle = findViewById(R.id.textViewTitle)
        textViewDescription = findViewById(R.id.textViewDescription)
    }
}