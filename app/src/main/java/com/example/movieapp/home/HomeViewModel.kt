package com.example.movieapp.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.MovieResponse
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
class HomeViewModel @Inject constructor(
    val netWorkRepository: NetWorkRepository
): ViewModel() {

    private val _movieList = MutableStateFlow<RequestState<MovieResponse>>(Idle)
    val movieList = _movieList.asStateFlow()

    fun getMovieList(movieName: String, pageNo: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _movieList.value = RequestState.Loading
            netWorkRepository.getMovieList(movieName = movieName, pageNo = pageNo).let {
                if(it.isSuccessful) {
                    _movieList.value = RequestState.Success(it.body())
                } else {
                    _movieList.value = RequestState.Error("Went something wrong!")
                }
            }
        }
    }
}