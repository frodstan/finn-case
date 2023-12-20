package com.example.ads

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.LocalContext
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
                title = stringResource(id = R.string.ads_title),
                filterFavourites = adsState.isFavouritesFiltered,
                onFilterFavouritesClick = {
                    viewModel.onFilterFavouritesClick()
                }
            )

            when(val currentAdsState = adsState.content) {
                is AdsContentUi.AdsContent -> {
                    val context = LocalContext.current
                    AdsList(
                        state = currentAdsState,
                        onItemClick = { _ ->
                            displayDetailNotImplementedToast(context)
                        },
                        onItemFavourite = { item ->
                            viewModel.onItemFavourite(item)
                        }
                    )
                }
                is AdsContentUi.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        LoadingIndicatorContent()
                    }
                }
                is AdsContentUi.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(text = stringResource(id = R.string.error))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AdsList(
    state: AdsContentUi.AdsContent,
    onItemClick: (AdItemUi) -> Unit,
    onItemFavourite: (AdItemUi) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(
            items = state.items,
            key = { item -> item.id },
            contentType = { item -> item },
        ) { item ->
            AdItem(
                modifier = Modifier.animateItemPlacement(),
                item = item,
                onItemClick = { onItemClick(item) },
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

private fun displayDetailNotImplementedToast(context: Context) {
    Toast.makeText(context, R.string.ads_detail_view_not_implemented, Toast.LENGTH_LONG)
        .show()
}