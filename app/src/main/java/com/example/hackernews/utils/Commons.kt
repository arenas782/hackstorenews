package com.example.hackernews.utils


import android.content.Context
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import com.example.hackernews.BaseApp
import com.example.hackernews.R
import com.google.android.material.snackbar.Snackbar


import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

object Commons {



    fun showSnackBar(title: String, view : View, type : SnackType){
        val snack = Snackbar.make(view,title, Snackbar.LENGTH_LONG)
        when (type){
            SnackType.ERROR ->{
                snack.view.setBackgroundColor(getColor(R.color.red_500))
            }
            SnackType.SUCCESS ->{
                snack.view.setBackgroundColor(getColor(R.color.green_600))
            }
        }
        snack.view.z = 5000f
        snack.show()
    }

    fun getColor(color: Int): Int = BaseApp.appContext.getColor(color)



    fun millisToDate(date : Long) : String{
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val netDate = Date(date * 1000)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }

    fun getString(string: Int): String = BaseApp.appContext.getString(string)

    fun getString(string: Int, value: String): String =
        BaseApp.appContext.getString(string, value)

    fun getString(string: Int, value: String, value2: String): String =
        BaseApp.appContext.getString(string, value, value2)

    enum class SnackType {
        SUCCESS,
        ERROR
    }
}