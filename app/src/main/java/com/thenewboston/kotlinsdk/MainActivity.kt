package com.thenewboston.kotlinsdk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.thenewboston.kotlinsdk.utils.TinyDB
import com.thenewboston.kotlinsdk.home.viewmodels.BankViewModel
import com.thenewboston.kotlinsdk.home.viewmodels.ProfileViewModel
import com.thenewboston.kotlinsdk.home.viewmodels.ValidatorViewModel
import com.thenewboston.kotlinsdk.home.views.BankFragment
import com.thenewboston.kotlinsdk.home.views.ProfileFragment
import com.thenewboston.kotlinsdk.home.views.ValidatorFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val bankViewModel by viewModels<BankViewModel>()
    private val validatorViewModel by viewModels<ValidatorViewModel>()
    private val profileViewModel by viewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.let {
            it.setDisplayShowHomeEnabled(true)
            it.setIcon(ContextCompat.getDrawable(this,
                R.drawable.logo
            ))
        }

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, ValidatorFragment()).commit()
        bottom_nav.setOnNavigationItemSelectedListener { p0 ->
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                when(p0.itemId) {
                    R.id.banks -> BankFragment()
                    R.id.profile -> ProfileFragment()
                    else -> ValidatorFragment()
                }
            ).commit()
            true
        }
    }

    override fun onStart() {
        super.onStart()
        // get acc no
        profileViewModel.accountNumber.postValue(TinyDB.getDataFromLocal(this, ACCOUNT_NO))
    }

    /* TODO: inject as dependencies later
    private fun initializeBankViewModel() = ViewModelProvider(
            this,
            BankViewModelProvider(
                BankRepoImpl(ApiClient.defaultBankClient.create(BankApis::class.java))
            )
        ).get(BankViewModel::class.java)

    private fun initializeValidatorViewModel() = ViewModelProvider(
            this,
            ValidatorViewModelProvider(
                ValidatorRepoImpl(
                    ApiClient.validatorsClient(
                        TinyDB.getDataFromLocal(this, PRIMARY_VALIDATOR)!!)
                        .create(ValidatorsApi::class.java)
                )
            )
        ).get(ValidatorViewModel::class.java)

    private fun initializeProfileViewModel() = ViewModelProvider(
            this,
            ProfileViewModelProvider(
                ProfileRepoImpl(
                    ApiClient.defaultBankClient.create(BankApis::class.java),
                    ApiClient.validatorsClient(
                        TinyDB.getDataFromLocal(this, PRIMARY_VALIDATOR)!!)
                        .create(ValidatorsApi::class.java)
                )
            )
        ).get(ProfileViewModel::class.java)*/
}
