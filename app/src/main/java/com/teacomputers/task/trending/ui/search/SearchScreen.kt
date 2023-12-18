package com.teacomputers.task.trending.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.teacomputers.task.trending.R
import com.teacomputers.task.trending.data.model.MediaItem
import com.teacomputers.task.trending.ui.movie.MovieViewModel
import com.teacomputers.task.trending.ui.navigation.Screens
import com.teacomputers.task.trending.ui.series.SeriesViewModel
import com.teacomputers.task.trending.util.Constants

private lateinit var viewModel: SearchViewModel

@Composable
fun SearchScreen(navController: NavController) {
    Column {
        viewModel =
            ViewModelProvider(LocalViewModelStoreOwner.current!!)[SearchViewModel::class.java]
        ActionBar()
        viewModel.searchString.observeAsState().value.let { it ->
            if (it.isNullOrEmpty()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(50.dp)
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    text = "No Results"
                )
            } else {
                viewModel.search()
                viewModel.searchResultsList.observeAsState().value.let {
                    if (it.isNullOrEmpty()) {
                        CircularProgressIndicator(
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        RecyclerView(items = it, navController)
                    }
                }

            }

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ActionBar() {
    var textInput by remember { mutableStateOf("") }
    Row(modifier = Modifier.padding(10.dp)) {
        TextField(modifier = Modifier.fillMaxWidth(), value = textInput, onValueChange = {
            textInput = it
            viewModel.searchString.value = it
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ItemPoster(item: MediaItem, onClick: () -> Unit) {
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
            model = LocalContext.current.getString(R.string.image_base_url) + item.poster_path,
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
private fun RecyclerView(items: List<MediaItem>, navController: NavController) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        items(items) { item ->
            ItemPoster(item = item) {
                if (item.media_type == Constants.MOVIE_TYPE) {
                    MovieViewModel.mMovie.value = MediaItem.convertToMovieObject(item)
                    navController.navigate(Screens.MovieDetailsScreen.name) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                } else if (item.media_type == Constants.TV_TYPE) {
                    SeriesViewModel.mSeries.value = MediaItem.convertToSeriesObject(item)
                    navController.navigate(Screens.SeriesDetailsScreen.name) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }

            }
        }
    }
}



