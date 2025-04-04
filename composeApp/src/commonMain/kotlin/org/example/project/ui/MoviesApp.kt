package org.example.project.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.example.project.data.MoviesRepository
import org.example.project.utils.MoviesContentType

@Composable
fun MoviesApp(
    windowSize: Int,
    moviesRepository: MoviesRepository,
) {
    val moviesViewModel = remember { MoviesViewModel(moviesRepository) }
    val uiState by moviesViewModel.uiState.collectAsState()

    LaunchedEffect(windowSize) {
        println("DEBUG: Window size is $windowSize")
    }
    val contentType = if (windowSize > 600) {
        MoviesContentType.ListAndDetail
    } else {
        MoviesContentType.ListOnly
    }

    Scaffold{
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            HomeScreen(
                moviesConnectionState = moviesViewModel.moviesConnectionState,
                moviesViewModel = moviesViewModel,
                uiState = uiState,
                retryAction = moviesViewModel::getMovies,
                modifier = Modifier.fillMaxSize(),
                contentType = contentType
            )
        }
    }
}

@Composable
fun HomeScreen(
    moviesConnectionState: MoviesConnectionState,
    moviesViewModel: MoviesViewModel,
    uiState: MoviesUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentType: MoviesContentType
) {
    if (contentType == MoviesContentType.ListAndDetail) {
        MoviesListAndDetail(
            moviesConnectionState = moviesConnectionState,
            selectedMovie = uiState.currentMovie,
            onClick = {
                moviesViewModel.updateCurrentMovie(it)
            },
            onBackPressed = {
                moviesViewModel.updateCurrentMovie(null)
            },
            moviesViewModel = moviesViewModel,
            retryAction = retryAction,
            initialSearchText = uiState.searchText,
        )
    } else {
        if (uiState.isShowingListPage) {
            MoviesList(
                moviesConnectionState = moviesConnectionState,
                onClick = {
                    moviesViewModel.updateCurrentMovie(it)
                    moviesViewModel.navigateToDetailPage()
                },
                modifier = modifier,
                moviesViewModel = moviesViewModel,
                retryAction = retryAction,
                initialSearchText = uiState.searchText,
            )
        } else {
            MovieDetail(
                selectedMovie = uiState.currentMovie,
                onBackPressed = {
                    moviesViewModel.navigateToListPage()
                }
            )
        }
    }
}
