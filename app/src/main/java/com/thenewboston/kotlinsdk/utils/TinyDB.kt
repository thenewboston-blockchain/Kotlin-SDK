package com.thenewboston.kotlinsdk.utils

import android.content.Context
import android.content.SharedPreferences
import com.thenewboston.kotlinsdk.LOCAL_DB_SHARED_PREF

class TinyDB {
    companion object {
        fun saveDataLocally(context: Context, key: String, value: String) {
            val pref: SharedPreferences = context.getSharedPreferences(LOCAL_DB_SHARED_PREF, Context.MODE_PRIVATE)
            pref.edit().putString(key, value).apply()
        }
        fun getDataFromLocal(context: Context, key: String): String? {
            val pref: SharedPreferences = context.getSharedPreferences(LOCAL_DB_SHARED_PREF, Context.MODE_PRIVATE)
            return pref.getString(key, null)
        }
    }
}
