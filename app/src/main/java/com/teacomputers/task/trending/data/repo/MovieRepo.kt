package com.teacomputers.task.trending.data.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.teacomputers.task.trending.data.model.Movie
import com.teacomputers.task.trending.data.remote.RetrofitBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieRepo {

    private var mMoviesLiveData = MutableLiveData<List<Movie>>()


    fun getMovies(): MutableLiveData<List<Movie>> {
        try {
            val service = RetrofitBuilder.getInstance()
            CompositeDisposable().add(
                service!!.getTrendingMovies()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe { it ->
                        if (it != null) {
                            mMoviesLiveData.value = it.results
                        }
                    }
            )
        } catch (e: Exception) {
            Log.e("MovieViewModel", "Repo Error: $e")
        }
        return mMoviesLiveData
    }


}