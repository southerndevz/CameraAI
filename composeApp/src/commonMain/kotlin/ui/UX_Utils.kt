package ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp

expect val systemBarsPadding:SystemBarsPadding


data class SystemBarsPadding(
    val hasMobileAppBar:Boolean = false,
    val statusBarSpacerPVs: PaddingValues = PaddingValues(vertical = if (hasMobileAppBar) 16.dp else 0.dp),
    val navigationBarSpacerPVs: PaddingValues = PaddingValues(vertical = 8.dp)
)