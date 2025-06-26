package com.example.mvi_test.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CommonLazyColumn(
    list: List<String> = listOf("aaaaa", "bbbb","ccccc","ddddd","eeeeee"),
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        itemsIndexed(list) { index, data ->
            CommonListItem(
                onClick = onClick
            )
            if(index != list.size -1){
                HorizontalDivider()
            }
        }
    }
}

@Preview
@Composable
private fun CommonLazyColumnPreview() {
    CommonLazyColumn()
}

@Composable
fun CommonListItem(
    title: String = "Title",
    onClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .clickable { onClick(title) }
            .background(Color.LightGray),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = title,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview
@Composable
private fun CommonListItemPreview() {
    CommonListItem()
}