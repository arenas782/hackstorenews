package com.example.hackernews.data.api

import com.example.hackernews.data.model.Post
import com.example.hackernews.data.model.response.Posts
import com.example.hackernews.utils.BaseResponse
import retrofit2.http.*


interface   NewsService {
    @GET("search_by_date?query=mobile")
    suspend fun getNewsByDate(): Posts
}
