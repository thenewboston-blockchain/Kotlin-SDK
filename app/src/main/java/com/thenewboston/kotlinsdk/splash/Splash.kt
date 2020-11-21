package com.thenewboston.kotlinsdk.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thenewboston.kotlinsdk.MainActivity
import com.thenewboston.kotlinsdk.PRIMARY_VALIDATOR
import com.thenewboston.kotlinsdk.R
import com.thenewboston.kotlinsdk.home.repository.bank.BankRepo
import com.thenewboston.kotlinsdk.home.repository.bank.BankRepoImpl
import com.thenewboston.kotlinsdk.network.apis.ApiClient
import com.thenewboston.kotlinsdk.network.apis.BankApis
import com.thenewboston.kotlinsdk.splash.repo.SplashRepoImpl
import com.thenewboston.kotlinsdk.utils.TinyDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
    }

    override fun onStart() {
        super.onStart()
        val viewModel = initSplashViewModel()
        val i = Intent(this, MainActivity::class.java)
        if(TinyDB.getDataFromLocal(this, PRIMARY_VALIDATOR)!=null) {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(i)
                finish()
            }, 500)
        } else {
            CoroutineScope(IO).launch {
                val ip = viewModel.getPrimaryValidatorId()
                withContext(Main) {
                    if(ip!=null) {
                        TinyDB.saveDataLocally(this@Splash, PRIMARY_VALIDATOR, ip)
                        startActivity(i)
                        finish()
                    } else Toast.makeText(this@Splash, "Something went wrong, try again.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /* TODO: inject as dependency later */
    private fun initSplashViewModel() = ViewModelProvider(
        this,
        SplashViewModelProvider(
            SplashRepoImpl(ApiClient.defaultBankClient.create(BankApis::class.java))
        )
    ).get(SplashViewModel::class.java)
}

@Suppress("UNCHECKED_CAST")
class SplashViewModelProvider(
    private val repo: SplashRepoImpl
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SplashViewModel(repo) as T
    }
}
