package com.thenewboston.kotlinsdk.home.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.thenewboston.kotlinsdk.GENERAL_ERROR
import com.thenewboston.kotlinsdk.PRIMARY_VALIDATOR
import com.thenewboston.kotlinsdk.R
import com.thenewboston.kotlinsdk.network.models.ValidatorConfigModel
import com.thenewboston.kotlinsdk.utils.TinyDB
import com.thenewboston.kotlinsdk.home.viewmodels.ValidatorViewModel
import kotlinx.android.synthetic.main.validator_overview_fragment.*

class ValidatorOverviewFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.validator_overview_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = requireActivity().run {
            ViewModelProvider(this).get(ValidatorViewModel::class.java)
        }

        primary_validator.text = TinyDB.getDataFromLocal(requireContext(), PRIMARY_VALIDATOR)

        viewModel.validatorConfigLiveData.observe(viewLifecycleOwner, Observer {
            if(it!=null) {
                if(it.second!=null) {
                    showData(it.second!!)
                } else {
                    handleError(it.first)
                }
            } else {
                handleError("Overview data is null")
            }
        })

        viewModel.getValidatorConfig()

    }

    private fun showData(data: ValidatorConfigModel) {
        tx_fee.text = if(data.defaultTransactionFee!=null) data.defaultTransactionFee.toString() else "-"
        port.text = if(data.port!=null) data.port.toString() else "-"
        daily_rate.text = if(data.dailyConfirmationRate!=null) data.dailyConfirmationRate.toString() else "-"
        protocol.text = formatStr(data.protocol)
        ip_address.text = formatStr(data.ipAddress)
        version.text = formatStr(data.version)
        root_account_file.text = formatStr(data.rootAccountFile)
        root_account_file_hash.text = formatStr(data.rootAccountFileHash)
        seed_block_identifier.text = formatStr(data.seedBlockIdentifier)
        node_type.text = formatStr(data.nodeType)
        validator_network_id.text = formatStr(data.nodeIdentifier)
        validator_acc_no.text = formatStr(data.accountNumber)
    }

    private fun handleError(err: String?) {
        errorText.text = err ?: GENERAL_ERROR
        wholeLayout.visibility = View.GONE
    }

    private fun formatStr(data: String?) = if(!data.isNullOrEmpty()) data else "-"
}
