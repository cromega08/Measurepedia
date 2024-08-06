package cromega.studio.measurepedia.ui.activities.measures

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import cromega.studio.measurepedia.ui.components.elements.TextTitle
import cromega.studio.measurepedia.ui.components.layouts.GenericBodyLazyColumn
import cromega.studio.measurepedia.ui.components.layouts.GenericFooterRow
import cromega.studio.measurepedia.ui.components.layouts.GenericHeaderColumn

internal object MeasuresScreen
{
    @Composable
    fun Screen() =
        Scaffold(
            topBar = { Header() },
            content = { Main(it) },
            bottomBar = { Footer() }
        )

    @Composable
    fun Header() =
        GenericHeaderColumn {
            TextTitle(text = "Measures")
        }

    @Composable
    fun Main(paddingValues: PaddingValues) =
        GenericBodyLazyColumn(
            contentPadding = paddingValues
        ) {

        }

    @Composable
    fun Footer() =
        GenericFooterRow {

        }
}