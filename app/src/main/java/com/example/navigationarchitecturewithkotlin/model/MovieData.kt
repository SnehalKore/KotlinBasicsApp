package com.example.navigationarchitecturewithkotlin.model

import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.navigationarchitecturewithkotlin.R
import com.google.gson.annotations.SerializedName
import com.squareup.picasso.Picasso

@Entity
data class MovieData(@SerializedName("id") @NonNull @PrimaryKey @ColumnInfo(name = "ID") val id: String,
                     @SerializedName("vote_count") @ColumnInfo(name = "Vote_Count") val vote_count: String,
                     @SerializedName("vote_average") @ColumnInfo(name = "Vote_Average") val vote_average: String,
                     @SerializedName("title") @ColumnInfo(name = "Title") val title: String,
                     @SerializedName("poster_path") @ColumnInfo(name = "Poster_Path") val poster_path: String?,
                     @SerializedName("adult") @ColumnInfo(name = "Adult") val adult: String,
                     @SerializedName("overview") @ColumnInfo(name = "Overview") val overview: String,
                     @SerializedName("release_date") @ColumnInfo(name = "Release_Date") val release_date: String) {

    companion object DataBindingAdapter {
        @BindingAdapter("bind:poster_path")
        @JvmStatic
        fun loadImage(imageView: ImageView, poster_path: String?) {
                Picasso.get().load("http://image.tmdb.org/t/p/w185/$poster_path").fit()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(imageView)
        }
    }
}
