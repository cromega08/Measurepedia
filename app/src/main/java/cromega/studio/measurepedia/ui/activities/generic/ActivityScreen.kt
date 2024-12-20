package cromega.studio.measurepedia.ui.activities.generic

import android.content.res.Resources
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cromega.studio.measurepedia.ui.theme.ChineseBlack
import cromega.studio.measurepedia.ui.theme.DesertSand
import cromega.studio.measurepedia.ui.theme.LightBrown
import cromega.studio.measurepedia.ui.theme.MellowApricot
import cromega.studio.measurepedia.ui.theme.PapayaWhip
import cromega.studio.measurepedia.ui.theme.Pineapple
import cromega.studio.measurepedia.ui.theme.PureBlack
import cromega.studio.measurepedia.ui.theme.SpaceCadet

abstract class ActivityScreen<VM: ActivityViewModel>(
    protected val viewModel: VM,
    protected val resources: Resources,
    protected val darkTheme: Boolean
) {
    protected val mainColor: Color = if (darkTheme) PureBlack else PapayaWhip
    protected val secondaryColor: Color = if (darkTheme) SpaceCadet else MellowApricot
    protected val tertiaryColor: Color = if (darkTheme) LightBrown else Pineapple
    protected val contrastColor: Color = if (darkTheme) DesertSand else ChineseBlack

    @Composable
    open fun Screen() =
        Scaffold(
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