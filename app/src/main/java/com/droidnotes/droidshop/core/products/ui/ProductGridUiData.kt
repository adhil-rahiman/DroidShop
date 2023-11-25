package com.droidnotes.droidshop.core.products.ui

import com.droidnotes.droidshop.features.home.HomeUiState
import com.droidnotes.droidshop.ui.base.UiEvent

sealed class ProductGridUiEvent : UiEvent {
    object PageLoadedEvent : ProductGridUiEvent()
    data class LoadProductsEvent(val page: Int) : ProductGridUiEvent()
    data class SearchEvent(val query: String) : ProductGridUiEvent()
    data class SortEvent(val sort: Sorting) : ProductGridUiEvent()
    data class FilterEvent(val filter: Set<String>) : ProductGridUiEvent()
}

sealed class ProductItemUiEvent : UiEvent {
    object ClickProduct : ProductItemUiEvent()
    object AddToCart : ProductItemUiEvent()
}