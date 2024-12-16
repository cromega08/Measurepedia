package cromega.studio.measurepedia.ui.activities.home

import android.content.res.Resources
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import cromega.studio.measurepedia.R
import cromega.studio.measurepedia.data.local.files.UserInfo
import cromega.studio.measurepedia.data.managers.general.TablesManager
import cromega.studio.measurepedia.data.models.instances.BodyPart
import cromega.studio.measurepedia.data.models.instances.MetricSystemUnit
import cromega.studio.measurepedia.data.models.instances.Person
import cromega.studio.measurepedia.enums.DateOrder
import cromega.studio.measurepedia.enums.MeasuredOrder
import cromega.studio.measurepedia.extensions.isNotNull
import cromega.studio.measurepedia.ui.activities.generic.ActivityViewModel
import java.sql.Date

class HomeViewModel(
    tablesManager: TablesManager,
    userInfo: UserInfo,
    resources: Resources,
    private val sharePersonInfoFunction: (String, String) -> Unit,
    private val openMeasuresFunction: (Map<String, Any>) -> Unit,
    private val openFieldsFunction: () -> Unit,
    private val openSettingsFunction: () -> Unit
): ActivityViewModel(
    tablesManager = tablesManager,
    userInfo = userInfo,
    resources = resources
) {
    private val personsState: SnapshotStateList<Person> =
        mutableStateListOf(
            *(
                    tablesManager
                        .personsManager
                        .readAll()
                        .toTypedArray()
                    )
        )

    val persons: List<Person>
        get() = personsState

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

    private val selectedPersonState: MutableState<Person?> = mutableStateOf(null)

    var selectedPerson: Person?
        get() = selectedPersonState.value
        set(value) { selectedPersonState.value = value }

    val hasSelectedPerson: Boolean
        get() = selectedPerson.isNotNull()

    private val editingPersonState: MutableState<Person?> = mutableStateOf(null)

    var editingPerson: Person?
        get() = editingPersonState.value
        set(value) { editingPersonState.value = value }

    val hasEditingPerson: Boolean
        get() = editingPerson.isNotNull()

    private val editingPersonMissingNameState: MutableState<Boolean> = mutableStateOf(false)

    var editingPersonMissingName: Boolean
        get() = editingPersonMissingNameState.value
        set(value) { editingPersonMissingNameState.value = value }

    private val deletingPersonState: MutableState<Person?> = mutableStateOf(null)

    var deletingPerson: Person?
        get() = deletingPersonState.value
        set(value) { deletingPersonState.value = value }

    val hasDeletingPerson: Boolean
        get() = deletingPerson.isNotNull()

    fun invertDateFilterExpanded() { dateFilterExpanded = !dateFilterExpanded }

    fun invertMeasuredFilterExpanded() { measuredFilterExpanded = !measuredFilterExpanded }

    fun requestUpdateOfPersons()
    {
        personsState.clear()
        personsState
            .addAll(
                tablesManager
                    .personsManager
                    .readByDateAndMeasured(
                        dateOrder = dateOrderOption,
                        measuredOrder = measuredOrderOption
                    )
            )
    }

    fun allPersonsNull()
    {
        selectedPerson = null
        editingPerson = null
        deletingPerson = null
    }

    fun setSelectedPersonOnly(person: Person)
    {
        allPersonsNull()
        selectedPerson = person
    }

    fun setEditingPersonOnly(person: Person)
    {
        allPersonsNull()
        editingPerson = person
    }

    fun setDeletingPersonOnly(person: Person)
    {
        allPersonsNull()
        deletingPerson = person
    }

    fun openEditorDialogWithEmptyPerson()
    {
        editingPerson =
            Person(
                id = 0,
                name = "",
                alias = "",
                updated = Date(System.currentTimeMillis()),
                measured = false
            )
    }

    private fun updateEditingPerson(apply: Person.() -> Unit)
    {
        editingPerson =
            editingPerson!!
                .clone()
                .apply(block = apply)
    }

    fun updateEditingPersonName(newName: String)
    {
        updateEditingPerson { this.name = newName }
    }

    fun updateEditingPersonAlias(newAlias: String)
    {
        updateEditingPerson { this.alias = newAlias }
    }

    fun finishUpdateEditingPerson()
    {
        if (editingPerson!!.id == 0) flushInsertPerson(person = editingPerson!!)
        else flushUpdatePerson(person = editingPerson!!)

        editingPersonMissingName = false

        allPersonsNull()
    }

    fun finishDeleteDeletingPerson()
    {
        flushDeletePerson(person = deletingPerson!!)
        allPersonsNull()
    }

    private fun flushInsertPerson(person: Person)
    {
        tablesManager
            .personsManager
            .insert(
                name = person.name,
                alias = person.alias.ifBlank { null }
            )
            .run { requestUpdateOfPersons() }
    }

    private fun flushUpdatePerson(person: Person)
    {
        tablesManager
            .personsManager
            .update(
                id = person.id,
                name = person.name,
                alias = person.alias.ifBlank { null },
                updated = Date(System.currentTimeMillis()),
                measured = person.measured
            )
            .run { requestUpdateOfPersons() }
    }

    private fun flushDeletePerson(person: Person)
    {
        tablesManager
            .personsManager
            .delete(id = person.id)
            .run { requestUpdateOfPersons() }
    }

    fun sharePersonInfo(person: Person)
    {
        val metricSystemUnits: List<MetricSystemUnit> = tablesManager.metricSystemsUnitsManager.readAll()
        val bodyParts: List<BodyPart> = tablesManager.bodyPartsManager.readByActive(active = true)
        val personName: String = "${person.name} - ${person.alias}"
        val textToShare: StringBuilder = StringBuilder()

        val placeholder: String = resources.getString(R.string.share_person_info_placeholder)
        val measured: String =
            resources
                .getString(
                    if (person.measured) R.string.measured
                    else R.string.not_measured
                )

        textToShare.appendLine(personName)
        textToShare.appendLine(String.format(placeholder, measured))
        textToShare.appendLine("----------")

        bodyParts
            .forEach { bodyPart ->
                textToShare.appendLine()
                textToShare.appendLine(bodyPart.name)
                textToShare.appendLine("-----")

                tablesManager
                    .fieldsManager
                    .readFilteredBy(
                        active = true,
                        bodyPartsIds = listOf(bodyPart.id)
                    )
                    .forEach { field ->
                        tablesManager
                            .recordsManager
                            .readFilteredBy(
                                personId = person.id,
                                fieldsIds = listOf(field.id)
                            ).forEach { record ->
                                val metricSystemUnit: MetricSystemUnit =
                                    metricSystemUnits.find { record.metricSystemUnitId == it.id }!!

                                textToShare.appendLine("${field.name} = ${record.measure} ${metricSystemUnit.abbreviation}")
                            }
                    }
            }

        textToShare.appendLine()

        sharePersonInfoFunction("\"$personName\"", textToShare.toString())
    }

    fun openMeasuresActivity(person: Person) =
        openMeasuresFunction(
            mapOf(
                "selectedPersonId" to person.id
            )
        )

    fun openFieldsActivity() = openFieldsFunction()

    fun openSettingsActivity() = openSettingsFunction()
}