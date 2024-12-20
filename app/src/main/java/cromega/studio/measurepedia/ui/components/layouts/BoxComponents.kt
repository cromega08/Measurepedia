package cromega.studio.measurepedia.ui.components.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.dp
import cromega.studio.measurepedia.ui.components.elements.TextSmall
import cromega.studio.measurepedia.ui.components.elements.VerticalArrowsIcon

@Composable
fun <T> Dropdown(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    textColor: Color = Color.Unspecified,
    expanded: Boolean = false,
    option: T,
    options: Array<T>,
    extractOptionName: (T) -> String,
    onOptionSelected: (T) -> Unit,
    onClickMenu: () -> Unit
) =
    Box(
        modifier =
        modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(5.dp))
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clickable(onClick = onClickMenu),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextSmall( text = extractOptionName(option), textColor = textColor)
            VerticalArrowsIcon(arrowUp = expanded)
        }

        DropdownMenu(
            modifier =
                Modifier
                    .background(color = backgroundColor),
            expanded = expanded,
            onDismissRequest = onClickMenu
        ) {
            options.forEach {
                DropdownMenuItem(
                    text = { Text(text = extractOptionName(it), color = textColor) },
                    onClick = { onOptionSelected(it) }
                )
            }
        }
    }

@Composable
fun FinalBackgroundBox(
    backgroundColor: Color,
    oppositeColor: Color,
    content: @Composable (BoxScope.() -> Unit)
) =
    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .drawBehind {
                val canvasWidth = size.width
                val canvasHeight = size.height

                drawRect(
                    color = oppositeColor,
                    size = Size(canvasWidth, (canvasHeight / 7) * 3)
                )

                drawArc(
                    color = backgroundColor,
                    startAngle = 0f,
                    sweepAngle = -180f,
                    useCenter = false,
                    topLeft = Offset(x = -(canvasWidth / 10), y = (canvasHeight / 10) * 2.5f),
                    size = Size((canvasWidth / 10) * 12, canvasHeight / 2),
                    style = Fill
                )
            },
        content = content
    )
