package com.example.navigationarchitecturewithkotlin.repository

import android.annotation.SuppressLint
import android.content.Context


class PreferenceManager {

    var context: Context? = null

    var sort: String?
        get() {
            val preferences = context!!.getSharedPreferences(SORT_PREF, Context.MODE_PRIVATE)
            return preferences.getString(SORTING, "")
        }
        set(temp) {
            val sharedPreferences = context!!.getSharedPreferences(SORT_PREF, Context.MODE_PRIVATE)
            val sharedPreferencesEditor = sharedPreferences.edit()
            sharedPreferencesEditor.putString(SORTING, temp)
            sharedPreferencesEditor.apply()
        }

    fun initPreferences(context: Context) {
        this.context = context
    }

    companion object {

        private val SORT_PREF = "sort_pref"

        private val SORTING = "sorting"

        @SuppressLint("StaticFieldLeak")
        private var instance: PreferenceManager? = null

        fun getInstance(): PreferenceManager {
            synchronized(PreferenceManager::class.java) {
                if (instance == null) {
                    instance = PreferenceManager()
                }
                return instance as PreferenceManager
            }
        }
    }

}