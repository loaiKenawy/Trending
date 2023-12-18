package com.teacomputers.task.trending.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.teacomputers.task.trending.data.model.MediaItem
import io.reactivex.Observable

@Dao
interface FavouriteDao {

    @Upsert
    fun insertItem(item: MediaItem)

    @Query("SELECT * FROM Items")
    fun getAllFavourite(): Observable<List<MediaItem>>

}