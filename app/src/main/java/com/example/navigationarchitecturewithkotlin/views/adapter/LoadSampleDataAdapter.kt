package com.example.navigationarchitecturewithkotlin.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.navigationarchitecturewithkotlin.R
import com.example.navigationarchitecturewithkotlin.databinding.SampleRowBinding
import com.example.navigationarchitecturewithkotlin.model.MovieData
import com.example.navigationarchitecturewithkotlin.views.interfaces.MovieListAdapterListener


class LoadSampleDataAdapter() : RecyclerView.Adapter<LoadSampleDataAdapter.MyViewHolder>() {
    private var movieList: List<MovieData>? = null
    private var layoutInflater: LayoutInflater? = null
    private var listener: MovieListAdapterListener? = null

    constructor(movieList: List<MovieData>) : this() {
        this.movieList = movieList
    }

    inner class MyViewHolder(val binding: SampleRowBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val binding = DataBindingUtil.inflate<SampleRowBinding>(layoutInflater!!, R.layout.sample_row, parent, false)
        binding.callback = listener
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.movieList = movieList?.get(position)
    }

    override fun getItemCount(): Int {
        return movieList!!.size
    }

    fun setListener(listener: MovieListAdapterListener) {
        this.listener = listener
    }

}