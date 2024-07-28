package cromega.studio.measurepedia.ui.components.elements

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cromega.studio.measurepedia.ui.components.layouts.CenteredColumn

@Composable
fun VerticalIconButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    onClick: () -> Unit,
    content: @Composable (ColumnScope.() -> Unit)
) =
    IconButton(
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        onClick = onClick,
        content = { CenteredColumn(content = content) }
    )
