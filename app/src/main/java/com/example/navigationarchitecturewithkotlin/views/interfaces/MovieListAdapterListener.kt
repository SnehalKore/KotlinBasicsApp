package com.example.navigationarchitecturewithkotlin.views.interfaces

import com.example.navigationarchitecturewithkotlin.model.MovieData


interface MovieListAdapterListener {
    fun onRowClick(movie: MovieData)
}
