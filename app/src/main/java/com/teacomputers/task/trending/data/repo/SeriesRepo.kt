package com.teacomputers.task.trending.data.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.teacomputers.task.trending.data.model.Series
import com.teacomputers.task.trending.data.remote.RetrofitBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SeriesRepo {

    private var mSeriesLiveData = MutableLiveData<List<Series>>()


    fun getSeries(): MutableLiveData<List<Series>> {
        try {
            val service = RetrofitBuilder.getInstance()
            CompositeDisposable().add(
                service!!.getTrendingSeries()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe { it ->
                        if (it != null) {
                            mSeriesLiveData.value = it.results
                        }
                    }
            )
        } catch (e: Exception) {
            Log.e("SeriesViewModel", "Repo Error: $e")
        }
        return mSeriesLiveData
    }
}