package com.example.newsapp.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Newspaper
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.newsapp.darkTheme
import com.example.newsapp.ui.theme.PrimaryColor
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(navController: NavController)
{
    var startAnimation by remember {
        mutableStateOf(false)
    }

    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 2500)
        )
    
    LaunchedEffect(key1 = true,)
    {
        startAnimation = true
        delay(4000)
        navController.popBackStack()
        navController.navigate("home")
    }
    Splash(alphaAnim.value)
}

@Composable
fun Splash(alpha: Float)
{
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = if (darkTheme) Color(0xFF3F2E3E) else PrimaryColor),
        contentAlignment = Alignment.Center,
        )
    {
        Column(horizontalAlignment = Alignment.CenterHorizontally)
        {
            Icon(
                imageVector = Icons.Rounded.Newspaper,
                contentDescription = "Splash Icon",
                Modifier.size(120.dp).alpha(alpha),
                tint = Color.White,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "News", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = Color.White,
                modifier = Modifier.alpha(alpha)
                )
        }
    }
}