package com.teacomputers.task.trending.data.model

import com.google.gson.annotations.SerializedName

class MovieResponse(
    @SerializedName("results")
    val results: List<Movie>
)