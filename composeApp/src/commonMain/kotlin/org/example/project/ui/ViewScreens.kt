package org.example.project.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.example.project.model.DetailedMovie
import androidx.compose.material.TextField
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextFieldDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import kotlinx.coroutines.delay
import migrationtest.composeapp.generated.resources.Res
import migrationtest.composeapp.generated.resources.broken_image
import migrationtest.composeapp.generated.resources.cancel
import migrationtest.composeapp.generated.resources.cursive_2
import migrationtest.composeapp.generated.resources.loading_img
import migrationtest.composeapp.generated.resources.micro
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MoviesListAndDetail(
    moviesConnectionState: MoviesConnectionState,
    selectedMovie: DetailedMovie?,
    onClick: (DetailedMovie) -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    moviesViewModel: MoviesViewModel,
    retryAction: () -> Unit,
    initialSearchText: String,
) {
    Row(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.weight(3f)
        ) {
            MoviesList(
                moviesConnectionState = moviesConnectionState,
                onClick = onClick,
                moviesViewModel = moviesViewModel,
                retryAction = retryAction,
                initialSearchText = initialSearchText,
            )
        }
        Box(
            modifier = Modifier.weight(3f)
        ) {
            MovieDetail(
                selectedMovie = selectedMovie,
                onBackPressed = onBackPressed,
            )
        }
    }
}

@Composable
fun TopMovieListItem(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.secondary
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .size(150.dp)
        ) {
            Image(
                painter = painterResource(Res.drawable.cursive_2),
                contentDescription = "broken image",
                modifier = modifier,
                contentScale = ContentScale.FillBounds,
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
/*private*/ fun MoviesListItem(
    movie: DetailedMovie,
    onItemClick: (DetailedMovie) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        onClick = { onItemClick(movie) },
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .size(150.dp)
        ) {
            KamelImage(
                modifier = Modifier
                    .height(150.dp)
                    .width(100.dp),
                resource = asyncPainterResource(data = movie.poster),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                onLoading = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingScreen(modifier.size(2.dp))
                    }
                },
                onFailure = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        BrokenImageScreen(modifier.size(200.dp))
                    }
                }
            )
            Column(
                modifier = Modifier
                    .padding(
                        vertical = 12.dp,
                        horizontal = 16.dp
                    )
                    .weight(1f)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                //Spacer(Modifier.height(20.dp))
                Column (
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = movie.released,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(bottom = 1.dp)
                    )
                    Text(
                        text = movie.runtime,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(bottom = 1.dp)
                    )
                    Text(
                        text = movie.genre,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(bottom = 1.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun MoviesList(
    moviesConnectionState: MoviesConnectionState,
    onClick: (DetailedMovie) -> Unit,
    modifier: Modifier = Modifier,
    moviesViewModel: MoviesViewModel,
    retryAction: () -> Unit,
    initialSearchText: String,
) {
    var searchText by remember { mutableStateOf(initialSearchText) }
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 25.dp)
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        EditSearchField(
            value = searchText,
            onValueChanged = {
                searchText = it
                moviesViewModel.updateSearchText(searchText)
            },
            modifier = Modifier.fillMaxWidth()
        )
        when (moviesConnectionState) {
            is MoviesConnectionState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    LoadingScreen(Modifier.size(200.dp))
                }
            }

            is MoviesConnectionState.Success -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = modifier.padding(top = 16.dp)
                ) {
                    items(moviesConnectionState.movies, key = { movie -> movie.imdbId }) { movie ->
                        MoviesListItem(
                            movie = movie,
                            onItemClick = onClick
                        )
                    }
                    if (moviesConnectionState.topMovie) {
                        item {
                            TopMovieListItem()
                        }
                    }
                }
            }

            else -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                ErrorScreen(retryAction, modifier)
            }
        }
    }
}

@Composable
expect fun HandleBackPress(onBackPressed: () -> Unit)

