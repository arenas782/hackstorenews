package com.example.hackernews.data.repository

import android.util.Log
import com.example.hackernews.BaseApp
import com.example.hackernews.data.api.NewsService
import com.example.hackernews.data.model.Post
import com.example.hackernews.data.room.PostDao
import com.example.hackernews.utils.Commons
import com.example.hackernews.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class MainRepository @Inject constructor(private val postDao: PostDao, private val newsService: NewsService) {

    suspend fun getNewsByDate(): Flow<Resource<List<Post>>> = flow{
        emit(Resource.loading(data = null))
        try {

            if (Commons.isOnline(BaseApp.appContext)){
                val remotePosts = newsService.getNewsByDate()
                remotePosts.hits.forEach{
                    Log.e("POST",it.toString())
                    postDao.insertPost(it)
                }
            }
            val posts = postDao.getAllPosts()
                emit(Resource.success(data = posts))
        }catch (e : Exception) {
            emit(Resource.error(data = null, message = e    .message ?: "Error Occurred!"))
        }
    }

    suspend fun deletePost(post : Post) {
        try {
            postDao.deletePost(post.objectID)
        }catch (e : Exception){
            Log.e("TAG", "$e.localizedMessage")
        }
    }
}