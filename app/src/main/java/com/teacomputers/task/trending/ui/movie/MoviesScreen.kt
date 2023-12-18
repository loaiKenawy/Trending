package com.teacomputers.task.trending.ui.movie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.teacomputers.task.trending.R
import com.teacomputers.task.trending.data.model.Movie
import com.teacomputers.task.trending.ui.navigation.Screens

private lateinit var viewModel: MovieViewModel

@Composable
fun MovieScreen(navController: NavController) {
    viewModel =
        ViewModelProvider(LocalViewModelStoreOwner.current!!)[MovieViewModel::class.java]
    viewModel.getTrendingMovies()
    Column {
        ActionBar()
        viewModel.mMoviesList.observeAsState().value.let {
            if (it.isNullOrEmpty()) {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                RecyclerView(it, navController)
            }

        }
    }
}

@Composable
private fun ActionBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(5.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Row(
            modifier = Modifier.padding(10.dp), horizontalArrangement = Arrangement.End
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .fillMaxHeight()
                    .align(Alignment.CenterVertically),
                imageVector = Icons.Outlined.Movie,
                contentDescription = "Movies"
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp), text = "Movies",
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                textAlign = TextAlign.Start
            )
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .fillMaxHeight()
                    .align(Alignment.CenterVertically),
                imageVector = Icons.Outlined.FilterAlt,
                contentDescription = "Filter"
            )
        }


    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MoviePoster(movie: Movie, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(5.dp),
        onClick = onClick
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = LocalContext.current.getString(R.string.image_base_url) + movie.poster_path,
            contentDescription = "description"
        ) {
            val state = painter.state
            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                CircularProgressIndicator()
            } else {
                SubcomposeAsyncImageContent()
            }
        }
    }


}

@Composable
private fun RecyclerView(movies: List<Movie>, navController: NavController) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        items(movies) { movie ->
            MoviePoster(movie = movie) {
                MovieViewModel.mMovie.value = movie
                navController.navigate(Screens.MovieDetailsScreen.name) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }
        }
    }
}