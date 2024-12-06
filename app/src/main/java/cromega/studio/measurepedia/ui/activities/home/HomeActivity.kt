package cromega.studio.measurepedia.ui.activities.home

import cromega.studio.measurepedia.ui.activities.fields.FieldsActivity
import cromega.studio.measurepedia.ui.activities.generic.Activity
import cromega.studio.measurepedia.ui.activities.measures.MeasuresActivity

class HomeActivity : Activity<HomeViewModel, HomeScreen>()
{
    override lateinit var viewModel: HomeViewModel

    override lateinit var screen: HomeScreen

    override fun instantiateVariables()
    {
        viewModel =
            HomeViewModel(
                tablesManager = tablesManager,
                resources = resources,
                openMeasuresFunction = ::openMeasuresFunction,
                openFieldsFunction = ::openFieldsFunction
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
}
