package cromega.studio.measurepedia.ui.activities.home

import android.content.Intent
import cromega.studio.measurepedia.R
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
                sharePersonInfoFunction = ::sharePersonInfo,
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

    private fun sharePersonInfo(personName: String, personInfo: String)
    {
        val intent =
            Intent()
                .apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"

                    putExtra(Intent.EXTRA_TITLE, String.format(getString(R.string.share_person_info_placeholder), personName))
                    putExtra(Intent.EXTRA_TEXT, personInfo)
                }

        val shareIntent = Intent.createChooser(intent, null)

        startActivity(shareIntent)
    }

    private fun openMeasuresFunction(data: Map<String, Any>) =
        changeActivity(activityToLoad = MeasuresActivity::class, data)

    private fun openFieldsFunction() =
        changeActivity(activityToLoad = FieldsActivity::class)

    private fun openSettingsFunction() =
        changeActivity(activityToLoad = SettingsActivity::class)
}
