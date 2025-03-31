package org.example.project.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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


@Composable
fun MovieAppBar(
    onBackButtonClick: () -> Unit,
    isShowingDetailPage: Boolean
) {
    TopAppBar(
        title = {""}, //убрать нахуй название и интегрировать туда поиск
//        title = {
//            Text(
//                text =
//                if (isShowingDetailPage) {
//                    ""
//                } else {
//                    "Movie Database App"
//                },
//                fontWeight = FontWeight.Bold
//            )
//        },
        backgroundColor = Color.Transparent,
        contentColor = MaterialTheme.colors.secondary,
        navigationIcon = if (isShowingDetailPage) {
            {
                Box(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            top = 8.dp,
                            bottom = 8.dp
                        )
                        .size(48.dp)
                        .background(
                            color = Color.Black.copy(alpha = 0.3f),
                            shape = CircleShape
                        )
                        .clickable(onClick = onBackButtonClick),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        } else {
            { Box {} }
        }
    )
}