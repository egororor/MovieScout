package org.example.project.data

import org.example.project.model.SearchMoviesList
import org.example.project.network.MoviesApiService

interface MoviesRepository {
    suspend fun getMovies(searchText: String): SearchMoviesList
}

class NetworkMoviesRepository(
    private val moviesApiService: MoviesApiService,
    private val apiKey: String
) : MoviesRepository {
    override suspend fun getMovies(searchText: String): SearchMoviesList {
        val detailedSearchResponse = SearchMoviesList(
            search = mutableListOf(),
            totalResults = "",
            response = ""
        )

        val searchResponse = moviesApiService.getMovies(searchText, "movie", apiKey)

        if (searchResponse.response != "True") {
            if (searchText == "") {
                val myTopMovie = SearchMoviesList(
                    search = mutableListOf(),
                    totalResults = "1",
                    response = "True",
                    topMovie = true
                )
                myTopMovie.search.add(moviesApiService.getDetailedMovie("tt1979320", apiKey))
                return myTopMovie
            }
            return SearchMoviesList(search = mutableListOf(), totalResults = "0", response = "False")
        }

        if (searchResponse.response == "True") {
            searchResponse.search.forEach { movie ->
                val imdbId = movie.imdbId
                val detailedMovie = moviesApiService.getDetailedMovie(imdbId, apiKey)
                detailedSearchResponse.search.add(detailedMovie)
            }
            detailedSearchResponse.totalResults = searchResponse.totalResults
            detailedSearchResponse.response = searchResponse.response
        }

        return detailedSearchResponse
    }
}