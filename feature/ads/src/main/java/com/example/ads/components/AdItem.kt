package com.example.ads.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ads.R
import com.example.ads.model.AdItemUi
import com.example.style.components.FinnImage
import com.example.style.theme.FinnCaseTheme

@Composable
fun AdItem(
    modifier: Modifier = Modifier,
    item: AdItemUi,
    onItemClick: () -> Unit,
    onFavouriteClick: () -> Unit,
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 4.dp,
        shape = MaterialTheme.shapes.large,
        onClick = { onItemClick() }
    ) {
        Column(
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            AdItemImage(
                imageUrl = item.imageUrl,
                isFavourite = item.isFavourite,
                price = item.price,
                onFavouriteClick = { onFavouriteClick() }
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (item.location != null) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = item.location,
                    style = MaterialTheme.typography.labelMedium
                )
            }
            if (item.title != null) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 4.dp),
                    text = item.title,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Composable
private fun AdItemImage(
    imageUrl: String?,
    isFavourite: Boolean,
    price: String?,
    onFavouriteClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (imageUrl != null) {
            FinnImage(
                modifier = Modifier
                    .heightIn(min = 128.dp)
                    .fillMaxWidth(),
                imageUrl = imageUrl,
                contentDescription = null,
                scaleCrop = true
            )
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
            modifier = Modifier
                .align(Alignment.TopEnd)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(bottomStartPercent = 50)
                ),
            onClick = { onFavouriteClick() }
        ) {
            AnimatedContent(
                targetState = isFavourite,
                label = "favourite_heart_icon_anim",
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
                        shape = RoundedCornerShape(
                            topEndPercent = 100
                        )
                    )
                    .padding(vertical = 4.dp)
                    .padding(start = 8.dp, end = 16.dp),
                text = price,
                color = MaterialTheme.colorScheme.onSurface
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
                favouriteItemType = null,
                imageUrl = null,
                location = "Oslo",
                price = "100"
            ),
            onFavouriteClick = { },
            onItemClick = { }
        )
    }
}