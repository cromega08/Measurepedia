package cromega.studio.measurepedia.ui.activities.fields

import android.widget.Toast
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
                userInfo = userInfo,
                resources = resources,
                openHomeFunction = ::openHomeFunction
            )

        screen =
            FieldsScreen(
                viewModel = viewModel,
                resources = resources,
                showToastFunction = ::showInsufficientToast
            )
    }

    private fun showInsufficientToast(stringId: Int)
    {
        val toast: Toast = Toast(applicationContext)

        toast.setText(stringId)
        toast.duration = Toast.LENGTH_SHORT

        toast.show()
    }

    private fun openHomeFunction() =
        changeActivity(activityToLoad = HomeActivity::class)
}
