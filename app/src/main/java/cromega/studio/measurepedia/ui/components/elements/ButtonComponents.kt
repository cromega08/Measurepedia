package cromega.studio.measurepedia.ui.components.elements

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RoundedCornerButton(
    modifier: Modifier = Modifier,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
    cornerSize: Dp = 10.dp,
    onClick: () -> Unit,
    content: @Composable (RowScope.() -> Unit)
) =
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(cornerSize),
        colors = buttonColors,
        onClick = onClick,
        content = content
    )
