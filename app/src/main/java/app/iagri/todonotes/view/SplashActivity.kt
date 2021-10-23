package app.iagri.todonotes.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import app.iagri.todonotes.R
import app.iagri.todonotes.onboarding.OnBoardingActivity
import app.iagri.todonotes.utils.PrefConstant
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class SplashActivity: AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var handler: Handler
    lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setUpSharedPreferences()
        goToNext()
        getFCMToken()
    }

    private fun goToNext() {
       Handler(Looper.getMainLooper()).postDelayed({
           checkLoginStatus()
       },2000)
    }

    private fun getFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("SplashActivity", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            if (token != null) {
                Log.d("SplashActivity", token)
            }
            Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
        })
    }

    private fun checkLoginStatus() {
        val isLoggedIn: Boolean = sharedPreferences.getBoolean("is_Logged_in",false)
        val isBoardingSuccessfully = sharedPreferences.getBoolean(PrefConstant.ON_BOARDING_SUCCESSFULLY,false)
        if (isLoggedIn) {
            //NotesActivity
            val intent = Intent(this@SplashActivity, NotesActivity::class.java)
            startActivity(intent)
        }else {
            //MainActivity
                if (isBoardingSuccessfully){
                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(intent)
                } else{
                    val intent = Intent(this@SplashActivity, OnBoardingActivity::class.java)
                    startActivity(intent)
                }

        }
        finish()
    }

    private fun setUpSharedPreferences() {
        sharedPreferences = getSharedPreferences("Charan", MODE_PRIVATE)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        handler.removeCallbacks(runnable)
    }
}