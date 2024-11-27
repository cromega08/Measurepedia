package cromega.studio.measurepedia.ui.activities.measures

import android.os.Bundle
import androidx.activity.compose.setContent
import cromega.studio.measurepedia.ui.activities.generic.Activity
import cromega.studio.measurepedia.ui.activities.home.HomeActivity
import cromega.studio.measurepedia.ui.theme.MeasurepediaTheme

class MeasuresActivity : Activity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selectedPersonId: Int =
            intent.getIntExtra("selectedPersonId", 0)

        setContent {
            MeasurepediaTheme {
                MeasuresState.initialize(
                    selectedPersonId = selectedPersonId,
                    openHomeFunction = { changeActivity(HomeActivity::class) }
                )
                MeasuresScreen.Screen()
            }
        }
    }
}
