package com.teacomputers.task.trending.data.local

import android.app.Application
import com.teacomputers.task.trending.util.Graph

class FavouriteApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}