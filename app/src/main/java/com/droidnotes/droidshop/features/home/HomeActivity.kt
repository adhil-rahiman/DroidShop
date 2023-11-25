package com.droidnotes.droidshop.features.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.droidnotes.droidshop.core.products.ui.ProductGridScreen
import com.droidnotes.droidshop.core.products.ui.ProductGridUiEvent
import com.droidnotes.droidshop.domain.productDiscovery.model.ProductModel
import com.droidnotes.droidshop.ui.base.UiState
import com.droidnotes.droidshop.ui.theme.DroidShopTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val homeViewModel: HomeViewModel = hiltViewModel()
            homeViewModel.initSearch()
            LaunchedEffect(key1 = Unit) {
                homeViewModel.fetchProducts()
            }
            val uiState: State<UiState<HomeUiState>> = homeViewModel.homeUiState.collectAsState()

            DroidShopTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProductGridScreen(
                        Modifier,
                        uiState.value
                    ) {
                        when (it) {
                            is ProductGridUiEvent.LoadProductsEvent -> {
                                homeViewModel.fetchProducts()
                            }
                            ProductGridUiEvent.PageLoadedEvent -> {}
                            is ProductGridUiEvent.SearchEvent -> {
                                homeViewModel.updateInput(it.query)
                            }
                            is ProductGridUiEvent.FilterEvent -> {
                                homeViewModel.filterProducts(it.filter)
                            }
                            is ProductGridUiEvent.SortEvent -> {
                                homeViewModel.sortProducts(it.sort)
                            }
                        }
                    }
                }
            }
        }
    }
}