package cromega.studio.measurepedia.ui.components.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun GenericDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties =
        DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
    content: @Composable () -> Unit
) =
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
        content = content
    )

@Composable
fun ColumnOrderedDialog(
    columnModifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    properties: DialogProperties =
        DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
    verticalArrangement: Arrangement.Vertical = Arrangement.SpaceAround,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    content: @Composable (ColumnScope.() -> Unit)
) =
    GenericDialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ) {
        Column(
            modifier =
                columnModifier
                    .padding(all = 10.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            content = content
        )
    }
