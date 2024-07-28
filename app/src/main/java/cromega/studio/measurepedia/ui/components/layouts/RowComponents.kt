package cromega.studio.measurepedia.ui.components.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GenericFooterRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    content: @Composable (RowScope.() -> Unit)
) =
    Row(
        modifier =
        modifier
            .fillMaxWidth()
            .heightIn(min = 1.dp, 100.dp)
            .padding(top = 0.dp, bottom = 35.dp, start = 20.dp, end = 20.dp),
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        content = content
    )
