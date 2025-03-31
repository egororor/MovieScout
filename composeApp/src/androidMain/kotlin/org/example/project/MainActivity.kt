package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import org.example.project.ui.theme.MovieDatabaseAppTheme
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import org.example.project.model.DetailedMovie
import org.example.project.ui.MovieDetail
import org.example.project.ui.MoviesListItem


class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            MovieDatabaseAppTheme {
                val layoutDirection = LocalLayoutDirection.current
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = WindowInsets.safeDrawing
                                .asPaddingValues()
                                .calculateStartPadding(layoutDirection),
                            end = WindowInsets.safeDrawing
                                .asPaddingValues()
                                .calculateEndPadding(layoutDirection)
                        ),
                    color = MaterialTheme.colors.primary
                ) {
                    val windowSize = calculateWindowSizeClass(this@MainActivity)
                    App(windowSize = windowSize.widthSizeClass.hashCode() * 600)
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun AppAndroidPreview() {
//    App(599)
//}
//
//@Preview
//@Composable
//fun MovieListItemPreview() {
//    MoviesListItem(
//        DetailedMovie(
//            "AAAAAAAAA",
//            released = "aaaa",
//            runtime = "aaaa",
//            genre = "aaaa",
//            director = "aaaa",
//            actors = "aaaa",
//            plot = "aaaa",
//            language = "aaaa",
//            country = "aaaa",
//            poster = "aaaa",
//            imdbId = "aaaa",
//        ),
//        onItemClick = {},
//        modifier = Modifier,
//    )
//}
//
//@Preview
//@Composable
//fun MovieDetailPreview() {
//    MovieDetail(
//        DetailedMovie(
//            "AAAAAAAAA",
//            released = "aaaa",
//            runtime = "aaaa",
//            genre = "aaaa",
//            director = "aaaa",
//            actors = "aaaa",
//            plot = "aaaa",
//            language = "aaaa",
//            country = "aaaa",
//            poster = "https://m.media-amazon.com/images/M/MV5BNWE5MGI3MDctMmU5Ni00YzI2LWEzMTQtZGIyZDA5MzQzNDBhXkEyXkFqcGc@._V1_SX300.jpg",
//            imdbId = "aaaa",
//        ),
//        onBackPressed = {},
//        modifier = Modifier.background(color = Color.Red),
//    )
//}