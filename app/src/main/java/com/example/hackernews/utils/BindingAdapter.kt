package com.example.hackernews.utils

import android.annotation.SuppressLint
import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView


@BindingAdapter("adapter")
fun setAdapter(rv: RecyclerView, mAdapter: RecyclerView.Adapter<*>) {
    rv.apply {
        setHasFixedSize(true)
        adapter = mAdapter
    }
}

@BindingAdapter(value= ["bind:story_title", "bind:title"],requireAll = true)
fun setTitle(tv: TextView,story_title : String?, title: String?) {
    if (story_title.isNullOrEmpty())
        tv.text = title
    else
        tv.text = story_title
}

@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("date")
fun date(tv: TextView, date : Long) {
    tv.text = Commons.millisToDate(date)
}


