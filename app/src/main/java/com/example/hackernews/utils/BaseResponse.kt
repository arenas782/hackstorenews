package com.example.hackernews.utils

import com.squareup.moshi.Json

data class BaseResponse<T>(

    @field:Json(name = "hits")
    val data: T? = null,

    )

