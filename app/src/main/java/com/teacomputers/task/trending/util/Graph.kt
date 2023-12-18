package com.teacomputers.task.trending.util

import android.content.Context
import com.teacomputers.task.trending.data.local.FavouriteDatabase
import com.teacomputers.task.trending.data.repo.FavouriteRepo

object Graph {
    lateinit var favDatabase: FavouriteDatabase
        private set

    val repository by lazy {
        FavouriteRepo(favDatabase.favDao())
    }

    fun provide(context: Context) {
        favDatabase = FavouriteDatabase.getDatabase(context)
    }

}



