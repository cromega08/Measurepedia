package cromega.studio.measurepedia.ui.activities.start

import android.os.Bundle
import androidx.activity.compose.setContent
import cromega.studio.measurepedia.ui.activities.generic.ProjectActivity
import cromega.studio.measurepedia.ui.theme.MeasurepediaTheme

class StartActivity : ProjectActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeasurepediaTheme {
                StartState.initialize()
            }
        }
    }
}
