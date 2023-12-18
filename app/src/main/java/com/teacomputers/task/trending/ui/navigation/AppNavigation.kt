@file:OptIn(ExperimentalMaterial3Api::class)

package com.teacomputers.task.trending.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.teacomputers.task.trending.ui.favorite.FavoriteScreen
import com.teacomputers.task.trending.ui.movie.MovieDetailsScreen
import com.teacomputers.task.trending.ui.movie.MovieScreen
import com.teacomputers.task.trending.ui.search.SearchScreen
import com.teacomputers.task.trending.ui.series.SeriesDetailsScreen
import com.teacomputers.task.trending.ui.series.SeriesScreen

@Composable
fun AppNavigation() {
    val navigationController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                var selectedItemIndex by rememberSaveable {
                    mutableIntStateOf(0)
                }
                navigationItemsList.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                            navigationController.navigate(item.route) {
                                popUpTo(navigationController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }

                        },
                        label = {
                            Text(text = item.title)
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navigationController,
            startDestination = Screens.MovieScreen.name,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(route = Screens.MovieScreen.name) {
                MovieScreen(navigationController)
            }
            composable(route = Screens.SeriesScreen.name) {
                SeriesScreen(navigationController)
            }
            composable(route = Screens.FavoriteScreen.name) {
                FavoriteScreen(navigationController)
            }
            composable(route = Screens.SearchScreen.name) {
                SearchScreen(navigationController)
            }
            composable(route = Screens.MovieDetailsScreen.name) {
                MovieDetailsScreen()
            }
            composable(route = Screens.SeriesDetailsScreen.name) {
                SeriesDetailsScreen()
            }
        }

    }
}