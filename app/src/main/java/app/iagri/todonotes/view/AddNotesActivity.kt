package app.iagri.todonotes.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import app.iagri.todonotes.BuildConfig
import app.iagri.todonotes.R
import com.google.android.material.textfield.TextInputEditText
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class AddNotesActivity : AppCompatActivity(),onOptionClickListener {

    lateinit var editTextTitle: TextInputEditText
    lateinit var editTextDescription: TextInputEditText
    lateinit var buttonSubmit: Button
    lateinit var imageViewAdd: ImageView
    private var latestTmpUri: Uri? = null
    var picturePath = ""
    lateinit var imageLocation: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)
        bindView()
        clickListener()
    }

    private fun clickListener() {
        buttonSubmit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (editTextTitle.text.toString().isNotEmpty() && editTextDescription.text.toString().isNotEmpty()){
                    val intent = Intent()
                    intent.putExtra("title", editTextTitle.text.toString())
                    intent.putExtra("description", editTextDescription.text.toString())
                    intent.putExtra("imagepath", picturePath)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                } else{
                    Toast.makeText(this@AddNotesActivity,getString(R.string.empty_string_error),Toast.LENGTH_SHORT).show()
                }
            }

        })


        imageViewAdd.setOnClickListener {
           // setupDialog()
            openPicker()
        }
    }

    private fun openPicker() {
        val dialog = FileSelectorFragment.newInstance()
        dialog.show(supportFragmentManager, FileSelectorFragment.TAG)
    }

    private fun setupDialog() {
        val view = LayoutInflater.from(this@AddNotesActivity).inflate(R.layout.dialog_selector, null)
        val textViewCamera = view.findViewById<TextView>(R.id.textViewCamera)
        val textViewGallery = view.findViewById<TextView>(R.id.textViewGallery)
        val dialog = AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(true)
                .create()
        textViewCamera.setOnClickListener {


        }
        textViewGallery.setOnClickListener {

        }
        dialog.show()
    }

    private fun takeImage() {

        lifecycleScope.launchWhenStarted {
            getTempFileUri().let { uri ->
                latestTmpUri = uri
                takeImageResult.launch(uri)
            }
        }
    }

    private fun getTempFileUri(): Uri {
        val timeStamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        val fileName = "JPEG_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val tempFile = File.createTempFile(fileName, ".jpg", storageDir).apply {
            createNewFile()
            //deleteOnExit()
        }
        imageLocation = tempFile
        System.out.println("imageLocation: "+imageLocation)
        return FileProvider.getUriForFile(applicationContext, "${BuildConfig.APPLICATION_ID}.provider", tempFile)
    }

    private fun bindView() {
        editTextTitle = findViewById(R.id.editTextTitle)
        editTextDescription = findViewById(R.id.editTextDescription)
        buttonSubmit = findViewById(R.id.buttonSubmit)
        imageViewAdd = findViewById(R.id.imageviewAdd)
    }

    private val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            picturePath = uri?.path.toString()
            System.out.println("selectImageFromGalleryResult:"+picturePath)
            imageViewAdd.setImageURI(uri)
        }
    }

    private val takeImageResult = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            latestTmpUri?.let { uri ->
                picturePath = imageLocation.path.toString()
                imageViewAdd.setImageURI(uri)
            }
        }
    }

    override fun onCameraClick() {
        takeImage()
    }

    override fun onGalleryClick() {
        selectImageFromGalleryResult.launch("image/*")
    }


}
interface onOptionClickListener {
    fun onCameraClick()
    fun onGalleryClick()
}