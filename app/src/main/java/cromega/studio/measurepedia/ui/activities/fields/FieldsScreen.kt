package cromega.studio.measurepedia.ui.activities.fields

import android.content.res.Resources
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import cromega.studio.measurepedia.ui.activities.generic.ActivityScreen

class FieldsScreen(
    viewModel: FieldsViewModel,
    resources: Resources
): ActivityScreen<FieldsViewModel>(
    viewModel = viewModel,
    resources = resources
) {
    @Composable
    override fun Header(): Nothing = TODO()

    @Composable()
    override fun Main(paddingValues: PaddingValues): Nothing = TODO()

    @Composable
    override fun Footer(): Nothing = TODO()
}