package cromega.studio.measurepedia.ui.activities.home

import android.os.Bundle
import androidx.activity.compose.setContent
import cromega.studio.measurepedia.ui.activities.generic.Activity
import cromega.studio.measurepedia.ui.activities.measures.MeasuresActivity
import cromega.studio.measurepedia.ui.theme.MeasurepediaTheme

class HomeActivity : Activity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent {
            MeasurepediaTheme {
                HomeState.initialize(
                    openMeasuresFunction = { changeActivity(MeasuresActivity::class, it) }
                )
                HomeScreen.Screen()
            }
        }
    }
}
