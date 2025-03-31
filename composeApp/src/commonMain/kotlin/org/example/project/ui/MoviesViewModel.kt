package org.example.project.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.data.MoviesRepository
import org.example.project.model.DetailedMovie

sealed interface MoviesConnectionState {
    data class Success(val movies: List<DetailedMovie>, var topMovie: Boolean) : MoviesConnectionState
    object Error : MoviesConnectionState
    object Loading : MoviesConnectionState
}

data class MoviesUiState(
    val currentMovie: DetailedMovie? = null,
    val isShowingListPage: Boolean = true,
    val searchText: String = "",
)

expect fun shareMovie(movieTitle: String, movieDescription: String)

//expect fun sleepMillis(millis: Long)

class MoviesViewModel(private val moviesRepository: MoviesRepository) {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _uiState = MutableStateFlow(
        MoviesUiState(currentMovie = null)
    )
    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()

    fun updateCurrentMovie(selectedMovie: DetailedMovie?) {
        _uiState.update { it.copy(currentMovie = selectedMovie) }
    }

    fun navigateToListPage() {
        _uiState.update { it.copy(isShowingListPage = true, currentMovie = null) }
    }

    fun navigateToDetailPage() {
        _uiState.update { it.copy(isShowingListPage = false) }
    }

    fun updateSearchText(searchText: String) {
        _uiState.update { it.copy(searchText = searchText) }
        getMovies()
    }

    var moviesConnectionState: MoviesConnectionState by mutableStateOf(MoviesConnectionState.Loading)
        private set

    init {
        getMovies()
    }

    fun getMovies() {
        coroutineScope.launch {
            moviesConnectionState = MoviesConnectionState.Loading
            delay(300)
            moviesConnectionState = try {
                val movies = moviesRepository.getMovies(_uiState.value.searchText)
                MoviesConnectionState.Success(movies.search, movies.topMovie)
            } catch (e: Exception) {
                Logger.logError("getMovies", "Error fetching movies: ${e.message}", e)
                MoviesConnectionState.Error
            }
        }
    }
}