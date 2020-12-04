package com.thenewboston.kotlinsdk.home.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.thenewboston.kotlinsdk.BuildConfig
import com.thenewboston.kotlinsdk.GENERAL_ERROR
import com.thenewboston.kotlinsdk.R
import com.thenewboston.kotlinsdk.home.viewmodels.BankViewModel
import com.thenewboston.kotlinsdk.network.apis.ApiClient
import com.thenewboston.kotlinsdk.network.models.BankConfigModel
import kotlinx.android.synthetic.main.bank_overview_fragment.*
import kotlinx.android.synthetic.main.bank_overview_fragment.errorText
import kotlinx.android.synthetic.main.bank_overview_fragment.ip_address
import kotlinx.android.synthetic.main.bank_overview_fragment.node_type
import kotlinx.android.synthetic.main.bank_overview_fragment.port
import kotlinx.android.synthetic.main.bank_overview_fragment.protocol
import kotlinx.android.synthetic.main.bank_overview_fragment.tx_fee
import kotlinx.android.synthetic.main.bank_overview_fragment.version
import kotlinx.android.synthetic.main.bank_overview_fragment.wholeLayout

class BankOverviewFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bank_overview_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = requireActivity().run {
            ViewModelProvider(this).get(BankViewModel::class.java)
        }
        active_bank.text = ApiClient.BANK_URL
        observeLiveData(viewModel)
    }

    private fun observeLiveData(viewModel: BankViewModel) {
        viewModel.liveDataBankOverview.observe(viewLifecycleOwner, Observer {
            Log.d("DATA_BANK", "$it")
            if(it!=null) {
                if (it.second != null) {
                    showData(it.second!!)
                } else {
                    handleError(it.first)
                }
            } else {
                handleError("Overview data is null")
            }
        })
        viewModel.getBankConfig()
    }

    private fun showData(data: BankConfigModel) {
        tx_fee.text = if(data.defaultTransactionFee!=null) data.defaultTransactionFee.toString() else "-"
        port.text = if(data.port!=null) data.port.toString() else "-"
        protocol.text = formatStr(data.protocol)
        ip_address.text = formatStr(data.ipAddress)
        version.text = formatStr(data.version)
        node_type.text = formatStr(data.nodeType)
        bank_network_id.text = formatStr(data.nodeIdentifier)
        bank_acc_no.text = formatStr(data.accountNumber)
        confirmation_services.text = if(data.nConfServices!=null) data.nConfServices.toString() else "-"
    }

    private fun formatStr(data: String?) = if(!data.isNullOrEmpty()) data else "-"

    private fun handleError(err: String?) {
        errorText.text = err ?: GENERAL_ERROR
        wholeLayout.visibility = View.GONE
    }
}
