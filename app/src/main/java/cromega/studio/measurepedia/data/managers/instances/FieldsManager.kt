package cromega.studio.measurepedia.data.managers.instances

import cromega.studio.measurepedia.data.database.tables.instances.FieldsTable
import cromega.studio.measurepedia.data.database.tables.instances.MetricSystemsUnitsTable
import cromega.studio.measurepedia.data.database.tables.instances.PersonsTable
import cromega.studio.measurepedia.data.database.tables.instances.RecordsTable
import cromega.studio.measurepedia.data.models.instances.Field
import cromega.studio.measurepedia.data.models.instances.MetricSystemUnit
import cromega.studio.measurepedia.extensions.extractIds
import cromega.studio.measurepedia.extensions.isNeitherNullOrEmpty
import cromega.studio.measurepedia.extensions.isNotNull
import cromega.studio.measurepedia.extensions.isNull

class FieldsManager(
    private val fieldsTable: FieldsTable,
    private val recordsTable: RecordsTable,
    private val personsTable: PersonsTable,
    private val metricSystemsUnitsTable: MetricSystemsUnitsTable
) {
   fun readAll(): List<Field> = fieldsTable.readAll()

    fun readFilteredBy(
        active: Boolean? = null,
        bodyPartsIds: List<Int>? = null
    ): List<Field>
    {
        return when
        {
            active.isNotNull() && bodyPartsIds.isNullOrEmpty() ->
                fieldsTable.readByActive(active = active!!)

            active.isNull() && bodyPartsIds.isNeitherNullOrEmpty() ->
                fieldsTable.readByBodyParts(bodyPartsIds = bodyPartsIds!!)

            active.isNotNull() && bodyPartsIds.isNeitherNullOrEmpty() ->
                fieldsTable.readByActiveAndBodyParts(
                    active = active!!,
                    bodyPartsIds = bodyPartsIds!!
                )

            else -> throw IllegalArgumentException("All arguments are null")
        }
    }

    fun insert(name: String, bodyPartId: Int, active: Boolean? = null)
    {
        val insertedFieldId: Long = fieldsTable
            .insert(
                name = name,
                bodyPartId = bodyPartId,
                active = active
            )

        val defaultMetricSystemUnit: MetricSystemUnit = metricSystemsUnitsTable.readAll()[0]

        if (insertedFieldId > 0)
        {
            personsTable
                .readAll()
                .forEach { person ->
                    recordsTable
                        .insert(
                            personId = person.id,
                            fieldId = insertedFieldId.toInt(),
                            metricSystemUnitId = defaultMetricSystemUnit.id
                        )
                }
        }
    }


    fun update(
        id: Int,
        name: String,
        bodyPartId: Int,
        active: Boolean
    ) =
        fieldsTable
            .update(
                id = id,
                name = name,
                bodyPartId = bodyPartId,
                active = active
            )

    fun delete(id: Int)
    {
        recordsTable
            .deleteByIds(
                ids = recordsTable.readByFields(fieldsIds = listOf(id)).extractIds()
            )

        fieldsTable.delete(id = id)
    }

    fun deleteByIds(ids: List<Int>) = ids.forEach { id -> delete(id = id) }
}