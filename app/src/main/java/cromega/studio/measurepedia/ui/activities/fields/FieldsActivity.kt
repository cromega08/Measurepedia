package cromega.studio.measurepedia.ui.activities.fields

import cromega.studio.measurepedia.ui.activities.generic.Activity
import cromega.studio.measurepedia.ui.activities.home.HomeActivity

class FieldsActivity : Activity<FieldsViewModel, FieldsScreen>()
{
    override lateinit var viewModel: FieldsViewModel

    override lateinit var screen: FieldsScreen

    override fun instantiateVariables()
    {
        viewModel =
            FieldsViewModel(
                tablesManager = tablesManager,
                resources = resources,
                openHomeFunction = ::openHomeFunction
            )

        screen =
            FieldsScreen(
                viewModel = viewModel,
                resources = resources
            )
    }

    private fun openHomeFunction() =
        changeActivity(activityToLoad = HomeActivity::class)
}
