package com.droidnotes.droidshop.domain.productDiscovery.repo

import com.droidnotes.droidshop.common.kotlin.DataWrapper
import com.droidnotes.droidshop.data.productDiscovery.repo.ProductRequest
import com.droidnotes.droidshop.domain.productDiscovery.model.ProductsResponseModel

interface ProductsRepository {
    suspend fun getProducts(request: ProductRequest): DataWrapper<ProductsResponseModel>
}