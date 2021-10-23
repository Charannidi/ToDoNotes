package app.iagri.todonotes.onboarding

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import app.iagri.todonotes.R
import app.iagri.todonotes.utils.PrefConstant
import app.iagri.todonotes.view.LoginActivity

class OnBoardingActivity : AppCompatActivity(), OnBoardingOneFragment.OnNextClick, OnBoardingTwoFragment.OnOptionClick {
    lateinit var viewPager: ViewPager2
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        bindViews()
        setUpSharedPreference()
    }

    private fun setUpSharedPreference() {
        sharedPreferences = getSharedPreferences("Charan", MODE_PRIVATE)
    }

    private fun bindViews() {
        viewPager = findViewById(R.id.viewPager)
        val adapter = FargmentAdapter(this)
        viewPager.adapter = adapter
    }

    override fun onClick() {
        viewPager.currentItem = 1
    }

    override fun onOptionBack() {
        viewPager.currentItem = 0
    }

    override fun onOptionDone() {
        editor = sharedPreferences.edit()
        editor.putBoolean(PrefConstant.ON_BOARDING_SUCCESSFULLY,true)
        val intent = Intent(this@OnBoardingActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}