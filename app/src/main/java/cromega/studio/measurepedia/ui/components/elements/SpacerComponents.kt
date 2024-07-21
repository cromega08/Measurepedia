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
    Spacer(
        modifier =
            Modifier
                .height(10.dp)
                .then(modifier)
    )

@Composable
fun SpacerHorizontalSmall(modifier: Modifier = Modifier) =
    Spacer(
        modifier =
            Modifier
                .width(10.dp)
                .then(modifier)
    )

@Composable
fun SpacerVerticalLine(
    modifier: Modifier = Modifier,
    height: Dp = 0.dp,
    width: Dp = 2.5.dp,
    color: Color = Color.Black
) {
    val finalModifier: Modifier =
        Modifier
            .width(width)
            .background(color = color)
            .then(modifier)

    if (height > 0.dp)
        finalModifier.height(height)

    Spacer(modifier = finalModifier)
}
