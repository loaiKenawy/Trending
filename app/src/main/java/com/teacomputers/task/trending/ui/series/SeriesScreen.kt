package com.teacomputers.task.trending.ui.series

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.outlined.LiveTv
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
import com.teacomputers.task.trending.data.model.Series
import com.teacomputers.task.trending.ui.navigation.Screens

private lateinit var viewModel: SeriesViewModel

@Composable
fun SeriesScreen(navController: NavController) {
    Column {
        ActionBar()
        viewModel =
            ViewModelProvider(LocalViewModelStoreOwner.current!!)[SeriesViewModel::class.java]
        viewModel.getTrendingSeries()
        viewModel.mSeriesList.observeAsState().value.let {
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
            modifier = Modifier.padding(10.dp)
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .fillMaxHeight()
                    .align(Alignment.CenterVertically),
                imageVector = Icons.Outlined.LiveTv,
                contentDescription = "Series"
            )
            Text(
                modifier = Modifier
                    .padding(5.dp), text = "Series",
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SeriesPoster(series: Series, onClick: () -> Unit) {
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
            model = LocalContext.current.getString(R.string.image_base_url) + series.poster_path,
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
private fun RecyclerView(series: List<Series>, navController: NavController) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        items(series) { item ->
            SeriesPoster(series = item) {
                SeriesViewModel.mSeries.value = item
                navController.navigate(Screens.SeriesDetailsScreen.name) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }
        }
    }
}