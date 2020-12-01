package com.thenewboston.kotlinsdk.splash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.thenewboston.kotlinsdk.MainActivity
import com.thenewboston.kotlinsdk.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Splash : AppCompatActivity() {
    private val viewModel by viewModels<SplashViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
    }

    override fun onStart() {
        super.onStart()
        viewModel.savedPrimaryValidator.observe(this, Observer {
            if(it!=null) {
                if(it) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@Splash, "Something went wrong, try again.", Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.getPrimaryValidatorId(this)
    }
}
