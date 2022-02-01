package com.example.hackernews.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class Post(
    @PrimaryKey(autoGenerate = false)
    @field:Json(name = "objectID")
    val objectID: String,

    @field:Json(name = "story_url")
    val story_url: String? = null,

    @field:Json(name = "url")
    val url: String? = null,

    @field:Json(name = "author")
    val author: String? = null,

    @field:Json(name = "created_at_i")
    val created_at_i: Long? = null,

    @field:Json(name = "story_title")
    val story_title: String? = null,

    @field:Json(name = "title")
    val title: String? = null,

    val dismissed: Int = 0,
)
