package com.example.hackernews.ui.main.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernews.R
import com.example.hackernews.data.model.Post
import com.example.hackernews.databinding.ItemPostBinding
import com.example.hackernews.ui.main.holder.PostViewHolder

class PostAdapter(private val setupClickCallback: (post : Post) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var posts: MutableList<Post>



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ItemPostBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            viewType,
            parent,
            false
        )
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PostViewHolder -> {
                holder.bind(posts[position])
                holder.binding.cardView.setOnClickListener {
                    setupClickCallback(posts[position])
                    Log.e("TAG","clicked")
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int = R.layout.item_post


    override fun getItemCount(): Int =
        if (::posts.isInitialized) posts.size else 0

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(posts: MutableList<Post>) {
        this.posts = posts
        notifyDataSetChanged()
    }
}