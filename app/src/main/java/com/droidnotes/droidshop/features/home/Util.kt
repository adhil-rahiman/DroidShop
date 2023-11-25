package com.droidnotes.droidshop.features.home

import com.droidnotes.droidshop.core.products.ui.Sorting
import com.droidnotes.droidshop.data.productDiscovery.repo.ProductRequest
import com.droidnotes.droidshop.mockData.MockData
import kotlin.math.ceil

fun nextPageAvailable(homeUiState: HomeUiState) =
    homeUiState.page == 0 || (homeUiState.page <= ceil(homeUiState.totalItems.toDouble() / limitPerPage.toDouble())
            && homeUiState.products.size <= homeUiState.totalItems)

fun shouldResetHomeUiData(
    homeUiData: HomeUiState, query: String, sorting: Sorting, filter: Set<String>
) = (query.equals(homeUiData.query, ignoreCase = true)
    .not() || homeUiData.sortBy != sorting || homeUiData.filter != filter)

fun defaultHomeUiState() = HomeUiState(
    arrayListOf(),
    arrayListOf(),
    "",
    0,
    limitPerPage,
    MockData.mockProductResponse.size,
    LoadingTypes.FULL_LOADER
)

fun defaultProductRequest() = ProductRequest(
    page = 1, query = ""
)

fun HomeUiState.setHomeUiData(
    query: String,
    sorting: Sorting,
    filter: Set<String>
) {
    page = 1
    this.query = query
    loadingTypes = LoadingTypes.LOAD_MORE
    sortBy = sorting
    this.filter = filter
    products.clear()
}