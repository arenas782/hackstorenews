package com.example.hackernews.data.repository

import android.view.View
import com.example.hackernews.data.api.NewsService
import com.example.hackernews.data.model.Post
import com.example.hackernews.data.room.PostDao
import com.example.hackernews.ui.main.MainActivity
import com.example.hackernews.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class MainRepository @Inject constructor(private val postDao: PostDao, private val newsService: NewsService) {

    suspend fun getNewsByDate(): Flow<Resource<List<Post>>> = flow{
        kotlinx.coroutines.delay(2000)
        emit(Resource.loading(data = null))
        try {
            var  cachedPosts = postDao.getAllPosts()
            if (cachedPosts.isNotEmpty()){
                emit(Resource.success(data = cachedPosts))
            }
            else{
                val remotePosts = newsService.getNewsByDate()
                remotePosts.hits.forEach{
                    postDao.insertPost(it)
                }
                cachedPosts = postDao.getAllPosts()
                emit(Resource.success(data = cachedPosts))
            }
        }catch (e : Exception) {
            emit(Resource.error(data = null, message = e    .message ?: "Error Occurred!"))
        }
    }
}