package com.teacomputers.task.trending.data.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.teacomputers.task.trending.data.local.FavouriteDao
import com.teacomputers.task.trending.data.model.MediaItem
import com.teacomputers.task.trending.data.model.Movie
import com.teacomputers.task.trending.util.Constants
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FavouriteRepo(private val favouriteDao: FavouriteDao) {

    private val favList = MutableLiveData<List<MediaItem>>()

    fun addItemToFavourite(item: MediaItem): Int {
        return try {
            Completable.fromRunnable {
                favouriteDao.insertItem(item)
            }.subscribeOn(Schedulers.io()).subscribe()
            Constants.SAVED_SUCCESSFULLY
        } catch (e: Exception) {
            Log.e("FavouriteRepo", "Repo Error: $e")
            Constants.SAVING_FAILED
        }
    }

    fun getAllFavourite(): MutableLiveData<List<MediaItem>> {
        try {
            CompositeDisposable().add(
                favouriteDao.getAllFavourite()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe { it ->
                        if (it != null) {
                            favList.value = it
                        }
                    }
            )

        } catch (e: Exception) {
            Log.e("FavouriteRepo", "Repo Error: $e")
        }

        return favList
    }

}