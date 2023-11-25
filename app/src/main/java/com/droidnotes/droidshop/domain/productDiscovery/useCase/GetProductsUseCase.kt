package com.droidnotes.droidshop.domain.productDiscovery.useCase

import com.droidnotes.droidshop.common.kotlin.DataWrapper
import com.droidnotes.droidshop.common.kotlin.coroutines.Dispatcher
import com.droidnotes.droidshop.common.kotlin.coroutines.DroidDispatchers
import com.droidnotes.droidshop.data.productDiscovery.repo.ProductRequest
import com.droidnotes.droidshop.domain.productDiscovery.model.ProductsResponseModel
import com.droidnotes.droidshop.domain.productDiscovery.repo.ProductsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productsRepository: ProductsRepository,
    @Dispatcher(DroidDispatchers.IO) private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(request: ProductRequest): DataWrapper<ProductsResponseModel> =
        withContext(coroutineDispatcher) {
            productsRepository.getProducts(request)
        }
}