package com.example.hackernews.data.model.response

import com.example.hackernews.data.model.Post
import com.squareup.moshi.Json

data class Posts(
    @field:Json(name = "hits")
    val hits : ArrayList<Post>,

)

