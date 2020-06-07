package com.example.navigationarchitecturewithkotlin.views.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navigationarchitecturewithkotlin.R
import com.example.navigationarchitecturewithkotlin.databinding.FragmentPopularMoviesBinding
import com.example.navigationarchitecturewithkotlin.model.MovieData
import com.example.navigationarchitecturewithkotlin.repository.PreferenceManager
import com.example.navigationarchitecturewithkotlin.repository.network.NetworkState
import com.example.navigationarchitecturewithkotlin.utils.ConnectionDetector
import com.example.navigationarchitecturewithkotlin.utils.Constants
import com.example.navigationarchitecturewithkotlin.viewModel.MovieListViewModel
import com.example.navigationarchitecturewithkotlin.views.activity.MainActivity
import com.example.navigationarchitecturewithkotlin.views.adapter.LoadSampleDataAdapter
import com.example.navigationarchitecturewithkotlin.views.interfaces.MovieListAdapterListener

class MoviesFragment : Fragment() {
    private val MOVIE_ID = "movie_id"
    private var sampleDataViewModel: MovieListViewModel?=null
    private lateinit var fragmentPopularMoviesBinding: FragmentPopularMoviesBinding
    private lateinit var loadSampleData: LoadSampleDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentPopularMoviesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_popular_movies, container, false)
        if (sampleDataViewModel == null) {
            PreferenceManager.getInstance().sort=""
            sampleDataViewModel = ViewModelProviders.of(this).get(MovieListViewModel::class.java)
            getMovieList()
        }
        return fragmentPopularMoviesBinding.root
    }

    private fun getMovieList() {
        if (activity != null) {
            (activity as MainActivity).showProgress()
        }
        sampleDataViewModel!!.getData(PreferenceManager.getInstance().sort!!, ConnectionDetector.networkStatus(context!!))

        // Create the observer which updates the UI.
        val nameObserver = Observer<List<MovieData>> { sampleData ->
            loadSampleData = LoadSampleDataAdapter(sampleData)
            fragmentPopularMoviesBinding.recyclerView.itemAnimator = DefaultItemAnimator()
            fragmentPopularMoviesBinding.recyclerView.layoutManager = LinearLayoutManager(context)
            fragmentPopularMoviesBinding.recyclerView.adapter = loadSampleData
            loadSampleData.setListener(object : MovieListAdapterListener {
                override fun onRowClick(movie: MovieData) {
                    val bundle = Bundle()
                    bundle.putString(MOVIE_ID, movie.id)
                    view?.findNavController()?.navigate(R.id.movieDetailFragment, bundle)

                }
            })
            (activity as MainActivity).hideProgress()
        }

        val networkState = Observer<NetworkState> { networkState ->
            if (networkState.status === NetworkState.Status.FAILED) {
                Toast.makeText(context, "Network call failed", Toast.LENGTH_LONG).show()
            }
        }
        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        sampleDataViewModel!!.networkState?.observe(this, networkState)
        sampleDataViewModel!!.sampleData?.observe(this, nameObserver)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.filter_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onResume() {
        super.onResume()
        when (PreferenceManager.getInstance().sort) {
            Constants.SORT_BY_DATE -> {
                PreferenceManager.getInstance().sort = getString(R.string.release_date_sort)
                fragmentPopularMoviesBinding.header.setText(R.string.sort_by_date_title)
            }
            Constants.SORT_BY_RATING -> {
                PreferenceManager.getInstance().sort = getString(R.string.vote_avg_sort)
                fragmentPopularMoviesBinding.header.setText(R.string.vote_average)
            }
            Constants.CLEAR -> {
                PreferenceManager.getInstance().sort = ""
                fragmentPopularMoviesBinding.header.setText(R.string.popular_movies)
            }
        }
        getMovieList()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (ConnectionDetector.networkStatus(context!!)) {
            if (item.itemId == R.id.home) {
                findNavController().popBackStack()
                return true
            } else if (item.itemId == R.id.action_filter) {
                view?.findNavController()?.navigate(R.id.sortByFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
