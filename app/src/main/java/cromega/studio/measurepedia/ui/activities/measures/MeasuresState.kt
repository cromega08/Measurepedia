package cromega.studio.measurepedia.ui.activities.measures

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import cromega.studio.measurepedia.data.models.instances.BodyPart
import cromega.studio.measurepedia.data.models.instances.Field
import cromega.studio.measurepedia.data.models.instances.MetricSystemUnit
import cromega.studio.measurepedia.data.models.instances.Person
import cromega.studio.measurepedia.data.models.instances.Record
import cromega.studio.measurepedia.extensions.extractIds
import cromega.studio.measurepedia.extensions.isNotNull
import cromega.studio.measurepedia.resources.utils.TablesUtils

internal object MeasuresState
{
    lateinit var selectedPerson: Person
    lateinit var bodyParts: Array<BodyPart>
    lateinit var fields: Array<Field>
    lateinit var records: SnapshotStateList<Record>
    lateinit var metricSystemsUnits: Array<MetricSystemUnit>
    lateinit var fieldsMeasureSelectorExpanded: SnapshotStateList<Boolean>
    private lateinit var openHomeFunction: () -> Unit

    @Composable
    fun initialize(
        selectedPersonId: Int,
        openHomeFunction: () -> Unit
    ) {
        this.selectedPerson =
            TablesUtils
                .personsTable
                .readPerson(id = selectedPersonId)

        this.bodyParts =
            TablesUtils
                .bodyPartsTable
                .readByActive()

        this.fields =
            TablesUtils
                .fieldsTable
                .readByActiveAndBodyParts(
                    bodyPartsIds = bodyParts.extractIds()
                )

        this.metricSystemsUnits =
            TablesUtils
                .metricSystemsUnitsTable
                .readAll()

        this.openHomeFunction = openHomeFunction

        val recordsData =
            TablesUtils
                .recordsTable
                .readByPersonAndFields(
                    personId = selectedPersonId,
                    fieldIds = fields.extractIds()
                )

        this.records = remember { mutableStateListOf(*recordsData)  }
        this.fieldsMeasureSelectorExpanded =
            remember { mutableStateListOf(*Array(fields.size) { false }) }
    }

    private fun updateRecord(recordId: Int, transform: (Record) -> Record)
    {
        records
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
        updateRecord(recordId) {record ->
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
            updateRecord(recordId) {record ->
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

    fun invertFieldMetricSystemUnitState(fieldIndex: Int)
    {
        fieldsMeasureSelectorExpanded[fieldIndex] = !fieldsMeasureSelectorExpanded[fieldIndex]
    }

    fun flushRecordsForUpdates()
    {
        records
            .forEach {record ->
                record.requestMeasureUpdate()

                TablesUtils
                    .recordsTable
                    .update(
                        id = record.id,
                        personId = record.personId,
                        fieldId = record.fieldId,
                        measure = record.measure,
                        metricSystemUnitId = record.metricSystemUnitId
                    )
            }
    }

    fun openHomeActivity() = openHomeFunction()
}