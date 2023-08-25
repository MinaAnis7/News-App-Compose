package com.example.newsapp.navigation

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.screens.AnimatedSplashScreen
import com.example.newsapp.screens.HomeScreen

@Composable
fun AppNavigation(sharedPreferences: SharedPreferences)
{
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {

        composable("splash")
        {
            AnimatedSplashScreen(navController)
        }

        composable("home")
        {
            HomeScreen(sharedPreferences)
        }
    }
}