package com.teacomputers.task.trending.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Items")
data class MediaItem(
    @PrimaryKey
    val id: Long,
    val backdrop_path: String,
    val title: String,
    val overview: String,
    val poster_path: String,
    val genre_ids: Array<Int>,
    val popularity: Double,
    val release_date: String,
    val vote_average: Double,
    val vote_count: Int,
    val media_type: String
) {
    companion object {
        fun convertToMovieObject(mediaItem: MediaItem): Movie {
            return Movie(
                mediaItem.id,
                mediaItem.backdrop_path,
                mediaItem.title,
                mediaItem.overview,
                mediaItem.poster_path,
                mediaItem.genre_ids,
                mediaItem.popularity,
                mediaItem.release_date,
                mediaItem.vote_average,
                mediaItem.vote_count,
                mediaItem.media_type
            )
        }
        fun convertToSeriesObject(mediaItem: MediaItem): Series {
            return Series(
                mediaItem.id,
                mediaItem.backdrop_path,
                mediaItem.title,
                mediaItem.overview,
                mediaItem.poster_path,
                mediaItem.genre_ids,
                mediaItem.popularity,
                mediaItem.release_date,
                mediaItem.vote_average,
                mediaItem.vote_count,
                mediaItem.media_type
            )
        }

        fun convertMovieToMediaItem(movie:Movie) :MediaItem{
            return MediaItem(
               movie.id,
               movie.backdrop_path,
               movie.title,
               movie.overview,
               movie.poster_path,
               movie.genre_ids,
               movie.popularity,
               movie.release_date,
               movie.vote_average,
               movie.vote_count,
               movie.media_type
            )
        }

        fun convertSeriesToMediaItem(series:Series) :MediaItem{
            return MediaItem(
                series.id,
                series.backdrop_path,
                series.name,
                series.overview,
                series.poster_path,
                series.genre_ids,
                series.popularity,
                series.release_date,
                series.vote_average,
                series.vote_count,
                series.media_type
            )
        }
    }

}