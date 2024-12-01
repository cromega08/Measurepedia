package cromega.studio.measurepedia.ui.activities.measures

import android.os.Bundle
import androidx.activity.compose.setContent
import cromega.studio.measurepedia.ui.activities.generic.Activity
import cromega.studio.measurepedia.ui.activities.home.HomeActivity
import cromega.studio.measurepedia.ui.theme.MeasurepediaTheme

class MeasuresActivity : Activity<MeasuresViewModel, MeasuresScreen>()
{
    override lateinit var viewModel: MeasuresViewModel

    override lateinit var screen: MeasuresScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MeasurepediaTheme {
                screen.Screen()
            }
        }
    }

    override fun instantiateVariables()
    {
        viewModel =
            MeasuresViewModel(
                tablesManager = tablesManager,
                resources = resources,
                selectedPersonId = intent.getIntExtra("selectedPersonId", 0),
                openHomeFunction = ::openHomeFunction
            )

        screen =
            MeasuresScreen(
                viewModel = viewModel,
                resources = resources
            )
    }

    private fun openHomeFunction() =
        changeActivity(activityToLoad = HomeActivity::class)
}
