package com.example.ads.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ads.R
import com.example.ads.model.AdItemUi
import com.example.style.theme.FinnCaseTheme

@Composable
fun AdItem(
    modifier: Modifier = Modifier,
    item: AdItemUi,
    onFavouriteClick: () -> Unit,
) {
    Surface(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            AdItemImage(
                imageUrl = null,
                isFavourite = item.isFavourite,
                price = 100,
                onFavouriteClick = { onFavouriteClick() }
            )

            Text(text = "Location")
            if (item.title != null) {
                Text(text = item.title)
            }
        }
    }
}

@Composable
private fun AdItemImage(
    imageUrl: String?,
    isFavourite: Boolean,
    price: Int?,
    onFavouriteClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        if (imageUrl != null) {
            Box(modifier = Modifier)
        } else {
            Box(
                modifier = Modifier
                    .aspectRatio(16f / 9f)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(id = R.string.ads_missing_image_text))
            }
        }

        IconButton(
            modifier = Modifier.align(Alignment.TopEnd),
            onClick = { onFavouriteClick() }
        ) {
            AnimatedContent(
                targetState = isFavourite,
                label = "favourite_heart_icon_anim"
            ) { favouriteIcon ->
                if (favouriteIcon) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = stringResource(id = R.string.content_description_favourite)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = stringResource(id = R.string.content_description_not_favourite)
                    )
                }
            }
        }

        if (price != null) {
            Text(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(percent = 100)
                    )
                    .padding(4.dp),
                text = price.toString()
            )
        }
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    FinnCaseTheme {
        AdItem(
            item = AdItemUi(
                id = "",
                title = "This is a sample title for an item",
                isFavourite = false,
                favouriteItemType = null
            ),
            onFavouriteClick = { }
        )
    }
}