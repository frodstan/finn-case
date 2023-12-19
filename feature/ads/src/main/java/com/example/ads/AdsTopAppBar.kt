package com.example.ads

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun AdsTopAppBar(
    title: String,
    filterFavourites: Boolean,
    onFilterFavouritesClick: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Row(
            modifier = Modifier
                .heightIn(min = 64.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )

            IconButton(
                onClick = { onFilterFavouritesClick() }
            ) {
                AnimatedContent(
                    targetState = filterFavourites,
                    label = "favourite_header_heart_icon_anim"
                ) { favouriteIcon ->
                    if (favouriteIcon) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = stringResource(id = R.string.content_description_ads_top_bar_filter_favourite)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = stringResource(id = R.string.content_description_ads_top_bar_filter_all)
                        )
                    }
                }
            }
        }
    }
}