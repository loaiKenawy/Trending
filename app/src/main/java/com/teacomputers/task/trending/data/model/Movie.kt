package com.teacomputers.task.trending.data.model

data class Movie(
    val id: Long,
    val backdrop_path: String,
    val title: String,
    val overview: String,
    val poster_path: String,
    val genre_ids: Array<Int>,
    val popularity: Double,
    val release_date: String,
    val vote_average: Double,
    val vote_count: Int,
    val media_type: String
)
