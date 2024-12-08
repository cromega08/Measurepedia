package cromega.studio.measurepedia.data.managers.instances

import cromega.studio.measurepedia.data.database.tables.instances.RecordsTable
import cromega.studio.measurepedia.data.models.instances.Record
import cromega.studio.measurepedia.extensions.extractIds
import cromega.studio.measurepedia.extensions.isNeitherNullOrEmpty
import cromega.studio.measurepedia.extensions.isNotNull
import cromega.studio.measurepedia.extensions.isNull

class RecordsManager(
    private val recordsTable: RecordsTable
) {
    fun readAll(): List<Record> = recordsTable.readAll()

    fun readFilteredBy(personId: Int? = null, fieldsIds: List<Int>? = null): List<Record>
    {
        return when
        {
            personId.isNotNull() && fieldsIds.isNeitherNullOrEmpty() ->
                recordsTable
                    .readByPersonAndFields(
                        personId = personId!!,
                        fieldIds = fieldsIds!!
                    )

            personId.isNotNull() && fieldsIds.isNullOrEmpty() ->
                recordsTable.readByPerson(personId = personId!!)

            personId.isNull() && fieldsIds.isNeitherNullOrEmpty() ->
                recordsTable.readByFields(fieldsIds = fieldsIds!!)

            else -> readAll()
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

    fun delete(id: Int) = recordsTable.delete(id = id)

    fun deleteByFieldsIds(fieldIds: List<Int>) =
        recordsTable.deleteByIds(ids = readFilteredBy(fieldsIds = fieldIds).extractIds())
}