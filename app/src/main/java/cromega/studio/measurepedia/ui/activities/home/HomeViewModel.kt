package cromega.studio.measurepedia.ui.activities.home

import android.content.res.Resources
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cromega.studio.measurepedia.data.managers.general.TablesManager
import cromega.studio.measurepedia.data.models.instances.Person
import cromega.studio.measurepedia.enums.DateOrder
import cromega.studio.measurepedia.enums.MeasuredOrder
import cromega.studio.measurepedia.extensions.isNotNull
import cromega.studio.measurepedia.ui.activities.generic.ActivityViewModel

class HomeViewModel(
    tablesManager: TablesManager,
    resources: Resources,
    private val openMeasuresFunction: (Map<String, Any>) -> Unit
): ActivityViewModel(
    tablesManager = tablesManager,
    resources = resources
) {
    private val searchTextState: MutableState<String> = mutableStateOf("")
    var searchText: String
        get() = searchTextState.value
        set(value) { searchTextState.value = value }

    private val dateFilterExpandedState: MutableState<Boolean> = mutableStateOf(false)
    var dateFilterExpanded: Boolean
        get() = dateFilterExpandedState.value
        set(value) { dateFilterExpandedState.value = value }

    private val measuredFilterExpandedState: MutableState<Boolean> = mutableStateOf(false)
    var measuredFilterExpanded: Boolean
        get() = measuredFilterExpandedState.value
        set(value) { measuredFilterExpandedState.value = value }

    private val dateOrderOptionState: MutableState<DateOrder> = mutableStateOf(DateOrder.CREATION)
    var dateOrderOption: DateOrder
        get() = dateOrderOptionState.value
        set(value) { dateOrderOptionState.value = value }

    private val measuredOrderOptionState: MutableState<MeasuredOrder> = mutableStateOf(MeasuredOrder.MEASURED_OR_NOT)
    var measuredOrderOption: MeasuredOrder
        get() = measuredOrderOptionState.value
        set(value) { measuredOrderOptionState.value = value }

    private val optionsDialogOpenedState: MutableState<Boolean> = mutableStateOf(false)
    var optionsDialogOpened: Boolean
        get() = optionsDialogOpenedState.value
        set(value) { optionsDialogOpenedState.value = value }

    var selectedPerson: Person? = null

    val hasSelectedPerson: Boolean
        get() = selectedPerson.isNotNull()

    fun invertDateFilterExpanded() { dateFilterExpanded = !dateFilterExpanded }

    fun invertMeasuredFilterExpanded() { measuredFilterExpanded = !measuredFilterExpanded }

    fun readByDateAndMeasured(): Array<Person> =
        tablesManager
            .personsManager
            .readByDateAndMeasured(
                dateOrder = dateOrderOption,
                measuredOrder = measuredOrderOption
            )

    fun openMeasuresActivity() =
        openMeasuresFunction(
            mapOf(
                "selectedPersonId" to selectedPerson!!.id
            )
        )
}