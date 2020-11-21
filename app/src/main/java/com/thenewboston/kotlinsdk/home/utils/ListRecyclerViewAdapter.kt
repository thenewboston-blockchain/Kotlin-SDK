package com.thenewboston.kotlinsdk.home.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.thenewboston.kotlinsdk.*
import com.thenewboston.kotlinsdk.network.models.BankAccountsDataModel
import com.thenewboston.kotlinsdk.network.models.ProfileTransactionDataModel
import com.thenewboston.kotlinsdk.network.models.ValidatorAccountDataModel
import com.thenewboston.kotlinsdk.network.models.ValidatorBankDataModel
import kotlinx.android.synthetic.main.bank_account_individual_view.view.*
import kotlinx.android.synthetic.main.profile_account_transaction_individual_view.view.*
import kotlinx.android.synthetic.main.validator_account_individual_view.view.*
import kotlinx.android.synthetic.main.validator_account_individual_view.view.acc_no
import kotlinx.android.synthetic.main.validator_bank_individual_view.view.*
import kotlinx.android.synthetic.main.validator_bank_individual_view.view.trust
import java.lang.NullPointerException

class ListRecyclerViewAdapter(
    private val context: Context,
    private val data: ArrayList<JsonElement>,
    private var nextOffset: Int?,
    private var page: Int,
    private var type: String,
    private val loadMore: ((Int) -> Unit)
): RecyclerView.Adapter<ListRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            try {
                LayoutInflater.from(parent.context!!).inflate(
                    getViewForList(page, type)!!,
                    null
                )
            } catch (e: NullPointerException) { View(context) }
        )
    }

    override fun getItemCount() = data.size

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        setDataForList(page, type, holder, position)
        // Load More at 10 fields before reaching end
        if(position == data.size - 10 && nextOffset!=null) {
            loadMore(nextOffset!!)
        }
    }

    // handle the individual views for different layouts
    private fun getViewForList(currentPage: Int, type: String): Int? {
        return when(currentPage) {
            BANK_PAGE -> {
                when(type) {
                    ACCOUNTS -> R.layout.bank_account_individual_view
                    else -> null
                }
            }
            VALIDATOR_PAGE -> {
                when(type) {
                    ACCOUNTS -> R.layout.validator_account_individual_view
                    BANKS -> R.layout.validator_bank_individual_view
                    else -> null
                }
            }
            PROFILE_PAGE -> {
                when(type) {
                    TRANSACTIONS -> R.layout.profile_account_transaction_individual_view
                    else -> null
                }
            }
            else -> null
        }
    }

    // set data
    private fun setDataForList(currentPage: Int, type: String, holder: ViewHolder, position: Int) {
        when(currentPage) {
            BANK_PAGE -> {
                when(type) {
                    ACCOUNTS -> handleAccountsViewsBank(
                        Gson().fromJson<ArrayList<BankAccountsDataModel>>(data),
                        holder, position
                    )
                }
            }
            VALIDATOR_PAGE -> {
                when(type) {
                    ACCOUNTS -> handleAccountNumberViewsValidator(
                        Gson().fromJson<ArrayList<ValidatorAccountDataModel>>(data),
                        holder, position
                    )
                    BANKS -> handleBankViewsValidator(
                        Gson().fromJson<ArrayList<ValidatorBankDataModel>>(data),
                        holder, position
                    )
                }
            }
            PROFILE_PAGE -> {
                when (type) {
                    TRANSACTIONS -> handleTransactionViewsProfile(
                        Gson().fromJson<ArrayList<ProfileTransactionDataModel>>(data),
                        holder, position
                    )
                }
            }
        }
    }

    // to parse from json
    private inline fun <reified T> Gson.fromJson(json: ArrayList<JsonElement>) = fromJson<T>(toJson(json), object : TypeToken<T>() {}.type)

    // update data for load more
    fun updateData(dataNew: ArrayList<JsonElement>, nextOffset_: Int?) {
        dataNew.forEach {
            data.add(it)
        }
        nextOffset = nextOffset_
        notifyDataSetChanged()
    }

    /* Banks */
    private fun handleAccountsViewsBank(data: ArrayList<BankAccountsDataModel>, holder: ViewHolder, position: Int) {
        val accView = holder.itemView
        val accData = data[position]
        accView.id_.text = accData.id
        accView.acc_no.text = accData.accountNumber
        accView.trust.text = accData.trust
        accView.created.text = accData.createdDate
        accView.modified.text = accData.modifiedDate
    }

    /* Validators */
    private fun handleAccountNumberViewsValidator(data: ArrayList<ValidatorAccountDataModel>, holder: ViewHolder, position: Int) {
        val accNoView = holder.itemView
        val accountData = data[position]
        accNoView.acc_no.text = accountData.accountNumber
        accNoView.balance.text = "${accountData.balance}"
        accNoView.balance_lock.text = accountData.balanceLock
    }

    private fun handleBankViewsValidator(data: ArrayList<ValidatorBankDataModel>, holder: ViewHolder, position: Int) {
        val bankView = holder.itemView
        val bankData = data[position]
        bankView.ip_addr.text = "${bankData.protocol}://${bankData.ipAddress}:${bankData.port ?: "-"}"
        bankView.tx_fee.text = "${bankData.defaultTransactionFee}"
        bankView.acc_no.text = bankData.accountNumber
        bankView.nid.text = bankData.nodeIdentifier
        bankView.conf_exp.text = "${bankData.confirmationExpiration}"
        bankView.trust.text = bankData.trust
    }

    /* Profile */
    private fun handleTransactionViewsProfile(data: ArrayList<ProfileTransactionDataModel>, holder: ViewHolder, position: Int) {
        val transactionsView = holder.itemView
        val transactionsData = data[position]
        transactionsView.indicator.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_received))
        transactionsView.amount.text = "${transactionsData.amount}"
        transactionsView.sender.text = transactionsData.block.sender
        transactionsView.recipient.text = transactionsData.recipient
        transactionsView.balance_key.text = transactionsData.block.balanceKey
        transactionsView.sign.text = transactionsData.block.signature
        transactionsView.dateCreated.text = transactionsData.block.createdDate
    }
}
