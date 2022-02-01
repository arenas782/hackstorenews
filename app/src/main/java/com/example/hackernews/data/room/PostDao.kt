package com.example.hackernews.data.room

import androidx.room.*
import com.example.hackernews.data.model.Post

@Dao
interface PostDao {
    @Query("SELECT * FROM post")
    suspend fun getAllPosts(): List<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post : Post)

    @Delete
    suspend fun deletePost(post : Post)

    @Query("DELETE FROM post")
    suspend fun clearPosts()


}