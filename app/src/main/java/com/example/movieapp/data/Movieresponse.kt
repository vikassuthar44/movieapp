package com.example.movieapp.data

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("Response")
    val response: String,
    @SerializedName("Search")
    val movieList: List<Movie>
) {
    data class Movie(
        @SerializedName("Title")
        val title: String,
        @SerializedName("imdbID")
        val imdbID: String,
        @SerializedName("Poster")
        val Poster: String
    )
}

data class MovieDetails(
    @SerializedName("Response")
    val response: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Actors")
    val actors: String,
    @SerializedName("Poster")
    val poster: String,
    @SerializedName("Plot")
    val plot:String
)