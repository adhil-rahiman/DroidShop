package com.droidnotes.droidshop.features.home

import com.droidnotes.droidshop.core.products.ui.Sorting
import com.droidnotes.droidshop.domain.productDiscovery.model.ProductModel

const val limitPerPage = 10

enum class LoadingTypes {
    NO_LOADER,
    FULL_LOADER,
    LOAD_MORE
}

data class HomeUiState(
    var products: MutableList<ProductModel>,
    var brands: MutableList<String>,
    var query: String = "",
    var page: Int = 1,
    val limitPerPage: Int = com.droidnotes.droidshop.features.home.limitPerPage,
    val totalItems: Int = 0,
    var loadingTypes: LoadingTypes = LoadingTypes.FULL_LOADER,
    var sortBy: Sorting = Sorting.None(),
    var filter: Set<String> = setOf(),
)