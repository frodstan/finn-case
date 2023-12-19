package com.example.style.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun FinnImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String?,
    scaleCrop: Boolean = false,
) {
    AsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentScale = if (scaleCrop) {
            ContentScale.Crop
        } else {
            ContentScale.Fit
        },
        contentDescription = contentDescription,
    )
}