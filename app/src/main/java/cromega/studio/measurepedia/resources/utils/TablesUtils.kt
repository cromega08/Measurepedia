package cromega.studio.measurepedia.resources.utils

import cromega.studio.measurepedia.data.database.tables.instances.BodyPartsTable
import cromega.studio.measurepedia.data.database.tables.instances.FieldsTable
import cromega.studio.measurepedia.data.database.tables.instances.MetricSystemsUnitsTable
import cromega.studio.measurepedia.data.database.tables.instances.PersonsTable
import cromega.studio.measurepedia.data.database.tables.instances.RecordsTable

object TablesUtils
{
    lateinit var personsTable: PersonsTable
    lateinit var bodyPartsTable: BodyPartsTable
    lateinit var metricSystemsUnitsTable: MetricSystemsUnitsTable
    lateinit var fieldsTable: FieldsTable
    lateinit var recordsTable: RecordsTable

    fun updateInstances(
        personsTable: PersonsTable,
        bodyPartsTable: BodyPartsTable,
        metricSystemsUnitsTable: MetricSystemsUnitsTable,
        fieldsTable: FieldsTable,
        recordsTable: RecordsTable
    ) {
        this.personsTable = personsTable
        this.bodyPartsTable = bodyPartsTable
        this.metricSystemsUnitsTable = metricSystemsUnitsTable
        this.fieldsTable = fieldsTable
        this.recordsTable = recordsTable
    }

    fun closeInstances()
    {
        personsTable.close()
        bodyPartsTable.close()
        metricSystemsUnitsTable.close()
        fieldsTable.close()
        recordsTable.close()
    }
}