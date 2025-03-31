package org.example.project.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Rating(
    @SerialName("Source") var source: String = "",
    @SerialName("Value") var value: String = "",
)

@Serializable
data class DetailedMovie(
    @SerialName("Title") val title: String,
    @SerialName("Year") val released: String = "",
    @SerialName("Runtime") val runtime: String = "",
    @SerialName("Genre") val genre: String = "",
    @SerialName("Director") val director: String = "",
    @SerialName("Actors") val actors: String = "",
    @SerialName("Plot") val plot: String = "",
    //@SerialName("Language") val language: String = "",
    @SerialName("Ratings") var ratings: MutableList<Rating> = mutableListOf(),
    //@SerialName("Awards") val awards: String = "",
    @SerialName("Country") val country: String = "",
    @SerialName("Poster") val poster: String,
    @SerialName("imdbID") val imdbId: String,
)
