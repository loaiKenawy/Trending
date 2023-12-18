package com.teacomputers.task.trending.data.remote

import com.teacomputers.task.trending.data.model.MediaItemsResponse
import com.teacomputers.task.trending.data.model.MovieResponse
import com.teacomputers.task.trending.data.model.SeriesResponse
import com.teacomputers.task.trending.util.Constants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("trending/movie/day?api_key=${Constants.API_KEY}")
    fun getTrendingMovies(): Observable<MovieResponse>

    @GET("trending/tv/day?api_key=${Constants.API_KEY}")
    fun getTrendingSeries(): Observable<SeriesResponse>

    @GET("search/multi?api_key=${Constants.API_KEY}")
    fun search(@Query("query") searchString: String): Observable<MediaItemsResponse>
}