package cromega.studio.measurepedia.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
