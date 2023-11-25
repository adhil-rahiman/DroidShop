package com.droidnotes.droidshop.data.productDiscovery.repo

import com.droidnotes.droidshop.common.kotlin.DataWrapper
import com.droidnotes.droidshop.common.kotlin.coroutines.Dispatcher
import com.droidnotes.droidshop.common.kotlin.coroutines.DroidDispatchers.IO
import com.droidnotes.droidshop.core.products.ui.Sorting
import com.droidnotes.droidshop.data.productDiscovery.network.api.ProductsApi
import com.droidnotes.droidshop.domain.productDiscovery.model.ProductModel
import com.droidnotes.droidshop.domain.productDiscovery.model.ProductsResponseModel
import com.droidnotes.droidshop.domain.productDiscovery.repo.ProductsRepository
import com.droidnotes.droidshop.mockData.MockData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import javax.inject.Inject

class MockProductsRepositoryImpl @Inject constructor(
    private val apiService: ProductsApi,
    @Dispatcher(IO) private val dispatcher: CoroutineDispatcher
) : ProductsRepository {

    override suspend fun getProducts(request: ProductRequest): DataWrapper<ProductsResponseModel> {
        delay(2000)
        val response =
            when (request.sortBy) {
                is Sorting.BrandName -> MockData.mockProductResponse.sortedBy { it.brand }
                is Sorting.Name -> MockData.mockProductResponse.sortedBy { it.name }
                is Sorting.PriceHighToLow -> MockData.mockProductResponse.sortedByDescending { it.price }
                is Sorting.PriceLowToHigh -> MockData.mockProductResponse.sortedBy { it.price }
                is Sorting.None -> MockData.mockProductResponse.sortedBy { it.id }
            }

        val filteredList = response.filterIndexed { index, productModel ->
            (productModel.name.contains(
                request.query,
                ignoreCase = true
            ) || productModel.brand.contains(request.query, ignoreCase = true))
                    && (request.filter.isEmpty() || request.filter.contains(productModel.brand))
        }.toMutableList()

        val brands = response.distinctBy {
            it.brand
        }.map {
            it.brand
        }.toMutableList()

        val subList: MutableList<ProductModel> = when {
            (request.page * 10) - 10 >= filteredList.size -> mutableListOf()
            (request.page * 10) - 1 >= filteredList.size -> filteredList.subList(
                (request.page * 10) - 10,
                filteredList.size - 1
            )

            else -> filteredList.subList((request.page * 10) - 10, (request.page * 10))
        }

        return DataWrapper.Success(
            ProductsResponseModel(
                "200",
                53,
                subList,
                brands
            )
        )
    }
}