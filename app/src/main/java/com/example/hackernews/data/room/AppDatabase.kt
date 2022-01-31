package com.example.hackernews.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hackernews.data.model.Post

@Database(entities = [Post::class], version = 7)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDato(): PostDao

    companion object {
        const val DATABASE_NAME : String = "news_db"
    }
}