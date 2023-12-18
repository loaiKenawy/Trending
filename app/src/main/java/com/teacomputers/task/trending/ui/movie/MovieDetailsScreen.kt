package com.teacomputers.task.trending.ui.movie

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.teacomputers.task.trending.R
import com.teacomputers.task.trending.data.model.MediaItem
import com.teacomputers.task.trending.data.model.Movie
import com.teacomputers.task.trending.util.GenreHandler

private lateinit var viewModel: MovieViewModel

@Composable
fun MovieDetailsScreen() {
    viewModel =
        ViewModelProvider(LocalViewModelStoreOwner.current!!)[MovieViewModel::class.java]
    Column {
        MovieViewModel.mMovie.observeAsState().value.let {
            if (it != null) {
                MovieDetails(it, viewModel)
            } else {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun ActionBar(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(5.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = title,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            )
        }

    }

}

@Composable
private fun MovieDetails(movie: Movie, movieViewModel: MovieViewModel) {
    val context = LocalContext.current
    ActionBar(movie.title)
    Column {
        // Movie Image

        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.35f),
            model = LocalContext.current.getString(R.string.image_base_url) + movie.backdrop_path,
            contentDescription = "description"
        ) {
            val state = painter.state
            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                CircularProgressIndicator()
            } else {
                SubcomposeAsyncImageContent()
            }
        }


        //Genera and release date
        Row {
            Text(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .weight(1f),
                text = GenreHandler.genreIdToString(movie.genre_ids),
                textAlign = TextAlign.Start,
                maxLines = 1,
                fontSize = 14.sp
            )
            Text(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .weight(1f),
                textAlign = TextAlign.End,
                maxLines = 1,
                text = "ReleaseDate:" + movie.release_date,
                fontSize = 14.sp
            )
        }

        //Popularity and VoteAverage
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clip(shape = RoundedCornerShape(15.dp))
                .background(Color.LightGray),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(10.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    text = "Popularity",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
                Text(
                    modifier = Modifier
                        .padding(10.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    text = movie.popularity.toString(),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(10.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    text = "Vote",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    modifier = Modifier
                        .padding(10.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    text = movie.vote_average.toString(),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp
                )
            }
        }

        //Overview
        Text(
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp, end = 10.dp),
            text = "Overview",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp),
            text = movie.overview,
            fontSize = 16.sp
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(Color.White),
                onClick = {
                    val res = movieViewModel.insertMovie(movie)
                    Toast.makeText(context, res, Toast.LENGTH_SHORT).show()
                }) {
                Text(
                    modifier = Modifier
                        .background(Color.LightGray)
                        .clip(shape = RoundedCornerShape(20.dp))
                        .fillMaxWidth()
                        .padding(15.dp),
                    text = "Add To Favourite",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }

        }

    }
}
