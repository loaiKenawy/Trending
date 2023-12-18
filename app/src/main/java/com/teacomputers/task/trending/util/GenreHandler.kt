package com.teacomputers.task.trending.util

object GenreHandler {
    fun genreIdToString(ids: Array<Int>) :String {
        var genresString = ""
        ids.forEach {
            when (it) {
                28 -> {
                    genresString += "Action"
                }
                12 -> {
                    genresString += "Adventure"
                }
                16 -> {
                    genresString += "Animation"
                }
                35 -> {
                    genresString += "Comedy"
                }

                80 -> {
                    genresString += "Crime"
                }

                99 -> {
                    genresString += "Documentary"
                }

                18 -> {
                    genresString += "Drama"
                }

                10751 -> {
                    genresString += "Family"
                }

                14 -> {
                    genresString += "Fantasy"
                }
                36 -> {
                    genresString += "History"
                }
                27 -> {
                    genresString += "Horror"
                }
                10402 -> {
                    genresString += "Music"
                }
                9648 -> {
                    genresString += "Mystery"
                }
                10749 -> {
                    genresString += "Romance"
                }
                878 -> {

                    genresString += "Science Fiction"
                }

                10770 -> {
                    genresString += "TV Movie"
                }

                53 -> {
                    genresString += "Thriller"
                }

                10752 -> {
                    genresString += "War"
                }
                37 -> {
                    genresString += "Western"
                }
            }
        }
        return genresString
    }

}
