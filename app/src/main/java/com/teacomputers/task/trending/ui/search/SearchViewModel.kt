package com.teacomputers.task.trending.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teacomputers.task.trending.data.model.MediaItem
import com.teacomputers.task.trending.data.repo.SearchRepo

class SearchViewModel : ViewModel() {
    var searchString = MutableLiveData<String>()
    private val mSearchRepo = SearchRepo()
    var searchResultsList = MutableLiveData<List<MediaItem>>()

    fun search() {
        searchResultsList = searchString.value?.let { mSearchRepo.search(it) }!!
    }
}