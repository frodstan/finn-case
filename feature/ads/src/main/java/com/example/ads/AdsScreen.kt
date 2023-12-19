package com.example.ads

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ads.components.AdItem
import com.example.ads.model.AdItemUi
import com.example.ads.model.AdsContentUi
import org.koin.androidx.compose.koinViewModel


@Composable
fun AdsScreen(viewModel: AdsViewModel = koinViewModel()) {
    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            val adsState by viewModel.adsState.collectAsState()
            AdsTopAppBar(
                title = stringResource(id = R.string.ads_for_you_title),
                filterFavourites = adsState.filterFavourites,
                onFilterFavouritesClick = {
                    viewModel.onFilterFavouritesClick()
                }
            )

            when(val currentAdsState = adsState.content) {
                is AdsContentUi.AdsContent -> {
                    AdsList(
                        state = currentAdsState,
                        onItemFavourite = { item ->
                            viewModel.onItemFavourite(item)
                        }
                    )
                }
                is AdsContentUi.Loading -> {
                    LoadingIndicatorContent()
                }
                is AdsContentUi.Error -> {
                    Text(text = stringResource(id = R.string.error))
                }
            }
        }
    }
}

@Composable
private fun AdsList(
    state: AdsContentUi.AdsContent,
    onItemFavourite: (AdItemUi) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(state.items) { item ->
            AdItem(
                item = item,
                onFavouriteClick = { onItemFavourite(item) }
            )
        }
    }
}

@Composable
private fun LoadingIndicatorContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(modifier = Modifier.size(24.dp))
        Text(text = stringResource(id = R.string.loading))
    }
}