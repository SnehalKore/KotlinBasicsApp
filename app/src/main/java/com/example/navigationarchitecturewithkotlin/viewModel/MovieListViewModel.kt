package com.example.navigationarchitecturewithkotlin.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.navigationarchitecturewithkotlin.model.MovieData
import com.example.navigationarchitecturewithkotlin.repository.network.NetworkState

class MovieListViewModel(application: Application) : BaseViewModel(application) {

    var sampleData: LiveData<List<MovieData>>? = null
    var networkState: LiveData<NetworkState>? = null

    fun getData(sortBy:String,isConnected: Boolean) {
        sampleData = repository!!.getMovieList(sortBy,isConnected)
        networkState = repository?.getNetworkState()
    }

}
