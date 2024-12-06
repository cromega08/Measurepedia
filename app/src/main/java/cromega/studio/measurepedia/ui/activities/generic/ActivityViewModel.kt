package cromega.studio.measurepedia.ui.activities.generic

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import cromega.studio.measurepedia.data.managers.general.TablesManager

abstract class ActivityViewModel(
    protected val tablesManager: TablesManager,
    protected val resources: Resources
) : ViewModel()