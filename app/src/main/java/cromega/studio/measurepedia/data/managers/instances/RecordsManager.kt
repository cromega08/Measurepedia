package cromega.studio.measurepedia.data.managers.instances

import cromega.studio.measurepedia.data.database.tables.instances.RecordsTable
import cromega.studio.measurepedia.data.models.instances.Record
import cromega.studio.measurepedia.extensions.isNotNull

class RecordsManager(
    private val recordsTable: RecordsTable
) {
    fun readAll(): List<Record> = recordsTable.readAll()

    fun readFilteredBy(personId: Int, fieldIds: List<Int>? = null): List<Record>
    {
        return if (fieldIds.isNotNull())
            {
                recordsTable
                    .readByPersonAndFields(
                        personId = personId,
                        fieldIds = fieldIds!!
                    )
            }
            else
            {
                recordsTable.readByPerson(personId = personId)
            }
    }

    fun insert(
        personId: Int,
        fieldId: Int,
        measure: Float? = null,
        metricSystemUnitId: Int
    ) =
        recordsTable
            .insert(
                personId = personId,
                fieldId = fieldId,
                measure = measure,
                metricSystemUnitId = metricSystemUnitId
            )

    fun update(
        id: Int,
        personId: Int,
        fieldId: Int,
        measure: Float,
        metricSystemUnitId: Int
    ) =
        recordsTable
            .update(
                id = id,
                personId = personId,
                fieldId = fieldId,
                measure = measure,
                metricSystemUnitId = metricSystemUnitId
            )
}