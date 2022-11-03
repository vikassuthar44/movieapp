package com.example.movieapp.network

import com.example.movieapp.data.MovieDetails
import com.example.movieapp.data.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(".")
    suspend fun getMovieList(
        @Query("apikey") apikey: String,
        @Query("s") movieName: String,
        @Query("page") page: String
    ): Response<MovieResponse>

    @GET(".")
    suspend fun moviewDetails(
        @Query("i") imdbID: String,
        @Query("apikey") apikey: String
    ): Response<MovieDetails>
}