package cromega.studio.measurepedia.ui.activities.home

import cromega.studio.measurepedia.ui.activities.fields.FieldsActivity
import cromega.studio.measurepedia.ui.activities.generic.Activity
import cromega.studio.measurepedia.ui.activities.measures.MeasuresActivity
import cromega.studio.measurepedia.ui.activities.settings.SettingsActivity

class HomeActivity : Activity<HomeViewModel, HomeScreen>()
{
    override lateinit var viewModel: HomeViewModel

    override lateinit var screen: HomeScreen

    override fun instantiateVariables()
    {
        viewModel =
            HomeViewModel(
                tablesManager = tablesManager,
                userInfo = userInfo,
                resources = resources,
                openMeasuresFunction = ::openMeasuresFunction,
                openFieldsFunction = ::openFieldsFunction,
                openSettingsFunction = ::openSettingsFunction
            )

        screen =
            HomeScreen(
                viewModel = viewModel,
                resources = resources
            )
    }

    private fun openMeasuresFunction(data: Map<String, Any>) =
        changeActivity(activityToLoad = MeasuresActivity::class, data)

    private fun openFieldsFunction() =
        changeActivity(activityToLoad = FieldsActivity::class)

    private fun openSettingsFunction() =
        changeActivity(activityToLoad = SettingsActivity::class)
}
