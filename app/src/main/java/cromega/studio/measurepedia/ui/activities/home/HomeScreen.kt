package cromega.studio.measurepedia.ui.activities.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cromega.studio.measurepedia.R
import cromega.studio.measurepedia.utils.ResourcesUtils

object HomeScreen {

    @Composable
    fun Screen() =
        Scaffold(
            topBar = { Header() },
            content = { Main(it) },
            bottomBar = { Footer() }
        )

    @Composable
    fun Header() {
        Column {
            Text(text = ResourcesUtils.getString(R.string.app_name))
        }
    }

    @Composable
    fun Main(paddingValues: PaddingValues) {
    }

    @Composable
    fun Footer() {
        Column {
            Text(text = ResourcesUtils.getString(R.string.developer_name))
        }
    }
}