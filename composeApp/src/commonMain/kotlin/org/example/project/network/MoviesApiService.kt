package org.example.project.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import org.example.project.model.DetailedMovie
import org.example.project.model.SearchMoviesList

class MoviesApiService(private val client: HttpClient) {

    private val BASE_URL = "https://www.omdbapi.com/"

    suspend fun getMovies(title: String, type: String, apiKey: String): SearchMoviesList {
        return client.get(BASE_URL) {
            url {
                parameters.append("s", title)
                parameters.append("type", type)
                parameters.append("apikey", apiKey)
            }
        }.body()
    }

    suspend fun getDetailedMovie(imdbId: String, apiKey: String): DetailedMovie {
        return client.get(BASE_URL) {
            url {
                parameters.append("i", imdbId)
                parameters.append("apikey", apiKey)
            }
        }.body()
    }
}