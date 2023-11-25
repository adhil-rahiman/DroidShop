package com.droidnotes.droidshop.data.productDiscovery.repo

import com.droidnotes.droidshop.network.apiCall
import com.droidnotes.droidshop.common.kotlin.DataWrapper
import com.droidnotes.droidshop.common.kotlin.coroutines.Dispatcher
import com.droidnotes.droidshop.common.kotlin.coroutines.DroidDispatchers.IO
import com.droidnotes.droidshop.data.productDiscovery.network.api.ProductsApi
import com.droidnotes.droidshop.domain.productDiscovery.model.ProductsResponseModel
import com.droidnotes.droidshop.domain.productDiscovery.repo.ProductsRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val apiService: ProductsApi,
    @Dispatcher(IO) private val dispatcher: CoroutineDispatcher
) : ProductsRepository {

    override suspend fun getProducts(request: ProductRequest): DataWrapper<ProductsResponseModel> {
        return apiCall(dispatcher) {
            apiService.getAllProducts()
        }
    }
}