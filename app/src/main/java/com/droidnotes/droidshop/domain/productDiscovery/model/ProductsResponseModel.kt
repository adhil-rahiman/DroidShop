package com.droidnotes.droidshop.domain.productDiscovery.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductsResponseModel(
    val status: String,
    val totalResults: Int,
    val products: MutableList<ProductModel>,
    val brands: MutableList<String>,
)