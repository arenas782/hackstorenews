package com.example.hackernews.ui.main.holder

import androidx.recyclerview.widget.RecyclerView
import com.example.hackernews.data.model.Post
import com.example.hackernews.databinding.ItemPostBinding


class PostViewHolder(val binding : ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post : Post) {
        binding.item = post
        binding.executePendingBindings()
    }
}