package cromega.studio.measurepedia.ui.activities.fields

import android.content.res.Resources
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import cromega.studio.measurepedia.R
import cromega.studio.measurepedia.data.managers.general.TablesManager
import cromega.studio.measurepedia.data.models.instances.BodyPart
import cromega.studio.measurepedia.data.models.instances.Field
import cromega.studio.measurepedia.data.models.instances.MetricSystemUnit
import cromega.studio.measurepedia.ui.activities.generic.ActivityViewModel

class FieldsViewModel(
    tablesManager: TablesManager,
    resources: Resources,
    private val openHomeFunction: () -> Unit
): ActivityViewModel(
    tablesManager = tablesManager,
    resources = resources
) {
    val tabs: Array<Int> = arrayOf(R.string.fields, R.string.metric_systems)

    private val tabIndexState: MutableState<Int> = mutableIntStateOf(0)

    var tabIndex: Int
        get() = tabIndexState.value
        set(value) { tabIndexState.value = value }

    private val deletedBodyPartsIds: MutableList<Int> = mutableListOf()

    private val deletedFieldsIds: MutableList<Int> = mutableListOf()

    private val deletedMetricSystemsUnitsIds: MutableList<Int> = mutableListOf()

    private val metricSystemsUnitsState: SnapshotStateList<MetricSystemUnit> =
        mutableStateListOf(*(getAllMetricSystemUnits()))

    val metricSystemUnits: List<MetricSystemUnit> = metricSystemsUnitsState

    private val bodyPartsState: SnapshotStateList<BodyPart> =
        mutableStateListOf(*(getAllBodyParts()))

    val bodyParts: List<BodyPart> = bodyPartsState

    private val fieldsState: SnapshotStateList<Field> =
        mutableStateListOf(*(getAllFields()))

    val fields: List<Field> = fieldsState

    fun refreshAllData()
    {
        refreshMetricSystemUnits()
        refreshBodyParts()
        refreshFields()
    }

    private fun refreshMetricSystemUnits()
    {
        metricSystemsUnitsState.clear()
        metricSystemsUnitsState.addAll(getAllMetricSystemUnits())
    }

    private fun refreshBodyParts()
    {
        bodyPartsState.clear()
        bodyPartsState.addAll(getAllBodyParts())
    }

    private fun refreshFields()
    {
        fieldsState.clear()
        fieldsState.addAll(getAllFields())
    }

    fun getAllMetricSystemUnits(): Array<MetricSystemUnit> =
        tablesManager
            .metricSystemsUnitsManager
            .readAll()
            .toTypedArray()

    private fun getAllBodyParts(): Array<BodyPart> =
        tablesManager
            .bodyPartsManager
            .readAll()
            .toTypedArray()

    private fun getAllFields(): Array<Field> =
        tablesManager
            .fieldsManager
            .readAll()
            .toTypedArray()

    fun findBodyPartById(bodyPartId: Int): BodyPart =
        bodyPartsState.find { bodyPart -> bodyPart.id == bodyPartId }!!

    fun filterFieldsByBodyPartId(bodyPartId: Int): List<Field> =
        fields.filter { field -> field.bodyPartId == bodyPartId }

    private fun modifyBodyPart(bodyPartIndex: Int, functionToApply: BodyPart.() -> Unit)
    {
        bodyPartsState[bodyPartIndex] =
            bodyPartsState[bodyPartIndex]
                .apply(block = functionToApply)
                .clone()
    }

    fun modifyBodyPartName(bodyPartIndex: Int, newName: String) =
        modifyBodyPart(
            bodyPartIndex = bodyPartIndex,
            functionToApply = { this.name = newName }
        )

    fun modifyBodyPartActiveAndPropagate(bodyPartIndex: Int, active: Boolean)
    {
        modifyBodyPartActive(
            bodyPartIndex = bodyPartIndex,
            isActive = active
        )

        modifyFieldsActiveByBodyPart(
            bodyPartIndex = bodyPartIndex,
            areActive = active
        )
    }

    fun modifyBodyPartActive(bodyPartIndex: Int, isActive: Boolean) =
        modifyBodyPart(
            bodyPartIndex = bodyPartIndex,
            functionToApply = { this.active = isActive }
        )

    fun removeBodyPartAndPropagate(bodyPart: BodyPart)
    {
        bodyPartsState.remove(bodyPart)

        if (bodyPart.id != 0) deletedBodyPartsIds.add(bodyPart.id)

        removeFieldsByBodyPartId(bodyPartId = bodyPart.id)
    }

     fun createField(bodyPartId: Int) =
         fieldsState
             .add(
                 Field(
                     id = 0,
                     name = resources.getString(R.string.field_name),
                     bodyPartId = bodyPartId,
                     active = false
                 )
             )

    fun modifyFieldsActiveByBodyPart(bodyPartIndex: Int, areActive: Boolean)
    {
        val bodyPart: BodyPart = bodyPartsState[bodyPartIndex]

        fieldsState
            .forEach {field ->
                val fieldIndex: Int = fieldsState.indexOf(field)

                if (field.bodyPartId == bodyPart.id)
                    modifyFieldActive(
                        fieldIndex = fieldIndex,
                        isActive = areActive
                    )
            }
    }

    private fun modifyField(fieldIndex: Int, functionToApply: Field.() -> Unit)
    {
        fieldsState[fieldIndex] =
            fieldsState[fieldIndex]
                .apply(block = functionToApply)
                .clone()
    }

    fun modifyFieldName(fieldIndex: Int, newName: String) =
        modifyField(
            fieldIndex = fieldIndex,
            functionToApply = { this.name = newName }
        )

    fun modifyFieldActiveAndPropagate(fieldIndex: Int, isActive: Boolean)
    {
        modifyBodyPartActive(
            bodyPartIndex =
                bodyPartsState
                    .indexOf(
                        findBodyPartById(
                            fieldsState[fieldIndex].bodyPartId
                        )
                    ),
            isActive = isActive
        )

        modifyFieldActive(
            fieldIndex = fieldIndex,
            isActive = isActive
        )
    }

    fun modifyFieldActive(fieldIndex: Int, isActive: Boolean) =
        modifyField(
            fieldIndex = fieldIndex,
            functionToApply = { this.active = isActive }
        )

    private fun modifyMetricSystemUnit(
        metricSystemUnitIndex: Int,
        functionToApply: MetricSystemUnit.() -> Unit
    ) {
        metricSystemsUnitsState[metricSystemUnitIndex] =
            metricSystemsUnitsState[metricSystemUnitIndex]
                .apply(block = functionToApply)
                .clone()
    }

    fun modifyMetricSystemUnitName(fieldIndex: Int, newName: String) =
        modifyMetricSystemUnit(
            metricSystemUnitIndex = fieldIndex,
            functionToApply = { this.name = newName }
        )

    fun modifyMetricSystemUnitAbbreviation(fieldIndex: Int, newAbbreviation: String) =
        modifyMetricSystemUnit(
            metricSystemUnitIndex = fieldIndex,
            functionToApply = { this.abbreviation = newAbbreviation }
        )

    fun removeFieldsByBodyPartId(bodyPartId: Int) =
        filterFieldsByBodyPartId(bodyPartId)
            .forEach { field -> removeField(field) }

    fun removeField(field: Field)
    {
        fieldsState.remove(field)

        if (field.id != 0) deletedFieldsIds.add(field.id)
    }

    fun updateData()
    {
        updateBodyPartsAndFieldsData()
        updateMetricSystemsUnitsData()
    }

    private fun updateBodyPartsAndFieldsData()
    {
        flushBodyPartsAndFieldsDeletes()
        flushBodyPartsAndFieldsUpdates()
    }

    private fun flushBodyPartsAndFieldsDeletes()
    {
        flushDeleteBodyParts()
        flushDeleteFields()
    }

    private fun flushDeleteBodyParts() =
        tablesManager
            .bodyPartsManager
            .deleteByIds(ids = deletedBodyPartsIds)

    private fun flushDeleteFields() =
        tablesManager
            .fieldsManager
            .deleteByIds(ids = deletedFieldsIds)

    private fun flushBodyPartsAndFieldsUpdates()
    {
        flushPositiveOperationsBodyParts()
        flushPositiveOperationsFields()
    }

    private fun flushPositiveOperationsBodyParts() =
        bodyPartsState
            .forEach { bodyPart ->
                if (bodyPart.id == 0) flushInsertBodyPart(bodyPart = bodyPart)
                else flushUpdateBodyParts(bodyPart = bodyPart)
            }

    private fun flushInsertBodyPart(bodyPart: BodyPart) =
        tablesManager
            .bodyPartsManager
            .insert(
                name = bodyPart.name,
                active = bodyPart.active
            )

    private fun flushUpdateBodyParts(bodyPart: BodyPart) =
        tablesManager
            .bodyPartsManager
            .update(
                id = bodyPart.id,
                name = bodyPart.name,
                active = bodyPart.active
            )

    private fun flushPositiveOperationsFields() =
        fieldsState
            .forEach { field ->
                if (field.id == 0) flushInsertField(field = field)
                else flushUpdateField(field = field)
            }

    private fun flushInsertField(field: Field) =
        tablesManager
            .fieldsManager
            .insert(
                name = field.name,
                bodyPartId = field.bodyPartId,
                active = field.active
            )

    private fun flushUpdateField(field: Field) =
        tablesManager
            .fieldsManager
            .update(
                id = field.id,
                name = field.name,
                bodyPartId = field.bodyPartId,
                active = field.active
            )

    private fun updateMetricSystemsUnitsData()
    {
        flushDeleteMetricSystemUnits()
        flushPositiveOperationsMetricSystemUnits()
    }

    private fun flushDeleteMetricSystemUnits() =
        tablesManager
            .metricSystemsUnitsManager
            .deleteByIds(ids = deletedMetricSystemsUnitsIds)

    private fun flushPositiveOperationsMetricSystemUnits() =
        metricSystemsUnitsState
            .forEach { metricSystemUnit ->
                if (metricSystemUnit.id == 0) flushInsertMetricSystemUnit(metricSystemUnit = metricSystemUnit)
                else flushUpdateMetricSystemUnit(metricSystemUnit = metricSystemUnit)
            }

    private fun flushInsertMetricSystemUnit(metricSystemUnit: MetricSystemUnit) =
        tablesManager
            .metricSystemsUnitsManager
            .insert(
                name = metricSystemUnit.name,
                abbreviation = metricSystemUnit.abbreviation
            )

    private fun flushUpdateMetricSystemUnit(metricSystemUnit: MetricSystemUnit) =
        tablesManager
            .metricSystemsUnitsManager
            .update(
                id = metricSystemUnit.id,
                name = metricSystemUnit.name,
                abbreviation = metricSystemUnit.abbreviation
            )

    fun openHomeActivity() = openHomeFunction()
}