package cromega.studio.measurepedia.ui.activities.generic

import android.content.res.Resources
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

abstract class ActivityScreen<VM: ActivityViewModel>(
    protected val viewModel: VM,
    protected val resources: Resources
) {
    protected open val screenModifier: Modifier = Modifier

    @Composable
    open fun Screen() =
        Scaffold(
            modifier = screenModifier,
            topBar = { Header() },
            content = { Main(it) },
            bottomBar = { Footer() }
        )

    @Composable
    protected abstract fun Header()

    @Composable
    protected abstract fun Main(paddingValues: PaddingValues)

    @Composable
    protected abstract fun Footer()
}