package com.example.hackernews.data.room

import androidx.room.*
import com.example.hackernews.data.model.Post

@Dao
interface PostDao {
    @Query("SELECT * FROM post WHERE dismissed ==0")
    suspend fun getAllPosts(): List<Post>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPost(post : Post)

    @Query("UPDATE post set dismissed = 1 where objectID ==:objectID")
    suspend fun deletePost(objectID : String)

    @Query("DELETE FROM post")
    suspend fun clearPosts()


}