package cromega.studio.measurepedia.ui.activities.settings

import android.content.res.Resources
import cromega.studio.measurepedia.data.local.files.UserInfo
import cromega.studio.measurepedia.data.managers.general.TablesManager
import cromega.studio.measurepedia.ui.activities.generic.ActivityViewModel

class SettingsViewModel(
    tablesManager: TablesManager,
    userInfo: UserInfo,
    resources: Resources,
    private val openHomeFunction: () -> Unit
): ActivityViewModel(
    tablesManager = tablesManager,
    userInfo = userInfo,
    resources = resources
) {

}