package cromega.studio.measurepedia.ui.activities.generic

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import cromega.studio.measurepedia.data.local.files.UserInfo
import cromega.studio.measurepedia.data.managers.general.TablesManager

abstract class ActivityViewModel(
    protected val tablesManager: TablesManager,
    protected val userInfo: UserInfo,
    protected val resources: Resources
) : ViewModel()