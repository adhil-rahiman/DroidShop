//package com.droidnotes.droidshop.core.products.ui
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.ColumnScope
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.defaultMinSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Button
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.ModalBottomSheet
//import androidx.compose.material3.Text
//import androidx.compose.material3.rememberModalBottomSheetState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.unit.dp
//import com.droidnotes.droidshop.R
//import com.droidnotes.droidshop.domain.productDiscovery.model.ProductModel
//import com.droidnotes.droidshop.ui.base.UiEvent
//import com.droidnotes.droidshop.ui.design.SearchBox
//import com.droidnotes.droidshop.ui.design.SearchBoxUiState
//import kotlinx.coroutines.launch
//
//sealed class ProductSearchListUiEvent : UiEvent {
//    data class OnItemSelected(val value: String) : SearchBoxUiEvent()
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ProductSearchList(
//    show: Boolean,
//    results: List<ProductModel>
//) {
//    var searchText by remember { mutableStateOf("") }
//    var isSearching by remember { mutableStateOf(false) }
//    SearchBox(searchText) {
//
//    }
//    LazyColumn(
//        modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant),
//        verticalArrangement = Arrangement.spacedBy(4.dp)
//    ) {
//        items(results, key = {
//            it
//        }) {
//            Row(modifier = Modifier
//                .fillMaxWidth()
//                .background(Color.White)
//                .clickable {
//                }) {
//                Text(
//                    text = it.name,
//                    style = MaterialTheme.typography.bodyMedium
//                )
//            }
//        }
//    }
//}