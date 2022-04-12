package com.example.places_with_weather_app.ui.theme.resources

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppDimens(
    val baseSmall: Dp = dimenBaseSmall,
    val baseMedium: Dp = dimenBaseMedium,
    val baseLarge: Dp = dimenBaseLarge,
    val baseExtra: Dp = dimenBaseExtra,
) {
    val bottomSheet = BottomSheet(
        bottomPadding = baseMedium,
        itemsHeight = (2 * baseExtra.value).dp,
        bottomSheetPaddingHorizontal = baseMedium,
        spacerWidth = baseLarge,
        topViewWidth = (2 * baseExtra.value).dp,
        topViewHeight = (baseSmall.value / 2).dp
    )

    data class BottomSheet(
        val bottomPadding: Dp,
        val itemsHeight: Dp,
        val bottomSheetPaddingHorizontal: Dp,
        val spacerWidth: Dp,
        val topViewWidth: Dp,
        val topViewHeight: Dp
    )
}

