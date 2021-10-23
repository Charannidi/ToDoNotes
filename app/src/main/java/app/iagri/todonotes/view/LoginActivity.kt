package app.iagri.todonotes.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import app.iagri.todonotes.R
import com.google.android.material.button.MaterialButton

class LoginActivity : AppCompatActivity() {

    lateinit var editTextUserName: EditText
    lateinit var editTextPassword: EditText
    lateinit var buttonLogin: MaterialButton
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var userName: String
    lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        bindView()
        setUpSharedPreference()

        buttonLogin.setOnClickListener {
            userName = editTextUserName.text.toString()
            password = editTextPassword.text.toString()
            println("userName: $userName")
            println("password: $password")
            if (userName.isNotEmpty() && password.isNotEmpty()) {
                val intent = Intent(this@LoginActivity, NotesActivity::class.java)
                intent.putExtra("userName", userName)
                startActivity(intent)
                //Login
                saveLoginStatus()
                saveUserName(userName)
            } else {
                Toast.makeText(this@LoginActivity, "Please fill username and password", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun saveUserName(userName: String) {
        editor = sharedPreferences.edit()
        editor.putString("userName", userName)
        editor.apply()
    }

    private fun saveLoginStatus() {
        editor = sharedPreferences.edit()
        editor.putBoolean("is_Logged_in", true)
        editor.apply()
    }

    private fun bindView() {
        editTextUserName = findViewById(R.id.editTextUserName)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
    }

    private fun setUpSharedPreference() {
        sharedPreferences = getSharedPreferences("Charan", MODE_PRIVATE)
    }


}