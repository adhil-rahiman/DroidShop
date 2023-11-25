package com.droidnotes.droidshop.ui.design

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droidnotes.droidshop.R
import com.droidnotes.droidshop.ui.base.UiEvent

sealed class SearchBoxUiEvent : UiEvent {
    data class EnterSearchQuery(val value: String) : SearchBoxUiEvent()
    object ClickFilter : SearchBoxUiEvent()
    object ClickSort : SearchBoxUiEvent()
}

@Composable
fun SearchBox(
    uiEvents: (SearchBoxUiEvent) -> Unit
) {
    var searchStr by remember {
        mutableStateOf("")
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(1.dp)
            ),
        horizontalArrangement = Arrangement.Center
    ) {
        TextField(modifier = Modifier
            .fillMaxWidth()
            .height(55.dp), value = searchStr,
            onValueChange = {
                searchStr = it
                uiEvents(SearchBoxUiEvent.EnterSearchQuery(it))
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.search_products),
                    color = Color.LightGray,
                    fontSize = 13.sp
                )
            },
            singleLine = true,
            trailingIcon = {
                Row {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(shape = CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                            .clickable {
                                uiEvents(SearchBoxUiEvent.ClickSort)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = DroidIcons.Sort,
                            tint = Color.White,
                            contentDescription = stringResource(R.string.sort),
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(shape = CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                            .clickable {
                                uiEvents(SearchBoxUiEvent.ClickFilter)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = DroidIcons.Filter,
                            tint = Color.White,
                            contentDescription = stringResource(R.string.filter),
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }, colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search Icon", tint = MaterialTheme.colorScheme.secondary
                )
            }
        )


    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchBox() {
//    SearchBox()
}