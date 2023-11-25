package com.droidnotes.droidshop.domain.productDiscovery.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductModel(
    val id: Int,
    val name: String,
    val price: Float,
    val brand: String,
    val imgUrl: String
)