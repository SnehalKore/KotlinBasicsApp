package com.example.navigationarchitecturewithkotlin.repository.network


import com.example.navigationarchitecturewithkotlin.model.DataModel
import com.example.navigationarchitecturewithkotlin.model.MovieData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("/3/discover/movie?")
    fun getMovieList(@Query("api_key") apiKey: String, @Query("sort_by") sortBy: String): Call<DataModel>

    @GET("/3/movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: String, @Query("api_key") apiKey: String): Call<MovieData>

}
