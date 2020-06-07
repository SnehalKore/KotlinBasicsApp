package com.example.navigationarchitecturewithkotlin.views.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.navigationarchitecturewithkotlin.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun showProgress() {
        if (progress_bar_main != null) {
            progress_bar_main.visibility = View.VISIBLE
        }
    }

    fun hideProgress() {
        if (progress_bar_main != null) {
            progress_bar_main.visibility = View.GONE
        }
    }
}