//@Composable
//fun MovieDetail(
//    selectedMovie: DetailedMovie?,
//    onBackPressed: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    HandleBackPress(onBackPressed)
//    val scrollState = rememberScrollState()
//    Box(
//        modifier = modifier
//            .verticalScroll(state = scrollState)
//    ) {
//        Column {
//            if (selectedMovie != null) {
//                Box(
//                    modifier = Modifier.padding(vertical = 0.dp),
//                ) {
//                    KamelImage(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .heightIn(
//                                min = 300.dp,
//                                max = 650.dp
//                            ),
//                        resource = asyncPainterResource(data = selectedMovie.poster),
//                        contentDescription = null,
//                        contentScale = ContentScale.Crop,
//                        onLoading = {
//                            Box(
//                                modifier = Modifier
//                                    .fillMaxSize(),
//                                contentAlignment = Alignment.Center
//                            ) {
//                                LoadingScreen(modifier.size(100.dp))
//                            }
//                        },
//                        onFailure = {
//                            Box(
//                                modifier = Modifier
//                                    .fillMaxSize(),
//                                contentAlignment = Alignment.Center
//                            ) {
//                                BrokenImageScreen(modifier.size(150.dp))
//                            }
//                        }
//                    )
//                    IconButton(
//                        onClick = onBackPressed,
//                        modifier = Modifier
//                            .padding(32.dp)
//                            .size(32.dp)
//                            .background(
//                                color = Color.Black.copy(alpha = 0.3f),
//                                shape = CircleShape
//                            )
//                            .align(Alignment.TopStart)
//                    ) {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = "Back",
//                            tint = Color.White
//                        )
//                    }
//                }
//                Spacer(Modifier.height(20.dp))
//                Text(
//                    text = selectedMovie.title,
//                    style = MaterialTheme.typography.h4,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier
//                        .padding(horizontal = 16.dp),
//                    color = MaterialTheme.colors.secondary
//                )
//                MovieDetailItem(selectedMovie.genre, MaterialTheme.typography.h6)
//                MovieDetailItem(selectedMovie.released, MaterialTheme.typography.h6)
//                MovieDetailItem(selectedMovie.runtime, MaterialTheme.typography.h6)
//                MovieDetailItem(selectedMovie.language, MaterialTheme.typography.h6)
//                MovieDetailItem(selectedMovie.country, MaterialTheme.typography.h6)
//                Spacer(Modifier.height(10.dp))
//                MovieDetailItem(selectedMovie.plot, MaterialTheme.typography.body1)
//                Spacer(Modifier.height(10.dp))
//                MovieDetailItem(
//                    "Director: " + selectedMovie.director,
//                    MaterialTheme.typography.h6
//                )
//                MovieDetailItem(
//                    "Actors: " + selectedMovie.actors,
//                    MaterialTheme.typography.h6
//                )
//                ShareMovieButton(selectedMovie.title, selectedMovie.plot)
//            }
//        }
//    }
//}

