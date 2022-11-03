package com.example.movieapp.moviewDetails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ContentScale.Companion
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp.ui.theme.MovieAppTheme
import com.example.movieapp.util.RequestRender
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsActivity : ComponentActivity() {

    private var movieId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = intent.getStringExtra("movieId").toString()
        setContent {
            MovieAppTheme() {
                MainContent()
            }
        }
    }

    @Composable
    fun MainContent() {
        val moviewDetailsViewModel = hiltViewModel<MoviewDetailsViewModel>()

        LaunchedEffect(key1 = Unit ) {
            moviewDetailsViewModel.getMovieDetails(movieId = movieId)
        }

        RequestRender(
            state = moviewDetailsViewModel.movieDetails.collectAsState(),
            onSuccess = {
                if(it.response == "True") {
                    val painter = rememberAsyncImagePainter(model = it.poster)
                    Surface(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(all = 20.dp), horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f),
                                contentScale = ContentScale.Fit,
                                painter = painter,
                                contentDescription = "moview"
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = it.title)
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = it.actors)
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = it.plot)
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "something went wrong")
                    }
                }
            },
            onLoading = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Loading...")
                }
            },
            onError = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = it)
                }
            }
        )
    }
}

