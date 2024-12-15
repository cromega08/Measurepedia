package cromega.studio.measurepedia.ui.activities.measures

import cromega.studio.measurepedia.ui.activities.generic.Activity
import cromega.studio.measurepedia.ui.activities.home.HomeActivity

class MeasuresActivity : Activity<MeasuresViewModel, MeasuresScreen>()
{
    override lateinit var viewModel: MeasuresViewModel

    override lateinit var screen: MeasuresScreen

    override fun instantiateVariables()
    {
        viewModel =
            MeasuresViewModel(
                tablesManager = tablesManager,
                userInfo = userInfo,
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
