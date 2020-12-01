package com.thenewboston.kotlinsdk.home.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.thenewboston.kotlinsdk.ACCOUNT_NO
import com.thenewboston.kotlinsdk.R
import com.thenewboston.kotlinsdk.utils.TinyDB
import com.thenewboston.kotlinsdk.home.viewmodels.ProfileViewModel
import com.thenewboston.kotlinsdk.utils.ViewUtils
import kotlinx.android.synthetic.main.profile_overview_fragment.*

class ProfileOverviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_overview_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = requireActivity().run {
            ViewModelProvider(this).get(ProfileViewModel::class.java)
        }

        copy.setOnClickListener {
            if(acc_no.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Nothing to copy", Toast.LENGTH_SHORT).show()
            } else {
                ViewUtils.copyText(requireActivity(), acc_no.text.toString())
            }
        }

        var accNo = TinyDB.getDataFromLocal(requireContext(), ACCOUNT_NO)
        viewModel.getAccountBalance(accNo ?: "")
        viewModel.accountBalanceLiveData.observe(viewLifecycleOwner, Observer {
            accNo = TinyDB.getDataFromLocal(requireContext(), ACCOUNT_NO)
            balance.text = it.toString()
        })
        if(accNo == null) {
            AccountNumberEditDialog(
                requireContext(),
                "",
                {updateAccNo(viewModel, it)},
                false
            ).show()
            acc_no.text = "-"
            balance.text = "-"
        } else {
            acc_no.text = accNo
        }
        edit_acc_no.setOnClickListener {
            AccountNumberEditDialog(
                requireContext(),
                accNo ?: "",
                {updateAccNo(viewModel, it)}
            ).show()
        }
    }

    private fun updateAccNo(viewModel: ProfileViewModel, accNo: String) {
        viewModel.updateAccountNumber(requireContext(), accNo)
        acc_no.text = accNo
    }
}
