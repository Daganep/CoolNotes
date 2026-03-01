package com.openkin.presentation.ui.splash

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.openkin.presentation.R
import com.openkin.presentation.ui.theme.white
import kotlinx.coroutines.delay

@Composable
fun Splash(home: () -> Unit) {
    var isSplashScreenShown by remember { mutableStateOf(true) }
    Box(
        modifier = Modifier.fillMaxSize().background(color = white),
        contentAlignment = Alignment.Center
    ) {
        val context = LocalContext.current
        val imageLoader = ImageLoader.Builder(context)
            .components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
        if (isSplashScreenShown) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(R.drawable.splash_gif)
                    .crossfade(true)
                    .build(),
                imageLoader = imageLoader,
                contentDescription = "This is a local GIF",
                modifier = Modifier.size(150.dp)
            )
        }
    }
    LaunchedEffect(isSplashScreenShown) {
        delay(timeMillis = 2000) //Время отображения Splash экрана
        home()
        isSplashScreenShown = false
    }
}
