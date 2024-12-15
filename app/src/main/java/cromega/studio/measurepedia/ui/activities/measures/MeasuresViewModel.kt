package cromega.studio.measurepedia.ui.activities.measures

import android.content.res.Resources
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import cromega.studio.measurepedia.data.local.files.UserInfo
import cromega.studio.measurepedia.data.managers.general.TablesManager
import cromega.studio.measurepedia.data.models.instances.BodyPart
import cromega.studio.measurepedia.data.models.instances.Field
import cromega.studio.measurepedia.data.models.instances.MetricSystemUnit
import cromega.studio.measurepedia.data.models.instances.Person
import cromega.studio.measurepedia.data.models.instances.Record
import cromega.studio.measurepedia.extensions.extractIds
import cromega.studio.measurepedia.extensions.isNotNull
import cromega.studio.measurepedia.ui.activities.generic.ActivityViewModel
import java.sql.Date

class MeasuresViewModel(
    tablesManager: TablesManager,
    userInfo: UserInfo,
    resources: Resources,
    selectedPersonId: Int,
    private val openHomeFunction: () -> Unit
): ActivityViewModel(
    tablesManager = tablesManager,
    userInfo = userInfo,
    resources = resources
) {
    val selectedPerson: Person =
        tablesManager
            .personsManager
            .readPerson(selectedPersonId)

    val bodyParts: List<BodyPart> =
        tablesManager
            .bodyPartsManager
            .readByActive()

    val fields: List<Field> =
        tablesManager
            .fieldsManager
            .readFilteredBy(
                active = true,
                bodyPartsIds = bodyParts.extractIds()
            )

    val metricSystemsUnits: List<MetricSystemUnit> =
        tablesManager
            .metricSystemsUnitsManager
            .readAll()

    private val recordsState: SnapshotStateList<Record> =
        mutableStateListOf(
            *(
                tablesManager
                    .recordsManager
                    .readFilteredBy(
                        personId = selectedPersonId,
                        fieldsIds = fields.extractIds()
                    ).toTypedArray()
                )
        )

    val records: List<Record> = recordsState

    val metricSystemsUnitsSelectorsExpanded = mutableStateListOf( *Array(fields.size) { false } )

    fun findArrayIndexOfField(field: Field): Int = fields.indexOf(field)

    fun findRecordByFieldId(fieldId: Int): Record = records.find { it.fieldId == fieldId }!!

    fun findMetricSystemUnitById(metricSystemUnitId: Int): MetricSystemUnit =
        metricSystemsUnits.find { it.id == metricSystemUnitId }!!

    fun filterFieldsByBodyPartId(bodyPartId: Int): List<Field> =
        fields
            .filter { it.bodyPartId == bodyPartId }

    fun filterRecordsByFieldsIds(fieldsIds: List<Int>): List<Record> =
        records
            .filter { fieldsIds.contains(it.fieldId) }

    private fun updateRecord(recordId: Int, transform: (Record) -> Record)
    {
        recordsState
            .replaceAll {current ->
                var toReturn: Record = current.clone()

                if (current.id == recordId)
                {
                    toReturn = transform(toReturn)
                }

                toReturn
            }
    }

    fun updateRecordMeasureByPrintable(recordId: Int)
    {
        updateRecord(recordId) { record ->
            record.apply {
                this.requestMeasureUpdate()
            }
        }
    }

    fun updateRecordMeasurePrintable(recordId: Int, newMeasurePrintable: String)
    {
        if (
            newMeasurePrintable.isNotBlank() ||
            newMeasurePrintable.toFloatOrNull().isNotNull()
        )
        {
            updateRecord(recordId) { record ->
                record.apply {
                    this.measurePrintable = newMeasurePrintable
                }
            }
        }
    }

    fun updateRecordMetricSystemUnitId(recordId: Int, newMetricSystemUnitId: Int)
    {
        updateRecord(recordId) {record ->
            record.apply {
                this.metricSystemUnitId = newMetricSystemUnitId
            }
        }
    }

    fun invertMetricSystemUnitSelector(fieldIndex: Int)
    {
        metricSystemsUnitsSelectorsExpanded[fieldIndex] = !metricSystemsUnitsSelectorsExpanded[fieldIndex]
    }

    fun updateData()
    {
        flushRecordsForUpdates()
        flushToDateCurrentPerson()
    }

    private fun flushRecordsForUpdates() =
        recordsState
            .forEach {record ->
                record.requestMeasureUpdate()

                tablesManager
                    .recordsManager
                    .update(
                        id = record.id,
                        personId = record.personId,
                        fieldId = record.fieldId,
                        measure = record.measure,
                        metricSystemUnitId = record.metricSystemUnitId
                    )
            }

    private fun flushToDateCurrentPerson() =
        tablesManager
            .personsManager
            .update(
                id = selectedPerson.id,
                name = selectedPerson.name,
                alias = selectedPerson.alias,
                updated = Date(System.currentTimeMillis()),
                measured = true
            )

    fun openHomeActivity() = openHomeFunction()
}