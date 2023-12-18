package com.teacomputers.task.trending.ui.series

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teacomputers.task.trending.data.model.MediaItem
import com.teacomputers.task.trending.data.model.Series
import com.teacomputers.task.trending.data.repo.FavouriteRepo
import com.teacomputers.task.trending.data.repo.SeriesRepo
import com.teacomputers.task.trending.util.Constants
import com.teacomputers.task.trending.util.Graph

class SeriesViewModel(private val repo: FavouriteRepo = Graph.repository) : ViewModel() {


    private val mSeriesRepo = SeriesRepo()
    var mSeriesList = MutableLiveData<List<Series>>()

    fun getTrendingSeries() {
        mSeriesList = mSeriesRepo.getSeries()
    }

    //Local
    fun insertSeries(series: Series): String {
        val res = repo.addItemToFavourite(MediaItem.convertSeriesToMediaItem(series))
        return if (res == Constants.SAVED_SUCCESSFULLY) {
            "Series Added To Favourite"
        } else {
            "Ops, Can't Add this"
        }
    }


    companion object {
        var mSeries = MutableLiveData<Series>()
    }
}