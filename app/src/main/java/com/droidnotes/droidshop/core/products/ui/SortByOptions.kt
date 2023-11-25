package com.droidnotes.droidshop.core.products.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.droidnotes.droidshop.R
import com.droidnotes.droidshop.core.products.ui.Sorting.*

sealed class Sorting(val nameRes: Int) {
    data class PriceLowToHigh(val resId: Int = R.string.price_low_to_high) : Sorting(resId)
    data class PriceHighToLow(val resId: Int = R.string.price_high_to_low) : Sorting(resId)
    data class Name(val resId: Int = R.string.name) : Sorting(resId)
    data class BrandName(val resId: Int = R.string.brand_name) : Sorting(resId)
    data class None(val resId: Int = R.string.key_default) : Sorting(resId)
}

@Composable
fun SortByOptions(
    selectedOption: Sorting,
    selectSort: (sorting: Sorting) -> Unit,
) {
    val radioOptions = listOf(None(), PriceLowToHigh(), PriceHighToLow(), Name(), BrandName())
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(selectedOption) }
    Column {
        Text(
            modifier = Modifier.padding(horizontal = 32.dp),
            text = stringResource(R.string.sort_by),
            style = MaterialTheme.typography.headlineSmall
        )
        radioOptions.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (option == selectedOption),
                        onClick = {
                            selectSort(option)
                            onOptionSelected(option)
                        }
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = CenterVertically
            ) {
                RadioButton(
                    selected = (option == selectedOption),
                    onClick = {
                        selectSort(option)
                        onOptionSelected(option)
                    }
                )
                Text(
                    text = stringResource(id = option.nameRes),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(
                modifier = Modifier
                    .padding(horizontal = 36.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
        }
    }
}