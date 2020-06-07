package com.example.navigationarchitecturewithkotlin.model

import com.google.gson.annotations.SerializedName

data class DataModel(@SerializedName("results")var sampleDataInner: List<MovieData>) {

}