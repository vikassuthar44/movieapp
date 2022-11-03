package com.example.movieapp.moviewDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.MovieDetails
import com.example.movieapp.network.NetWorkRepository
import com.example.movieapp.util.RequestState
import com.example.movieapp.util.RequestState.Idle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviewDetailsViewModel @Inject constructor(
    val netWorkRepository: NetWorkRepository
): ViewModel() {

    private val _movieDetails = MutableStateFlow<RequestState<MovieDetails>>(Idle)
    val movieDetails = _movieDetails.asStateFlow()


    fun getMovieDetails(movieId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _movieDetails.value = RequestState.Loading
            netWorkRepository.getMovieDetails(movieId = movieId).let {
                if(it.isSuccessful) {
                    _movieDetails.value = RequestState.Success(it.body())
                } else {
                    _movieDetails.value = RequestState.Error(it.message())
                }
            }
        }
    }
}