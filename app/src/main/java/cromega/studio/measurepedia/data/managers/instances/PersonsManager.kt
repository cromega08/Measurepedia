package cromega.studio.measurepedia.data.managers.instances

import cromega.studio.measurepedia.data.database.tables.instances.FieldsTable
import cromega.studio.measurepedia.data.database.tables.instances.MetricSystemsUnitsTable
import cromega.studio.measurepedia.data.database.tables.instances.PersonsTable
import cromega.studio.measurepedia.data.database.tables.instances.RecordsTable
import cromega.studio.measurepedia.data.models.instances.Person
import cromega.studio.measurepedia.enums.DateOrder
import cromega.studio.measurepedia.enums.MeasuredOrder
import cromega.studio.measurepedia.extensions.extractIds
import java.sql.Date

class PersonsManager(
    private val personsTable: PersonsTable,
    private val fieldsTable: FieldsTable,
    private val metricSystemsUnitsTable: MetricSystemsUnitsTable,
    private val recordsTable: RecordsTable
) {

    fun readPerson(personId: Int): Person = personsTable.readPerson(id = personId)

    fun readAll(): List<Person> = personsTable.readAll()

    fun readOrderedByDateOrder(dateOrder: DateOrder): List<Person> =
        when (dateOrder)
        {
            DateOrder.RECENT -> personsTable.readOrderedByUpdated(true)
            DateOrder.OLDEST -> personsTable.readOrderedByUpdated(false)
            DateOrder.CREATION -> personsTable.readAll()
        }

    fun readFilteredByMeasured(measuredOrder: MeasuredOrder): List<Person> =
        when(measuredOrder)
        {
            MeasuredOrder.MEASURED -> personsTable.readFilteredByMeasured(true)
            MeasuredOrder.NOT_MEASURED -> personsTable.readFilteredByMeasured(false)
            MeasuredOrder.MEASURED_OR_NOT -> personsTable.readAll()
        }

    fun readByDateAndMeasured(
        dateOrder: DateOrder,
        measuredOrder: MeasuredOrder
    ): List<Person> =
        readOrderedByDateOrder(dateOrder)
            .filter {person ->
                when(measuredOrder)
                {
                    MeasuredOrder.MEASURED -> person.measured
                    MeasuredOrder.NOT_MEASURED -> !person.measured
                    MeasuredOrder.MEASURED_OR_NOT -> true
                }
            }

    fun insert(name: String, alias: String? = null, updated: Date? = null, defaultMetricSystemUnitId: Int)
    {
        val personInsertResult: Int =
            personsTable
                .insert(
                    name = name,
                    alias = alias,
                    updated = updated
                )
                .toInt()

        if (personInsertResult > 0)
            fieldsTable
                .readAll()
                .forEach { field ->
                    recordsTable.insert(
                        personId = personInsertResult,
                        fieldId = field.id,
                        metricSystemUnitId = defaultMetricSystemUnitId
                    )
                }
    }

    fun update(id: Int, name: String, alias: String? = null, updated: Date? = null, measured: Boolean? = null) =
        personsTable.update(id, name, alias, updated, measured)

    fun delete(id: Int)
    {
        recordsTable
            .deleteByIds(
                ids =  recordsTable.readByPerson(personId = id).extractIds()
            )

        personsTable.delete(id = id)
    }
}