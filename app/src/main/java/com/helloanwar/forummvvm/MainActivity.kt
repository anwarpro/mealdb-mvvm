package com.helloanwar.forummvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.helloanwar.forummvvm.data.Resource
import com.helloanwar.forummvvm.data.model.Meal
import com.helloanwar.forummvvm.data.source.MealRepository
import com.helloanwar.forummvvm.data.source.local.LocalMealSource
import com.helloanwar.forummvvm.data.source.remote.RemoteMealSource
import com.helloanwar.forummvvm.data.succeeded
import com.helloanwar.forummvvm.ui.home.HomeViewModel
import com.helloanwar.forummvvm.ui.theme.ForumMVVMTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel: HomeViewModel by viewModels()

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ForumMVVMTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    HomeScreen(viewModel)
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Forum MVVM")
                },
                backgroundColor = MaterialTheme.colors.primary
            )
        }
    ) {
        val mealsRemote = viewModel.meal.observeAsState()

        when (mealsRemote.value) {
            is Resource.Error -> {
                Text(text = "Some went wrong")
            }
            Resource.Loading -> {
                CircularProgressIndicator()
            }
            is Resource.Success -> {
                LazyVerticalGrid(cells = GridCells.Fixed(2)) {

                    if (mealsRemote.value?.succeeded == true) {
                        val data = (mealsRemote.value as Resource.Success).data

                        data.let {
                            items(it.meals) { meal ->
                                MealItem(meal)
                            }
                        }
                    }

                }
            }
            null -> {

            }
        }
    }
}

@Composable
private fun MealItem(meal: Meal) {
    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.padding(4.dp)) {
        Image(
            painter = rememberImagePainter(meal.strMealThumb) {
                placeholder(R.drawable.ic_launcher_background)
                error(R.drawable.ic_launcher_background)
            },
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentScale = ContentScale.FillBounds
        )
        Row(
            modifier = Modifier.background(
                color = Color.Black.copy(alpha = 0.25f)
            )
        ) {
            Text(
                text = meal.strMeal.toString(),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ForumMVVMTheme {
        val viewModel: HomeViewModel =
            HomeViewModel(MealRepository(RemoteMealSource(), LocalMealSource()))
        HomeScreen(viewModel = viewModel)
    }
}

@Preview(showBackground = true)
@Composable
fun MealItemPreview() {
    MealItem(
        meal = Meal(
            strMealThumb = "https://www.themealdb.com/images/media/meals/tkxquw1628771028.jpg",
            strMeal = "Corba"
        )
    )
}