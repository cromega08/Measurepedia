package cromega.studio.measurepedia.ui.activities.fields

import android.content.res.Resources
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cromega.studio.measurepedia.ui.activities.generic.ActivityScreen

class FieldsScreen(
    viewModel: FieldsViewModel,
    resources: Resources
): ActivityScreen<FieldsViewModel>(
    viewModel = viewModel,
    resources = resources
) {
    @Composable
    override fun Header() = Spacer(modifier = Modifier)

    @Composable()
    override fun Main(paddingValues: PaddingValues) =
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            Text("Rendered")
        }

    @Composable
    override fun Footer() = Spacer(modifier = Modifier)
}