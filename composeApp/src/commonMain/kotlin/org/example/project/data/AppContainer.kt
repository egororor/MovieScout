package org.example.project.data

import org.example.project.createHttpClient
import org.example.project.network.MoviesApiService

interface AppContainer {
    val moviesRepository: MoviesRepository
}

class DefaultAppContainer(apiKey: String) : AppContainer {
    private val httpClient = createHttpClient()
    private val moviesApiService = MoviesApiService(httpClient)

    override val moviesRepository: MoviesRepository by lazy {
        NetworkMoviesRepository(moviesApiService, apiKey)
    }
}