package com.droidnotes.droidshop.data.productDiscovery.di

import com.droidnotes.droidshop.data.productDiscovery.repo.MockProductsRepositoryImpl
import com.droidnotes.droidshop.domain.productDiscovery.repo.ProductsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductsDataModule {

    @Binds
    abstract fun bindsProductsRepo(
        productsRepository: MockProductsRepositoryImpl
    ): ProductsRepository

}