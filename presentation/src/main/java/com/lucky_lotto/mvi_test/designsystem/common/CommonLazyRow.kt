package com.lucky_lotto.mvi_test.designsystem.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lucky_lotto.domain.model.Keyword
import com.lucky_lotto.mvi_test.ui.theme.CommonStyle
import com.lucky_lotto.mvi_test.ui.theme.DarkGray
import com.lucky_lotto.mvi_test.ui.theme.ScreenBackground

@Composable
fun CommonLazyRow(
    keywordList: List<Keyword> = listOf(Keyword(), Keyword(), Keyword(), Keyword(), Keyword()),
    removable: Boolean = true,
    onClickChip: (String) -> Unit = {},
    onClickDelete: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyRow (
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        itemsIndexed(keywordList) { index, data ->
            CommonChip(
                keyword = data,
                removable = removable,
                onClickChip = onClickChip,
                onClickDelete = onClickDelete
            )
        }
    }
}

@Preview
@Composable
private fun CommonLazyRowPreview() {
    CommonLazyRow()
}

@Composable
fun CommonFlowRow(
    keywordList: List<Keyword> = listOf(Keyword(), Keyword(), Keyword(), Keyword(), Keyword()),
    removable: Boolean = true,
    onClickChip: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    FlowRow (
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        keywordList.forEach { data ->
            CommonChip(
                keyword = data,
                removable = removable,
                onClickChip = onClickChip,
                onClickDelete = {}
            )
        }
    }
}

@Preview
@Composable
private fun CommonFlowRowPreview() {
    CommonFlowRow()
}

@Composable
fun CommonChip(
    keyword: Keyword = Keyword(),
    removable: Boolean = true,
    onClickChip: (String) -> Unit = {},
    onClickDelete: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    AssistChip(
        modifier = modifier,
        onClick = { onClickChip(keyword.title) },
        label = { Text(text = keyword.title, style = CommonStyle.text14) },
        shape = RoundedCornerShape(10.dp),
        colors = AssistChipDefaults.assistChipColors(containerColor = ScreenBackground),
        border = null,
        trailingIcon = {
            if(removable) {
                Icon(
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .clickable { onClickDelete(keyword.id) }
                        .size(16.dp),
                    imageVector = Icons.Default.Clear,
                    contentDescription = "remove",
                    tint = DarkGray
                )
            }
        }
    )
}

@Preview
@Composable
private fun CommonListItemPreview() {
    CommonChip()
}