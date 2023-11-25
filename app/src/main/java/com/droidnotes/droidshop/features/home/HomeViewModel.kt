package com.droidnotes.droidshop.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidnotes.droidshop.core.products.ui.Sorting
import com.droidnotes.droidshop.domain.productDiscovery.useCase.GetProductsUseCase
import com.droidnotes.droidshop.ui.base.LoadingType
import com.droidnotes.droidshop.ui.base.UiState
import com.droidnotes.droidshop.ui.base.mapToUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val productRequest = defaultProductRequest()

    private var _homeUiData: HomeUiState = defaultHomeUiState()

    private val _homeUiState: MutableStateFlow<UiState<HomeUiState>> =
        MutableStateFlow(UiState.Loading(LoadingType.FULL_LOADER))
    val homeUiState: StateFlow<UiState<HomeUiState>> = _homeUiState.asStateFlow()

    private val _inputText: MutableStateFlow<String> =
        MutableStateFlow("")
    private val inputText: StateFlow<String> = _inputText

    @OptIn(FlowPreview::class)
    fun initSearch() {
        viewModelScope.launch {
            inputText.debounce(timeoutMillis = 500).collectLatest { input ->
                searchProducts(input, _homeUiData.sortBy, _homeUiData.filter)
            }
        }
    }

    fun updateInput(inputText: String) {
        _inputText.update { inputText }
    }

    fun sortProducts(sorting: Sorting) {
        if (_homeUiData.sortBy == sorting) return
        searchProducts(_homeUiData.query, sorting, _homeUiData.filter)
    }

    fun filterProducts(filter: Set<String>) {
        if (_homeUiData.filter.equals(filter)) return
        searchProducts(_homeUiData.query, _homeUiData.sortBy, filter)
    }

    fun fetchProducts() {
        searchProducts(_homeUiData.query, _homeUiData.sortBy, _homeUiData.filter)
    }

    private fun searchProducts(
        query: String = "",
        sorting: Sorting = Sorting.None(),
        filter: Set<String> = setOf()
    ) {
        if (shouldResetHomeUiData(_homeUiData, query, sorting, filter)) {
            _homeUiData.setHomeUiData(query, sorting, filter)
        } else {
            _homeUiData.page += 1
        }

        if (nextPageAvailable(_homeUiData).not()) {
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            if (_homeUiData.loadingTypes == LoadingTypes.FULL_LOADER) _homeUiState.emit(
                UiState.Loading(LoadingType.FULL_LOADER)
            ) else {
                _homeUiData = _homeUiData.copy(
                    page = _homeUiData.page,
                    loadingTypes = LoadingTypes.LOAD_MORE
                )
                _homeUiState.emit(UiState.Success(_homeUiData))
            }

            getProductsUseCase(
                productRequest.copy(
                    page = _homeUiData.page,
                    query = _homeUiData.query,
                    sortBy = _homeUiData.sortBy,
                    filter = _homeUiData.filter
                )
            ).run {
                _homeUiState.emit(this.mapToUiState(success = {
                    _homeUiData = _homeUiData.copy(
                        page = _homeUiData.page,
                        products = _homeUiData.products.apply {
                            addAll(it.products)
                        },
                        brands = it.brands,
                        loadingTypes = LoadingTypes.NO_LOADER
                    )
                    _homeUiData
                }))
            }
        }
    }
}