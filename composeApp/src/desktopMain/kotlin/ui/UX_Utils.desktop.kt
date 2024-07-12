package ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp

actual val systemBarsPadding: SystemBarsPadding
    get() = SystemBarsPadding(navigationBarSpacerPVs = PaddingValues(all = 0.dp))