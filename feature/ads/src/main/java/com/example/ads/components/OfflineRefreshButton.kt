package com.example.ads.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ads.R

@Composable
fun OfflineRefreshIndicator(
    modifier: Modifier = Modifier,
    onRefreshClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.ads_viewing_offline_text))
        IconButton(onClick = { onRefreshClick() }) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = stringResource(id = R.string.content_description_reload)
            )
        }
    }
}