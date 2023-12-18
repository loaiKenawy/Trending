package com.teacomputers.task.trending

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.teacomputers.task.trending.ui.navigation.AppNavigation
import com.teacomputers.task.trending.ui.theme.TrendingTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrendingTheme {
                AppNavigation()
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TrendingTheme {

    }
}