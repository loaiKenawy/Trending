package com.teacomputers.task.trending.data.model

import com.google.gson.annotations.SerializedName

class SeriesResponse(
    @SerializedName("results")
    val results: List<Series>
)