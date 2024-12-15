package cromega.studio.measurepedia.ui.activities.settings

import cromega.studio.measurepedia.ui.activities.generic.Activity
import cromega.studio.measurepedia.ui.activities.home.HomeActivity

class SettingsActivity : Activity<SettingsViewModel, SettingsScreen>()
{
    override lateinit var viewModel: SettingsViewModel
    override lateinit var screen: SettingsScreen

    override fun instantiateVariables()
    {
        viewModel =
            SettingsViewModel(
                tablesManager = tablesManager,
                userInfo = userInfo,
                resources = resources,
                openHomeFunction = ::openHomeFunction
            )

        screen =
            SettingsScreen(
                viewModel = viewModel,
                resources = resources
            )
    }

    private fun openHomeFunction() =
        changeActivity(activityToLoad = HomeActivity::class)
}