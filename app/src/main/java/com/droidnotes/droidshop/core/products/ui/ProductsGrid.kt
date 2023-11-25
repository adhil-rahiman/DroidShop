package com.droidnotes.droidshop.core.products.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.droidnotes.droidshop.R
import com.droidnotes.droidshop.core.products.ui.Sorting.None
import com.droidnotes.droidshop.domain.productDiscovery.model.ProductModel
import com.droidnotes.droidshop.features.home.HomeUiState
import com.droidnotes.droidshop.features.home.LoadingTypes
import com.droidnotes.droidshop.ui.base.UiState
import com.droidnotes.droidshop.ui.design.DroidWheelLoader
import com.droidnotes.droidshop.ui.design.SearchBox
import com.droidnotes.droidshop.ui.design.SearchBoxUiEvent
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun ProductGridScreen(
    modifier: Modifier = Modifier,
    uiState: UiState<HomeUiState>,
    uiEvents: (ProductGridUiEvent) -> Unit
) {
    Column {
        when (uiState) {
            is UiState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.decathlon),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.displayMedium
                    )
                    DroidWheelLoader(contentDesc = "Loading")
                }
            }

            is UiState.Success -> {
                Box {
                    var showSortBs by remember { mutableStateOf(false) }
                    var showFilterBs by remember { mutableStateOf(false) }
                    var sortBy: Sorting by remember { mutableStateOf(None()) }
                    val filters = remember { mutableStateOf(uiState.data.filter) }

                    Column {
                        Text(
                            text = stringResource(R.string.decathlon),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.displayMedium
                        )

                        SearchBox {
                            when (it) {
                                is SearchBoxUiEvent.EnterSearchQuery -> uiEvents(
                                    ProductGridUiEvent.SearchEvent(
                                        it.value
                                    )
                                )

                                SearchBoxUiEvent.ClickFilter -> {
                                    showFilterBs = true
                                    showSortBs = false
                                }

                                SearchBoxUiEvent.ClickSort -> {
                                    showSortBs = true
                                    showFilterBs = false
                                }
                            }
                        }
                        ProductGrid(
                            uiState.data
                        ) {
                            uiEvents(it)
                        }

                        if (showSortBs) {
                            OptionsBottomSheet(showSortBs, {
                                uiEvents(
                                    ProductGridUiEvent.SortEvent(
                                        sortBy
                                    )
                                )
                            }, {
                                showSortBs = false
                            }) {
                                SortByOptions(uiState.data.sortBy) {
                                    sortBy = it
                                }
                            }
                        } else if (showFilterBs) {
                            OptionsBottomSheet(showFilterBs, {
                                uiEvents(
                                    ProductGridUiEvent.FilterEvent(
                                        filters.value
                                    )
                                )
                            }, {
                                showFilterBs = false
                            }) {
                                FilterOptions(
                                    uiState.data.brands,
                                    filters
                                )
                            }
                        }
                    }
                    if (uiState.data.loadingTypes == LoadingTypes.LOAD_MORE) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Transparent)
                                .clickable {
                                },
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            DroidWheelLoader(contentDesc = stringResource(R.string.loading))
                            Text(
                                text = stringResource(R.string.loading_products),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            is UiState.Error -> {

            }
        }
    }
}

@Composable
fun ProductGrid(
    gridUiState: HomeUiState,
    uiEvents: (ProductGridUiEvent) -> Unit
) {
    val gridState: LazyGridState = rememberLazyGridState()
    var page = remember { mutableIntStateOf(1) }

    val loadMore = remember {
        derivedStateOf {
            val layoutInfo = gridState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

            lastVisibleItemIndex > (totalItemsNumber - 3)
        }
    }

    LaunchedEffect(loadMore) {
        snapshotFlow { loadMore.value }
            .distinctUntilChanged()
            .collect {
                Log.d("GridTest", "loadMore ${loadMore.value}, page - ${page.intValue}")
                if (it) {
                    page.intValue += 1
                    uiEvents(ProductGridUiEvent.LoadProductsEvent(page.intValue))
                }
            }
    }

    LazyVerticalGrid(
        modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant),
        state = gridState,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(gridUiState.products.size, key = {
                gridUiState.products[it].id
            }) { i ->
                ProductCard(product = gridUiState.products[i])
            }
        })

}


@Composable
fun ProductCard(
    product: ProductModel
) {
    Card(
        modifier = Modifier
            .clickable {
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        shape = RoundedCornerShape(1.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(top = 0.dp, bottom = 6.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                placeholder = if (LocalInspectionMode.current) {
                    painterResource(R.drawable.ic_placeholder_default)
                } else {
                    null
                },
                model = product.imgUrl,
                contentScale = ContentScale.Crop,
                contentDescription = product.name
            )
            Text(
                modifier = Modifier.padding(start = 4.dp, top = 2.dp),
                text = product.brand + "-" + product.id,
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                modifier = Modifier.padding(start = 6.dp, top = 4.dp),
                text = product.name,
                style = MaterialTheme.typography.labelMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontSize = 12.sp,
                color = Color.LightGray
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 6.dp, top = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.price_template, product.price),
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                        }
                        .clip(shape = CircleShape)
                        .background(Color.White), contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.ic_cart),
                        contentDescription = "Filter Icon", tint = MaterialTheme.colorScheme.primary
                    )

                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewCard() {
    ProductCard(
        product = ProductModel(
            id = 1,
            name = "Men's Jacket",
            brand = "Adidas",
            imgUrl = "https://contents.mediadecathlon.com/p1692773/33a46f2e8c97968fe17f38c64ef39067/p1692773.jpg",
            price = 999f
        )
    )
}
