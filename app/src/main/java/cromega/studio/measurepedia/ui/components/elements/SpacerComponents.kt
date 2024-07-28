package cromega.studio.measurepedia.ui.components.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SpacerVerticalSmall(modifier: Modifier = Modifier) =
    Spacer(modifier = modifier.height(10.dp))

@Composable
fun SpacerVerticalMedium(modifier: Modifier = Modifier) =
    Spacer(modifier = modifier.height(20.dp))

@Composable
fun SpacerHorizontalSmall(modifier: Modifier = Modifier) =
    Spacer(modifier = modifier.width(10.dp))

@Composable
fun SpacerHorizontalLine(
    modifier: Modifier = Modifier,
    height: Dp = 2.5.dp,
    width: Dp = 0.dp,
    color: Color = Color.Black
) {
    val finalModifier: Modifier =
        modifier
            .height(height)
            .background(color = color)

    if (width > 0.dp) finalModifier.width(width)

    Spacer(modifier = finalModifier)
}

@Composable
fun SpacerVerticalLine(
    modifier: Modifier = Modifier,
    height: Dp = 0.dp,
    width: Dp = 2.5.dp,
    color: Color = Color.Black
) {
    val finalModifier: Modifier =
        modifier
            .width(width)
            .background(color = color)

    if (height > 0.dp)
        finalModifier.height(height)

    Spacer(modifier = finalModifier)
}
