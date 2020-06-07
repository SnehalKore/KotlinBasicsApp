package com.example.navigationarchitecturewithkotlin.viewModel

import android.app.Application


import com.example.navigationarchitecturewithkotlin.NavigationArchApplication
import com.example.navigationarchitecturewithkotlin.repository.NavRepository

import androidx.lifecycle.AndroidViewModel
import io.reactivex.annotations.NonNull

open class BaseViewModel(@NonNull application: Application) : AndroidViewModel(application) {

    init {
        if (repository == null)
            repository = (application as NavigationArchApplication).appRepository
    }
    companion object {
        var repository: NavRepository? = null
    }

}

