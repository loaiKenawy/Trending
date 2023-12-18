package com.teacomputers.task.trending.data.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.teacomputers.task.trending.data.model.MediaItem
import com.teacomputers.task.trending.data.remote.RetrofitBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchRepo {

    private var searchResultsLiveData = MutableLiveData<List<MediaItem>>()

    fun search(searchString :String): MutableLiveData<List<MediaItem>> {
        try {
            val service = RetrofitBuilder.getInstance()
            CompositeDisposable().add(
                service!!.search(searchString)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe { it ->
                        if (it != null) {
                            searchResultsLiveData.value = it.results
                        }
                    }
            )
        } catch (e: Exception) {
            Log.e("SearchViewModel", "Repo Error: $e")
        }
        return searchResultsLiveData
    }
}