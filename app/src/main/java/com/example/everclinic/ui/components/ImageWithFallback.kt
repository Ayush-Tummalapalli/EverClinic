
/**
 * This file contains the ImageWithFallback composable, which displays an image from a URL
 * with a fallback placeholder in case of an error.
 * Mapped from ImageWithFallback.tsx.
 */
package com.example.everclinic.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.everclinic.R

@Composable
fun ImageWithFallback(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .error(R.drawable.ic_launcher_background) // Replace with a proper placeholder drawable
            .build(),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}
