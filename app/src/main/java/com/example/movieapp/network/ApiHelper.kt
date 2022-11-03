package com.example.movieapp.network

import com.example.movieapp.data.MovieDetails
import com.example.movieapp.data.MovieResponse
import retrofit2.Response

interface ApiHelper {
    suspend fun getMovie(movieName: String, pageNo: Int): Response<MovieResponse>

    suspend fun getMovieDetails(movieId: String): Response<MovieDetails>
}