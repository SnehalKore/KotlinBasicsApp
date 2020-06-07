package com.example.navigationarchitecturewithkotlin.views.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.navigationarchitecturewithkotlin.R
import com.example.navigationarchitecturewithkotlin.databinding.FragmentMovieDetailsBinding
import com.example.navigationarchitecturewithkotlin.model.MovieData
import com.example.navigationarchitecturewithkotlin.repository.network.NetworkState
import com.example.navigationarchitecturewithkotlin.utils.ConnectionDetector
import com.example.navigationarchitecturewithkotlin.viewModel.MovieDetailsViewModel
import com.example.navigationarchitecturewithkotlin.views.activity.MainActivity


class MovieDetailsFragment : Fragment() {
    private val MOVIE_ID = "movie_id"
    private lateinit var fragmentMovieDetailsBinding: FragmentMovieDetailsBinding
    private lateinit var movieDetailViewModel: MovieDetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentMovieDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false)
        movieDetailViewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel::class.java)
        makeGetMovieDetailCall()
        return fragmentMovieDetailsBinding.root
    }

    private fun makeGetMovieDetailCall() {
        if (activity != null) {
            (activity as MainActivity).showProgress()
        }
        movieDetailViewModel.getMovieDetailResponse(this.arguments!![MOVIE_ID] as String, ConnectionDetector.networkStatus(this.context!!))
        val nameObserver = Observer<MovieData> { movie ->
            if (movie != null) {
                setData(movie)
            }
            (activity as MainActivity).hideProgress()
        }
        val networkState = Observer<NetworkState> { networkState ->
            if (networkState.status === NetworkState.Status.FAILED) {
                Toast.makeText(context, "Network call failed", Toast.LENGTH_LONG).show()
            }
        }
        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        movieDetailViewModel.movie?.observe(this, nameObserver)
        movieDetailViewModel.networkState?.observe(this, networkState)

    }

    private fun setData(movie: MovieData?) {
        fragmentMovieDetailsBinding.rating.rating = java.lang.Float.parseFloat(movie!!.vote_average) * 5 / 10
        fragmentMovieDetailsBinding.movie = movie
    }
}
