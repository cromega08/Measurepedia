package cromega.studio.measurepedia.ui.activities.home

import android.os.Bundle
import androidx.activity.compose.setContent
import cromega.studio.measurepedia.ui.activities.generic.Activity
import cromega.studio.measurepedia.ui.activities.measures.MeasuresActivity
import cromega.studio.measurepedia.ui.theme.MeasurepediaTheme

class HomeActivity : Activity<HomeViewModel, HomeScreen>()
{
    override lateinit var viewModel: HomeViewModel

    override lateinit var screen: HomeScreen

    override fun onCreate(savedInstanceState: Bundle?)
    {
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
            HomeViewModel(
                tablesManager = tablesManager,
                resources = resources,
                openMeasuresFunction = ::openMeasuresFunction
            )

        screen =
            HomeScreen(
                viewModel = viewModel,
                resources = resources
            )
    }

    private fun openMeasuresFunction(data: Map<String, Any>) =
        changeActivity(activityToLoad = MeasuresActivity::class, data)
}
