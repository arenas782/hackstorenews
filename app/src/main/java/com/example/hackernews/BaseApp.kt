package com.example.hackernews

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApp  : Application(){
    companion object {

        lateinit var instance: BaseApp

        val appContext: Context by lazy {
            instance.applicationContext
        }
    }
    override fun onCreate() {
        super.onCreate()

        instance = this
    }
}
