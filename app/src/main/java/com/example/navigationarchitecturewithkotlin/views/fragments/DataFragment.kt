package com.example.navigationarchitecturewithkotlin.views.fragments

import android.os.Bundle
import com.example.navigationarchitecturewithkotlin.repository.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.navigationarchitecturewithkotlin.R
import kotlinx.android.synthetic.main.fragment_data.view.*

class DataFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.shop_women.setOnClickListener {
            PreferenceManager.getInstance().sort = ""
            view.findNavController().navigate(R.id.moviesFragment, null)
        }
        view.shop_men.setOnClickListener {
            view.findNavController().navigate(R.id.seriesFragment)
        }

    }
}
