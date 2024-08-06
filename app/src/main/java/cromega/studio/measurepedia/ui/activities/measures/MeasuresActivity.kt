package cromega.studio.measurepedia.ui.activities.measures

import android.os.Bundle
import androidx.activity.compose.setContent
import cromega.studio.measurepedia.ui.activities.generic.Activity
import cromega.studio.measurepedia.ui.theme.MeasurepediaTheme

class MeasuresActivity : Activity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeasurepediaTheme {
                MeasuresState.initialize()
                MeasuresScreen.Screen()
            }
        }
    }
}
