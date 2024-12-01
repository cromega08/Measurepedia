package cromega.studio.measurepedia.ui.activities.fields

import android.os.Bundle
import androidx.activity.compose.setContent
import cromega.studio.measurepedia.ui.activities.generic.Activity
import cromega.studio.measurepedia.ui.activities.home.HomeActivity
import cromega.studio.measurepedia.ui.theme.MeasurepediaTheme

class FieldsActivity : Activity<FieldsViewModel, FieldsScreen>()
{
    override lateinit var viewModel: FieldsViewModel

    override lateinit var screen: FieldsScreen

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        setContent {
            MeasurepediaTheme {

            }
        }
    }

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
