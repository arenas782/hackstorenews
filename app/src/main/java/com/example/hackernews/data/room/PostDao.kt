package com.example.hackernews.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hackernews.data.model.Post

@Dao
interface PostDao {
    @Query("SELECT * FROM post")
    suspend fun getAllPosts(): List<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post : Post)
}