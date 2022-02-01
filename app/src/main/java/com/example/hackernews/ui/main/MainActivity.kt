package com.example.hackernews.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.hackernews.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.progress_bar

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mutableMainProgress.observe(this) {
            progress_bar.visibility = it
            Log.e("Progress bar ","$it")
        }
    }
    companion object{
        val mutableMainProgress = MutableLiveData(View.VISIBLE)
    }
}