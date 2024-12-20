package cromega.studio.measurepedia.ui.activities.settings

import android.content.res.Resources
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cromega.studio.measurepedia.data.local.files.UserInfo
import cromega.studio.measurepedia.data.managers.general.TablesManager
import cromega.studio.measurepedia.enums.Languages
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
    private val languageState: MutableState<Languages> = mutableStateOf(userInfo.language)

    var language: Languages
        get() = languageState.value
        set(value) { languageState.value = value }

    private val languageDropdownExpandedState: MutableState<Boolean> = mutableStateOf(false)

    var languageDropdownExpanded: Boolean
        get() = languageDropdownExpandedState.value
        set(value) { languageDropdownExpandedState.value = value }

    private val darkThemeState: MutableState<Boolean> = mutableStateOf(userInfo.darkTheme)

    var darkTheme: Boolean
        get() = darkThemeState.value
        set(value) { darkThemeState.value = value }

    private val updateRecordsDialogOpenState: MutableState<Boolean> = mutableStateOf(false)

    var updateRecordsDialogOpen: Boolean
        get() = updateRecordsDialogOpenState.value
        set(value) { updateRecordsDialogOpenState.value = value }

    fun invertUpdateRecordsDialogOpen()
    {
        updateRecordsDialogOpen = !updateRecordsDialogOpen
    }

    fun invertDarkTheme()
    {
        darkTheme = !darkTheme
    }

    fun invertLanguageDropdownExpanded()
    {
        languageDropdownExpanded = !languageDropdownExpanded
    }

    fun updateAllRecordsMetricSystemUnitIdToDefault() =
        tablesManager
            .recordsManager
            .updateAllRecordsMetricSystemUnitId(
                metricSystemUnitId = userInfo.defaultMetricSystemUnitId
            )

    fun updateData()
    {
        refreshUserInfo()

        userInfo.flushUpdateUserInfo()
    }

    private fun refreshUserInfo()
    {
        userInfo.language = language
        userInfo.darkTheme = darkTheme
    }

    fun openHomeActivity() = openHomeFunction()
}