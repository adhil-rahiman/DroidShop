package com.droidnotes.droidshop.data.productDiscovery.di

import com.droidnotes.droidshop.data.productDiscovery.network.api.ProductsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ProductsApiModule {

    @Provides
    fun provideProductsApi(retrofit: Retrofit): ProductsApi = retrofit.create(ProductsApi::class.java)

}
