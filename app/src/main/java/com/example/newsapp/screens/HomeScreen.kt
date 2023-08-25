package com.example.newsapp.screens


import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Newspaper
import androidx.compose.material.icons.rounded.NightsStay
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.newsapp.R
import com.example.newsapp.ViewModel.NewsViewModel
import com.example.newsapp.darkTheme
import com.example.newsapp.ui.theme.PrimaryColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(sharedPreferences: SharedPreferences)
{
    val newsViewModel:NewsViewModel = viewModel()
    val editor = sharedPreferences.edit()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = if (darkTheme) Color(0xFF3F2E3E) else PrimaryColor,
                    ),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Newspaper,
                            contentDescription = "Logo",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "News",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        darkTheme = !darkTheme
                        editor.putBoolean("mode", darkTheme)
                        editor.apply()

                    }) {
                        Icon(
                            imageVector =
                            if(darkTheme) Icons.Rounded.WbSunny else Icons.Rounded.NightsStay,
                            contentDescription = "Dark mode")
                    }
                }
            )
        }
    )
    { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Button(
                    onClick = { newsViewModel.loadNews() },
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = "Load News", color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = { newsViewModel.cancelLoading() },
                    modifier = Modifier.weight(1f),
                    ) {
                    Text(
                        text = "Cancel Loading", color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            if(newsViewModel.isArrived && !newsViewModel.isLoading)
            {
                LazyColumn(modifier = Modifier.fillMaxSize())
                {
                    items(newsViewModel.news!!.size){index ->
                        val article = newsViewModel.news!![index]

                        Card(modifier = Modifier
                            .shadow(elevation = 7.dp, shape = RoundedCornerShape(percent = 15))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(160.dp)
                                    .background(color = if (darkTheme) Color(0xFF3F2E3E) else Color.White)
                            )
                            {
                                Card(
                                    shape = RoundedCornerShape(percent = 10),
                                    modifier = Modifier.weight(0.7f)
                                ) {
                                    AsyncImage(
                                        model = article.image,
                                        contentDescription = "Image",
                                        contentScale = ContentScale.Crop,
                                        error = painterResource(id = R.drawable.notfound)
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .padding(all = 16.dp)
                                ) {
                                    Text(
                                        text = article.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.weight(1f)
                                    )

                                    Text(
                                        text = article.date,
                                        color = Color.Gray,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                }

                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
            else if (newsViewModel.isLoading)
            {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
                {
                    CircularProgressIndicator()
                }
            }
            
            if(newsViewModel.isException)
            {

                AlertDialog(
                    onDismissRequest = { 
                   newsViewModel.isException = false
                },
                    confirmButton = {
                        TextButton(onClick = { newsViewModel.isException = false }) {
                            Text(text = "Ok", fontSize = 16.sp)
                        }
                    },
                    title = {
                            Text(
                                text = "Error Occurred While Loading!",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                    },
                    containerColor = Color.White,
                    text = {
                        Text(
                            text = "Error occurred while loading news, Please check your connection and try again",
                            fontSize = 16.sp
                            )
                    }
                    
                    )
            }



        }
    }

}