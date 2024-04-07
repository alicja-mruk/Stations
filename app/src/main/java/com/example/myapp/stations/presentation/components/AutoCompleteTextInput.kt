package com.example.myapp.stations.presentation.components

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize

data class AutoCompleteTextInputItem(
    val id: Long,
    val label: String,
    val keyword: String? = null,
)

@Composable
fun AutoCompleteTextInput(
    searchOnlyByKeyword: Boolean = false,
    data: List<AutoCompleteTextInputItem>,
    label: String?,
    @StringRes placeholderRes: Int,
    onSelect: (item: AutoCompleteTextInputItem) -> Unit
) {
    val context = LocalContext.current

    var textInputValue by remember { mutableStateOf("") }

    val heightTextFields by remember {
        mutableStateOf(55.dp)
    }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    var expanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    fun getKeywordText(autoCompleteTextInputItem: AutoCompleteTextInputItem): String {
        val searchText =
            if (searchOnlyByKeyword) autoCompleteTextInputItem.keyword
                ?: autoCompleteTextInputItem.label
            else autoCompleteTextInputItem.label

        return searchText.lowercase()
    }

    fun getFilteredData(): List<AutoCompleteTextInputItem> {
        return if (textInputValue.isNotEmpty())
            data.filter { autoCompleteTextInputItem ->
                val keywordText = getKeywordText(autoCompleteTextInputItem)
                keywordText.contains(textInputValue.lowercase())
            } else data
    }

    Column(
        modifier = Modifier
            .padding(30.dp)
            .fillMaxWidth()
            .clickable(interactionSource = interactionSource, indication = null, onClick = {
                expanded = false
            })
    ) {
        label?.let {
            Text(
                modifier = Modifier.padding(start = 3.dp, bottom = 2.dp),
                text = it,
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(modifier = Modifier
                    .fillMaxWidth()
                    .height(heightTextFields)
                    .border(
                        width = 1.8.dp, color = Color.Black, shape = RoundedCornerShape(15.dp)
                    )
                    .onGloballyPositioned { coordinates ->
                        textFieldSize = coordinates.size.toSize()
                    },
                    value = textInputValue,
                    onValueChange = {
                        textInputValue = it
                        expanded = true
                    },
                    placeholder = { Text(context.getString(placeholderRes)) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        cursorColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    textStyle = TextStyle(
                        color = Color.Black, fontSize = 16.sp
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = Icons.Rounded.KeyboardArrowDown,
                                contentDescription = "arrow",
                                tint = Color.Black
                            )
                        }
                    })
            }

            AnimatedVisibility(visible = expanded) {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .width(textFieldSize.width.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier.heightIn(max = 300.dp),
                    ) {
                        val filteredData = getFilteredData()

                        items(
                            filteredData
                        ) {
                            ItemsCategory(it) { item ->
                                textInputValue = item.label
                                onSelect(item)
                                expanded = false
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ItemsCategory(
    item: AutoCompleteTextInputItem,
    onSelect: (item: AutoCompleteTextInputItem) -> Unit
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            onSelect(item)
        }
        .padding(10.dp)) {
        Text(text = item.label, fontSize = 16.sp)
    }
}