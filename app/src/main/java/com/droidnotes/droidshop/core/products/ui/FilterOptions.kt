package com.droidnotes.droidshop.core.products.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.droidnotes.droidshop.R
import com.droidnotes.droidshop.ui.theme.DroidShopTheme

@Composable
fun FilterOptions(
    options: List<String>,
    selectedOptions: MutableState<Set<String>>
) {
    Column {
        Text(
            modifier = Modifier.padding(horizontal = 32.dp),
            text = stringResource(R.string.filter),
            style = MaterialTheme.typography.headlineSmall
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 32.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp),
                text = stringResource(R.string.brands),
                style = MaterialTheme.typography.titleMedium
            )
        }
        options.forEach { option ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable {
                        onCheckChanged(
                            selectedOptions,
                            selectedOptions.value
                                .contains(option)
                                .not(),
                            option
                        )
                    },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = selectedOptions.value.contains(option),
                    onCheckedChange = { selected ->
                        onCheckChanged(selectedOptions, selected, option)
                    }
                )
                Text(
                    text = option,
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

private fun onCheckChanged(
    selectedOptions: MutableState<Set<String>>,
    selected: Boolean,
    option: String
) {
    val currentSelected = selectedOptions.value.toMutableSet()
    if (selected) {
        currentSelected.add(option)
    } else {
        currentSelected.remove(option)
    }
    selectedOptions.value = currentSelected
    Log.d("value", selectedOptions.value.toString())
}

@Preview(showBackground = true)
@Composable
fun FilterOptionsPreview() {
    DroidShopTheme {

    }
}