package com.example.navigationarchitecturewithkotlin.views.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.navigationarchitecturewithkotlin.R
import com.example.navigationarchitecturewithkotlin.databinding.FragmentSortByBinding
import com.example.navigationarchitecturewithkotlin.repository.PreferenceManager
import com.example.navigationarchitecturewithkotlin.utils.Constants


class SortByFragment : Fragment() {

    private lateinit var fragmentSortByBinding: FragmentSortByBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentSortByBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sort_by, container, false)
        fragmentSortByBinding.dateSort.setOnClickListener { navigateBack(Constants.SORT_BY_DATE) }
        fragmentSortByBinding.rateSort.setOnClickListener { navigateBack(Constants.SORT_BY_RATING) }
        fragmentSortByBinding.clearAll.setOnClickListener { navigateBack(Constants.CLEAR) }
        return fragmentSortByBinding.root
    }

    private fun navigateBack(sortBy: String) {
        PreferenceManager.getInstance().sort = sortBy
       findNavController().popBackStack()
    }
}
