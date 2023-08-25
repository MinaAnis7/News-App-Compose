package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.newsapp.navigation.AppNavigation
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.ui.theme.PrimaryColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController

var darkTheme:Boolean by mutableStateOf(value = false)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val sharedPreferences = getSharedPreferences("mode", MODE_PRIVATE)
            darkTheme = sharedPreferences.getBoolean("mode", false)

            NewsAppTheme(darkTheme = darkTheme) {
                // A surface container using the 'background' color from the theme
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = if (darkTheme) Color(0xFF3F2E3E) else PrimaryColor,
                        darkIcons = false
                    )
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(sharedPreferences)
                }
            }
        }
    }
}