package com.example.navigationarchitecturewithkotlin.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.navigationarchitecturewithkotlin.model.MovieData
import com.example.navigationarchitecturewithkotlin.repository.network.NetworkState

class MovieDetailsViewModel(application: Application): BaseViewModel(application) {

    var movie: LiveData<MovieData>? = null
    var networkState: LiveData<NetworkState>? = null

    fun getMovieDetailResponse(movieID: String, isConnected: Boolean) {
        movie = repository?.getMovieDetail(movieID, isConnected)
        networkState = repository?.getNetworkState()
    }

}