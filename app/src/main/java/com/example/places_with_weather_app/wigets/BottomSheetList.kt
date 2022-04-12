package com.example.places_with_weather_app.wigets

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.places_with_weather_app.ui.theme.AppBaseTheme

open class BottomSheetOptions(
    open val title: String,
    open val imageVector: ImageVector = Icons.Filled.Menu,
    open val contentDescription: String = "Menu"
)

@Composable
fun <ITEM : BottomSheetOptions> BottomSheetList(
    items: List<ITEM>,
    modifier: Modifier = Modifier,
    bottomPadding: Dp = AppBaseTheme.dimens.bottomSheet.bottomPadding,
    itemsHeight: Dp = AppBaseTheme.dimens.bottomSheet.itemsHeight,
    titleView: @Composable (() -> Unit)? = null,
    onClick: (item: ITEM) -> Unit
) {
    LazyColumn(modifier = modifier.padding(bottom = bottomPadding)) {

        item {
            TopView()
        }

        titleView?.let {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = AppBaseTheme.dimens.baseMedium),
                    contentAlignment = Alignment.Center
                ) {
                    titleView()
                }
            }
        }
        items(items = items) { item: ITEM ->
            BottomSheetListItem(
                title = item.title,
                height = itemsHeight,
                imageVector = item.imageVector,
                contentDescription = item.contentDescription,
                onItemClick = { onClick(item) }
            )
        }
    }
}

@Composable
private fun BottomSheetListItem(
    title: String,
    imageVector: ImageVector,
    contentDescription: String,
    height: Dp,
    onItemClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(title) })
            .height(height)
            .padding(start = AppBaseTheme.dimens.bottomSheet.bottomSheetPaddingHorizontal),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = imageVector, contentDescription = contentDescription)
        Spacer(modifier = Modifier.width(AppBaseTheme.dimens.bottomSheet.spacerWidth))
        Text(
            text = title,
            modifier = Modifier
                .padding(end = AppBaseTheme.dimens.bottomSheet.bottomSheetPaddingHorizontal)
        )
    }
}

@Composable
private fun TopView() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = AppBaseTheme.dimens.baseMedium),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.size(
                AppBaseTheme.dimens.bottomSheet.topViewWidth,
                AppBaseTheme.dimens.bottomSheet.topViewHeight
            )
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            drawRoundRect(
                color = Color.LightGray,
                cornerRadius = CornerRadius(canvasHeight / 2, canvasHeight / 2),
                size = Size(canvasWidth, canvasHeight)
            )
        }
    }
}

@Preview
@Composable
fun BottomSheetListItemPreview() {
    BottomSheetListItem(title = "title", imageVector = Icons.Filled.Menu, contentDescription = "", height = 64.dp, onItemClick = {})
}