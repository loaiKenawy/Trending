package com.teacomputers.task.trending.ui.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teacomputers.task.trending.data.model.MediaItem
import com.teacomputers.task.trending.data.model.Movie
import com.teacomputers.task.trending.data.repo.FavouriteRepo
import com.teacomputers.task.trending.data.repo.MovieRepo
import com.teacomputers.task.trending.util.Constants
import com.teacomputers.task.trending.util.Graph

class MovieViewModel(private val repo: FavouriteRepo = Graph.repository) : ViewModel() {


    //Remote
    private val mMovieRepo = MovieRepo()
    var mMoviesList = MutableLiveData<List<Movie>>()

    fun getTrendingMovies() {
        mMoviesList = mMovieRepo.getMovies()
    }

    //Local
    fun insertMovie(movie: Movie): String {

        val res = repo.addItemToFavourite(MediaItem.convertMovieToMediaItem(movie))
        return if (res == Constants.SAVED_SUCCESSFULLY) {
            "Movie Added To Favourite"
        } else {
            "Ops, Can't Add this"
        }
    }

    companion object {
        var mMovie = MutableLiveData<Movie>()
    }
}