package com.thenewboston.kotlinsdk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.thenewboston.kotlinsdk.network.apis.BankApis
import com.thenewboston.kotlinsdk.network.apis.ApiClient
import com.thenewboston.kotlinsdk.network.apis.ValidatorsApi
import com.thenewboston.kotlinsdk.home.repository.bank.BankRepoImpl
import com.thenewboston.kotlinsdk.home.repository.profile.ProfileRepoImpl
import com.thenewboston.kotlinsdk.home.repository.validator.ValidatorRepoImpl
import com.thenewboston.kotlinsdk.utils.TinyDB
import com.thenewboston.kotlinsdk.home.viewModelProviders.BankViewModelProvider
import com.thenewboston.kotlinsdk.home.viewModelProviders.ProfileViewModelProvider
import com.thenewboston.kotlinsdk.home.viewModelProviders.ValidatorViewModelProvider
import com.thenewboston.kotlinsdk.home.viewmodels.BankViewModel
import com.thenewboston.kotlinsdk.home.viewmodels.ProfileViewModel
import com.thenewboston.kotlinsdk.home.viewmodels.ValidatorViewModel
import com.thenewboston.kotlinsdk.home.views.BankFragment
import com.thenewboston.kotlinsdk.home.views.ProfileFragment
import com.thenewboston.kotlinsdk.home.views.ValidatorFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var bankViewModel: BankViewModel
    private lateinit var validatorViewModel: ValidatorViewModel
    private lateinit var profileViewModel: ProfileViewModel

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
        bankViewModel = initializeBankViewModel()
        validatorViewModel = initializeValidatorViewModel()
        profileViewModel = initializeProfileViewModel()
        // get acc no
        profileViewModel.accountNumber.postValue(TinyDB.getDataFromLocal(this, ACCOUNT_NO))
    }

    /* TODO: inject as dependencies later */
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
        ).get(ProfileViewModel::class.java)
}
