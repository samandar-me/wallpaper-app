package com.sdk.wallpaperapp.presentation.activity.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.viewModels
import com.sdk.wallpaperapp.R
import com.sdk.wallpaperapp.presentation.activity.main.MainActivity
import com.sdk.wallpaperapp.presentation.activity.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sp)

        initViews()
    }

    private fun initViews() {
        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                if (viewModel.isUserExists()) {
                    val intent = Intent(this@SpActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this@SpActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }.start()
    }
}