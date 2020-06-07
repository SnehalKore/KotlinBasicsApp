package com.example.navigationarchitecturewithkotlin

import android.app.Application
import com.example.navigationarchitecturewithkotlin.repository.AppExecutors
import com.example.navigationarchitecturewithkotlin.repository.NavRepository
import com.example.navigationarchitecturewithkotlin.repository.PreferenceManager
import com.example.navigationarchitecturewithkotlin.repository.database.AppDatabase
import com.example.navigationarchitecturewithkotlin.repository.network.ApiClient
import com.example.navigationarchitecturewithkotlin.repository.network.ApiInterface

class NavigationArchApplication : Application() {

    private var mAppExecutors: AppExecutors? = null

    private val apiInterface: ApiInterface
        get() = ApiClient.client!!.create(ApiInterface::class.java)

    private fun getDatabase(): AppDatabase {
        return AppDatabase.getInstance(this, mAppExecutors!!)
    }

    val appRepository: NavRepository?
        get() = NavRepository.getInstance(getDatabase(), apiInterface, mAppExecutors!!)

    override fun onCreate() {
        super.onCreate()
        mAppExecutors = AppExecutors()
        PreferenceManager.getInstance().initPreferences(this)
    }

}
