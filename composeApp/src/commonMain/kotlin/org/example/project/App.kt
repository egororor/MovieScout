package org.example.project

import androidx.compose.runtime.*
import org.example.project.data.DefaultAppContainer
import org.example.project.ui.MoviesApp
import org.example.project.ui.theme.MovieDatabaseAppTheme

@Composable
fun App(windowSize: Int) {
    MovieDatabaseAppTheme {
        val apiKey = "5f348621"
        val appContainer = DefaultAppContainer(apiKey)
        val moviesRepository = appContainer.moviesRepository

        MoviesApp(
            windowSize = windowSize,
            moviesRepository = moviesRepository,
        )
    }
}