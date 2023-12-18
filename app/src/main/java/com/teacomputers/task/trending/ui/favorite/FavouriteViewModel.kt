package com.teacomputers.task.trending.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teacomputers.task.trending.data.model.MediaItem
import com.teacomputers.task.trending.data.repo.FavouriteRepo
import com.teacomputers.task.trending.util.Graph

class FavouriteViewModel(private val repo: FavouriteRepo = Graph.repository) : ViewModel() {

    var favList = MutableLiveData<List<MediaItem>>()
    fun getAllFavourite() {
        favList = repo.getAllFavourite()
    }

}