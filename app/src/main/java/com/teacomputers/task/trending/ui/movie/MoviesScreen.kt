package com.teacomputers.task.trending.ui.movie

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
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

private var genreFilter = -1

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
    val openDialog = remember { mutableStateOf(false) }
    val actionChecked = remember {
        mutableStateOf(false)
    }
    val comedyChecked = remember {
        mutableStateOf(false)
    }
    val familyChecked = remember {
        mutableStateOf(false)
    }
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
            IconButton(onClick = {
                openDialog.value = !openDialog.value
            }) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .fillMaxHeight()
                        .align(Alignment.CenterVertically),
                    imageVector = Icons.Outlined.FilterAlt,
                    contentDescription = "Filter"
                )
            }
            Box {
                val popupWidth = 300.dp
                val popupHeight = 300.dp
                if (openDialog.value) {
                    Popup(
                        alignment = Alignment.TopCenter,
                        properties = PopupProperties()
                    ) {
                        Box(
                            Modifier
                                .size(popupWidth, popupHeight)
                                .padding(top = 5.dp)
                                .background(Color.LightGray, RoundedCornerShape(10.dp))
                                .border(1.dp, color = Color.Black, RoundedCornerShape(10.dp))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Row {
                                    Text(
                                        text = "Action",
                                        color = Color.White,
                                        modifier = Modifier
                                            .padding(vertical = 5.dp)
                                            .weight(1f),
                                        fontSize = 16.sp
                                    )
                                    Checkbox(checked = actionChecked.value, onCheckedChange = {
                                        openDialog.value = false
                                        comedyChecked.value = false
                                        familyChecked.value = false
                                        genreFilter = 28
                                    })
                                }
                                Row {
                                    Text(
                                        text = "Comedy",
                                        color = Color.White,
                                        modifier = Modifier
                                            .padding(vertical = 5.dp)
                                            .weight(1f),
                                        fontSize = 16.sp
                                    )
                                    Checkbox(checked = comedyChecked.value, onCheckedChange = {
                                        //35
                                        openDialog.value = false
                                        actionChecked.value = false
                                        familyChecked.value = false
                                        genreFilter = 35
                                    })
                                }
                                Row {
                                    Text(
                                        text = "Family",
                                        color = Color.White,
                                        modifier = Modifier
                                            .padding(vertical = 5.dp)
                                            .weight(1f),
                                        fontSize = 16.sp
                                    )
                                    Checkbox(checked = false, onCheckedChange = {
                                        openDialog.value = false
                                        actionChecked.value = false
                                        comedyChecked.value = false
                                        genreFilter = 10751
                                    })
                                }

                            }
                        }
                    }
                }
            }
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

        if (genreFilter != -1) {
            var tempList = emptyList<Movie>()
            movies.forEach {
                if (it.genre_ids.any { it == genreFilter }) {
                    tempList = movies.filter { movie -> movie.genre_ids.any { it == genreFilter } }
                }
            }
            if (tempList.isNotEmpty()) {
                items(tempList) { movie ->
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
        } else {
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
}


