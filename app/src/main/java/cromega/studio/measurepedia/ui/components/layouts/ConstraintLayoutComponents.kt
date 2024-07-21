package cromega.studio.measurepedia.ui.components.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope

@Composable
inline fun CardConstraintLayout(
    modifier: Modifier = Modifier,
    crossinline content: @Composable (ConstraintLayoutScope.() -> Unit)
) =
    ConstraintLayout(
        modifier =
            Modifier
                .background(color = Color.LightGray, shape = RoundedCornerShape(15.dp))
                .padding(horizontal = 20.dp, vertical = 15.dp)
                .then(modifier),
        content = content
    )
