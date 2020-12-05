package com.thenewboston.kotlinsdk.home.views

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.thenewboston.kotlinsdk.R
import kotlinx.android.synthetic.main.edit_acc_no_dialog.*

class AccountNumberEditDialog(
    private val ctx : Context,
    private val accNo: String,
    private val updateAccNo : ((String) -> Unit)?,
    private val cancellable: Boolean = false
) : Dialog(ctx) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_acc_no_dialog)
        close.setOnClickListener { dismiss() }
        acc_no_edit.setText(accNo)
        setCancelable(cancellable)
        save.setOnClickListener {
            if (acc_no_edit.text.trim().isNotEmpty()) {
                updateAccNo?.let { updateAccNo!!(acc_no_edit.text.toString()) }
                dismiss()
            } else {
                Toast.makeText(ctx, "Please enter an acc no.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
