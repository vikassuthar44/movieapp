package com.example.movieapp.network

import javax.inject.Inject

class NetWorkRepository @Inject constructor(
    val apiHelper: ApiHelper
) {

    suspend fun getMovieList(movieName: String, pageNo: Int) = apiHelper.getMovie(movieName = movieName, pageNo = pageNo)

    suspend fun getMovieDetails(movieId: String) = apiHelper.getMovieDetails(movieId = movieId)
}

