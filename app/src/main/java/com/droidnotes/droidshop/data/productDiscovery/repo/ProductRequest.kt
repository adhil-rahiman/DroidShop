package com.droidnotes.droidshop.data.productDiscovery.repo

import com.droidnotes.droidshop.core.products.ui.Sorting

data class ProductRequest(
    var page: Int,
    var query: String = "",
    var sortBy: Sorting = Sorting.None(),
    var filter: Set<String> = setOf()
)
