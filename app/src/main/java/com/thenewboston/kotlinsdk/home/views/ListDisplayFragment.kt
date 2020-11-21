package com.thenewboston.kotlinsdk.home.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonElement
import com.thenewboston.kotlinsdk.*
import com.thenewboston.kotlinsdk.home.utils.ListRecyclerViewAdapter
import com.thenewboston.kotlinsdk.home.viewmodels.BankViewModel
import com.thenewboston.kotlinsdk.utils.TinyDB
import com.thenewboston.kotlinsdk.home.viewmodels.ProfileViewModel
import com.thenewboston.kotlinsdk.home.viewmodels.ValidatorViewModel
import com.thenewboston.kotlinsdk.network.models.GenericListDataModel
import kotlinx.android.synthetic.main.list_display_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListDisplayFragment(
    private val currentPage: Int,
    private val type: String
) : Fragment() {

    private var profileViewModel: ProfileViewModel? = null
    private var bankViewModel: BankViewModel? = null
    private var validatorViewModel: ValidatorViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_display_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar.visibility = View.VISIBLE

        handleLoading()
        when(currentPage) {
            VALIDATOR_PAGE -> {
                validatorViewModel = requireActivity().run {
                    ViewModelProvider(this)[ValidatorViewModel::class.java]
                }
                handleValidatorsPage(type)
            }
            BANK_PAGE -> {
                bankViewModel = requireActivity().run {
                    ViewModelProvider(this)[BankViewModel::class.java]
                }
                handleBankPage(type)
            }
            PROFILE_PAGE -> {
                profileViewModel = requireActivity().run {
                    ViewModelProvider(this)[ProfileViewModel::class.java]
                }
                profileViewModel!!.accountNumber.observe(viewLifecycleOwner, Observer {
                    if(it!=null) {
                        recView.adapter = null
                        handleProfilePageDataFlow(type, accountNumber = it)
                    }
                })
            }
        }

        recView.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun handleProfilePageDataFlow(type: String, offset: Int = LIST_DATA_OFFSET_DEFAULT, accountNumber: String?) {
        val viewModel = profileViewModel!!
        if(accountNumber!=null) {
            CoroutineScope(IO).launch {
            val data = when(type) {
                    TRANSACTIONS -> viewModel.getAccountTransactions(accountNumber, offset = offset)
                    else -> null
                }
                handleDataDisplayAndUpdate(data?.second as GenericListDataModel?, data?.first ?: resources.getString(R.string.err_something_went_wrong), type)
            }
        } else {
            handleError(resources.getString(R.string.err_no_acc_no))
        }

    }

    private fun handleBankPage(type: String, offset: Int = LIST_DATA_OFFSET_DEFAULT) {
        val viewModel = bankViewModel!!
        CoroutineScope(IO).launch {
            val data = when(type) {
                ACCOUNTS -> viewModel.getBankAccounts(offset = offset)
                else -> null
            }
            handleDataDisplayAndUpdate(data?.second as GenericListDataModel?, data?.first ?: resources.getString(R.string.err_something_went_wrong), type)
        }
    }

    private fun handleValidatorsPage(type: String, offset: Int = LIST_DATA_OFFSET_DEFAULT) {
        val primaryValidator = TinyDB.getDataFromLocal(requireContext(), PRIMARY_VALIDATOR)
        val viewModel = validatorViewModel!!
        if(primaryValidator!=null) {
            CoroutineScope(IO).launch {
                val data= when(type) {
                    ACCOUNTS -> viewModel.getValidatorAccounts(offset = offset)
                    else -> null
                }
                handleDataDisplayAndUpdate(data?.second as GenericListDataModel?, data?.first ?: resources.getString(R.string.err_something_went_wrong), type)
            }
        } else {
            handleError(resources.getString(R.string.err_no_primary_validator))
        }
    }

    /* UTILS */
    // Format : .....?limit=30&offset=30
    private fun getOffsetFromLink(link: String?, total: Int, err: String): Int? {
        return if(link != null) {
            val tillNow =  link.split("offset=")[1].toInt()
            progress_text.text = "1-$tillNow of $total"
            tillNow
        } else {
            progress_text.text = if(total == 0) err else "1-$total of $total"
            null
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun handleDataLoadMore(dataUpdate: ArrayList<JsonElement>, nextOffset: Int?, type: String) {
        if(recView.adapter == null) {
            recView.adapter = ListRecyclerViewAdapter(
                requireContext(),
                dataUpdate,
                nextOffset,
                currentPage,
                type
            ) {
                when(currentPage) {
                    BANK_PAGE -> handleBankPage(type, it)
                    VALIDATOR_PAGE -> handleValidatorsPage(type, it)
                    PROFILE_PAGE -> handleProfilePageDataFlow(type, it, profileViewModel?.accountNumber?.value)
                }
            }
        } else {
            // update data and offset
            (recView.adapter as ListRecyclerViewAdapter).updateData(dataUpdate, nextOffset)
        }

    }

    private suspend fun handleDataDisplayAndUpdate(
        data: GenericListDataModel?,
        err: String,
        type: String
    ) {
        withContext(Main) {
            if (data!=null) {
                handleShowData()
                handleDataLoadMore(
                    data.results,
                    getOffsetFromLink(data.next, data.count, err),
                    type
                )
            } else {
                handleError(err)
            }
        }
    }

    private fun handleError(err: String) {
        head_layout.visibility = View.GONE
        errorDisp.text = err
        errorDisp.visibility = View.VISIBLE
        recView.visibility = View.GONE
        progressBar.visibility = View.GONE
    }

    private fun handleLoading() {
        head_layout.visibility = View.GONE
        errorDisp.visibility = View.GONE
        recView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun handleShowData() {
        head_layout.visibility = View.VISIBLE
        errorDisp.visibility = View.GONE
        recView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }
}
