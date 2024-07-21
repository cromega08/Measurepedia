package cromega.studio.measurepedia.ui.components.elements

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cromega.studio.measurepedia.R

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

@Composable
fun AddIcon() = Icon(imageVector = Icons.Default.Add, contentDescription = Icons.Default.Add.name)

@Composable
fun FaceIcon() = Icon(imageVector = Icons.Default.Face, contentDescription = Icons.Default.Face.name)

@Composable
fun DownloadIcon() =
    Icon(
        painter = painterResource(id = R.drawable.download),
        contentDescription = "",
        modifier = Modifier.size(24.dp)
    )

@Composable
fun UploadIcon() =
    Icon(
        painter = painterResource(id = R.drawable.upload),
        contentDescription = "",
        modifier = Modifier.size(24.dp)
    )

@Composable
fun SettingsIcon() = Icon(imageVector = Icons.Default.Settings, contentDescription = Icons.Default.Settings.name)
