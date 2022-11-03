package com.example.movieapp.network

import com.example.movieapp.Constant
import com.example.movieapp.data.MovieDetails
import com.example.movieapp.data.MovieResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    val apiService: ApiService
):ApiHelper {

    override suspend fun getMovie(movieName: String, pageNo: Int): Response<MovieResponse> = apiService.getMovieList(apikey = Constant.API_KEY, movieName = movieName, page = pageNo.toString())

    override suspend fun getMovieDetails(movieId: String): Response<MovieDetails> = apiService.moviewDetails(imdbID = movieId, apikey = Constant.API_KEY)
}