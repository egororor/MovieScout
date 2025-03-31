package org.example.project.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchMoviesList(
    @SerialName("Search") var search: MutableList<DetailedMovie> = mutableListOf(),
    @SerialName("totalResults") var totalResults: String = "",
    @SerialName("Response") var response: String,
    @SerialName("TopMovie") var topMovie: Boolean = false
)
