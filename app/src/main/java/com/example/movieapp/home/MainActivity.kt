package com.example.movieapp.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp.data.MovieResponse.Movie
import com.example.movieapp.moviewDetails.MovieDetailsActivity
import com.example.movieapp.ui.theme.MovieAppTheme
import com.example.movieapp.util.RequestRender
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                MainContent()
            }
        }
    }
}

@Composable
fun MainContent() {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val movieName = remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    val pageNo = remember {
        mutableStateOf(1)
    }
    val state = rememberLazyGridState()
    if(state.firstVisibleItemIndex % 4 == 0) {
        pageNo.value = pageNo.value++
    }
    LaunchedEffect(key1 = pageNo.value) {
        homeViewModel.getMovieList(movieName = movieName.value, pageNo = pageNo.value)
    }

    Scaffold(topBar = {
        Text(text = "Movie App")
    }) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextField(value = movieName.value, onValueChange = {
                    movieName.value = it
                })
                Button(onClick = {
                    homeViewModel.getMovieList(movieName.value, pageNo.value)
                }) {
                    Text(text = "search")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            RequestRender(
                state = homeViewModel.movieList.collectAsState(),
                onSuccess = {
                    if(it.response == "True") {
                        LazyVerticalGrid(
                            state = state,
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(all = 20.dp),
                            content = {
                                items(items = it.movieList) { item ->
                                    SingleMovieItem(movie = item) {
                                        val intent = Intent(context, MovieDetailsActivity::class.java)
                                        intent.putExtra("movieId", it)
                                        context.startActivity(intent)
                                    }
                                }
                            })
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Movie Not Found")
                        }
                    }
                },
                onLoading = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Loading....")
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
}

@Composable
fun SingleMovieItem(
    movie: Movie,
    onClick: (String) -> Unit
) {
    val painterImage = rememberAsyncImagePainter(model = movie.Poster, )
    Surface(
        modifier = Modifier
            .wrapContentSize()
            .padding(all = 10.dp)
            .background(color = Color.Gray, shape = RoundedCornerShape(size = 20.dp))
            .padding(bottom = 10.dp)
            .clickable {
                onClick.invoke(movie.imdbID)
            }
    ) {
       Column(horizontalAlignment = Alignment.CenterHorizontally) {
           Image(
               modifier = Modifier
                   .fillMaxWidth()
                   .aspectRatio(1f),
               painter = painterImage,
               contentDescription = "movie",
               contentScale = ContentScale.Crop
           )
           Spacer(modifier = Modifier.height(10.dp))
           Text(text = movie.title)
       }
    }
}
