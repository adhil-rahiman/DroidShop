package com.droidnotes.droidshop.data.productDiscovery.network.api

import com.droidnotes.droidshop.domain.productDiscovery.model.ProductsResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsApi{

    // https://myapi.org/v2/top-headlines?query=adidas&apiKey=0fef0ew4425r4t5tjh56605049442n43
    @GET("v2/products")
    suspend fun getAllProducts(@Query("query") query: String = ""): ProductsResponseModel
}