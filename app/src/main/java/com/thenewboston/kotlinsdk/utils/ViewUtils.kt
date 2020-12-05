package com.thenewboston.kotlinsdk.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast

class ViewUtils {
    companion object {
        fun copyText(activity: Activity, text: String) {
            val clipboard: ClipboardManager = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("text", text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(activity.applicationContext, "Copied to clipboard", Toast.LENGTH_SHORT).show()
        }
    }
}
