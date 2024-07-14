package cromega.studio.measurepedia.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun SearchIcon() = Icon(imageVector = Icons.Default.Search, contentDescription = Icons.Default.Search.name)

@Composable
fun ClearIcon() = Icon(imageVector = Icons.Default.Clear, contentDescription = Icons.Default.Clear.name)

@Composable
fun VerticalArrowsIcon(arrowUp: Boolean = false)
{
    val arrow: ImageVector = if (arrowUp) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown

    Icon(
        imageVector = arrow,
        contentDescription = arrow.name
    )
}
