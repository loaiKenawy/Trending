package com.teacomputers.task.trending.data.model

import com.google.gson.annotations.SerializedName

data class Series(
    val id: Long,
    val backdrop_path: String,
    val name: String,
    val overview: String,
    val poster_path: String,
    val genre_ids: Array<Int>,
    val popularity: Double,
    @SerializedName("first_air_date")
    val release_date: String,
    val vote_average: Double,
    val vote_count: Int,
    val media_type: String
)
