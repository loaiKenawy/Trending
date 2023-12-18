package com.teacomputers.task.trending.data.model

import com.google.gson.annotations.SerializedName

class MediaItemsResponse(
    @SerializedName("results")
    val results: List<MediaItem>
)