@Composable
fun MovieDetail(
    selectedMovie: DetailedMovie?,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    HandleBackPress(onBackPressed)

    if (selectedMovie == null) return

    val scrollState = rememberScrollState()
    val horizontalPadding = 16.dp
    val imageRatio = 2f / 3f

    Column(
        modifier = modifier.fillMaxSize()
    ) {

        // --- TOP HALF ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .statusBarsPadding()  // Ensures content is below the phone's status bar
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        bottom = horizontalPadding * 2,
                        top = horizontalPadding * 3
                    )
                    .padding(
                        horizontal = horizontalPadding,
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // --- Poster on the left ---
                Box(
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxHeight()
                        .padding(end = horizontalPadding / 2), // Same spacing before the center line
                   contentAlignment = Alignment.BottomCenter
                ) {
                    BoxWithConstraints {
                        val width = maxWidth
                        val height = width / imageRatio
                        KamelImage(
                            modifier = Modifier
                                .width(width)
                                .height(height)
                                .clip(RoundedCornerShape(12.dp)),
                            resource = asyncPainterResource(data = selectedMovie.poster),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            onLoading = {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    LoadingScreen(Modifier.size(60.dp))
                                }
                            },
                            onFailure = {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    BrokenImageScreen(Modifier.size(60.dp))
                                }
                            }
                        )
                    }
                }

                // --- Movie info on the right ---
                Column(
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxHeight()
                        .padding(start = horizontalPadding / 2), // same spacing as on the left
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = selectedMovie.title,
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.secondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${selectedMovie.released} · ${selectedMovie.runtime}",
                        style = MaterialTheme.typography.subtitle1
                    )
                    // Extract ratings from the list
                    val rottenTomatoesRating = selectedMovie.ratings.find { it.source == "Rotten Tomatoes" }?.value
                    val metacriticRaw = selectedMovie.ratings.find { it.source == "Metacritic" }?.value
                    // Convert Metacritic rating from "74/100" to "74%"
                    val metacriticRating = metacriticRaw?.substringBefore("/")?.plus("%")

                    // Display ratings if available
                    Text(
                        text = "🍅 $rottenTomatoesRating · 🍿 $metacriticRating",
                        style = MaterialTheme.typography.subtitle1
                    )
                    Text(
                        text = selectedMovie.genre,
                        style = MaterialTheme.typography.subtitle1
                    )
                }
            }

            // --- Back Icon (top-left) ---
            IconButton(
                onClick = onBackPressed,
                modifier = Modifier
                    .padding(16.dp)
                    .size(32.dp)
//                    .background(
//                        color = Color.Black.copy(alpha = 0.3f),
//                        shape = CircleShape
//                    )
                    .align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            // --- Share Icon (top-right) ---
            IconButton(
                onClick = { shareMovie(selectedMovie.title, selectedMovie.plot) },
                modifier = Modifier
                    .padding(16.dp)
                    .size(32.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.3f),
                        shape = CircleShape
                    )
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share",
                    tint = Color.White
                )
            }
        }

        // --- BOTTOM HALF ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = horizontalPadding, vertical = 12.dp)
            ) {
                Text(
                    text = selectedMovie.plot,
                    style = MaterialTheme.typography.body1
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Director: ")
                        }
                        append(selectedMovie.director)
                    },
                    style = MaterialTheme.typography.subtitle1
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Actors: ")
                        }
                        append(selectedMovie.actors)
                    },
                    style = MaterialTheme.typography.subtitle1
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Countries: ")
                        }
                        append(selectedMovie.country)
                    },
                    style = MaterialTheme.typography.subtitle1
                )
            }
        }
    }
}

@Composable
fun MovieDetailItem(
    text: String,
    style: TextStyle
) {
    Text(
        text = text,
        style = style,
        modifier = Modifier
            .padding(horizontal = 16.dp),
        color = MaterialTheme.colors.secondary
    )
}

@Composable
fun EditSearchField(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        singleLine = true,
        modifier = modifier,
        onValueChange = onValueChanged,
        placeholder = { Text(" Search") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        shape = RoundedCornerShape(16.dp),
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = { onValueChanged("") }) {
                    Icon(
                        painter = painterResource(Res.drawable.cancel),
                        contentDescription = "Clear search",
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = MaterialTheme.colors.primaryVariant,
            cursorColor = MaterialTheme.colors.background,
            textColor = MaterialTheme.colors.secondary,
            placeholderColor = MaterialTheme.colors.onBackground
        ),
    )
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(Res.drawable.loading_img),
        contentDescription = "loading",
        modifier = modifier
    )
}

@Composable
fun BrokenImageScreen(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(Res.drawable.broken_image),
        contentDescription = "broken image",
        modifier = modifier
    )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("loading failed")
        Button(onClick = retryAction) {
            Text("retry")
        }
    }
}

@Composable
fun ShareMovieButton(movieTitle: String, movieDescription: String) {
    Button(onClick = { shareMovie(movieTitle, movieDescription) }) {
        Text("Share Movie")
    }
}
