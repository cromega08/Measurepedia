package cromega.studio.measurepedia.ui.components.elements

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RoundedCornerButton(
    modifier: Modifier = Modifier,
    cornerSize: Dp = 10.dp,
    onClick: () -> Unit,
    content: @Composable (RowScope.() -> Unit)
) =
    Button(
        modifier = Modifier.then(modifier),
        shape = RoundedCornerShape(cornerSize),
        onClick = onClick,
        content = content
    )
