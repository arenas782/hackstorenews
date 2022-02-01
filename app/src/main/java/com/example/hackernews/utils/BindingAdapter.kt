package com.example.hackernews.utils

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernews.ui.main.adapter.PostAdapter
import com.example.hackernews.ui.main.adapter.SwipeGesture
import com.example.hackernews.ui.main.viewmodel.MainViewModel


@BindingAdapter(value= ["bind:adapter", "bind:viewmodel"],requireAll = true)
fun setAdapter(rv: RecyclerView, mAdapter: RecyclerView.Adapter<*>,vm : MainViewModel) {
    rv.apply {
        setHasFixedSize(true)
        mAdapter as PostAdapter
        val swipeGesture = object : SwipeGesture(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Log.e("TAG","$direction")
                vm.deletePost(viewHolder.adapterPosition)
            }
        }
        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(rv)
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


