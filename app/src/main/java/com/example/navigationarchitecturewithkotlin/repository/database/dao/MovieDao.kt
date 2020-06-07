package com.example.navigationarchitecturewithkotlin.repository.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.example.navigationarchitecturewithkotlin.model.MovieData


@Dao
interface MovieDao {

    companion object {
        const val tableName = "MovieData"
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<MovieData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movies: MovieData)

    @Query("SELECT * FROM $tableName")
    fun fetchAllMovies(): List<MovieData>

    @Query("SELECT * FROM $tableName WHERE ID = :movieID")
    fun fetchMovieDetails(movieID: String): MovieData

}
