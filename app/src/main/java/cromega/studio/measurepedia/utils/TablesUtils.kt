package cromega.studio.measurepedia.utils

import cromega.studio.measurepedia.data.database.tables.instances.BodyPartsTable
import cromega.studio.measurepedia.data.database.tables.instances.FieldsTable
import cromega.studio.measurepedia.data.database.tables.instances.MetricSystemsUnitsTable
import cromega.studio.measurepedia.data.database.tables.instances.PersonsTable
import cromega.studio.measurepedia.data.database.tables.instances.RecordsTable

data class TablesUtils(
    val personsTable: PersonsTable,
    val bodyPartsTable: BodyPartsTable,
    val metricSystemsUnitsTable: MetricSystemsUnitsTable,
    val fieldsTable: FieldsTable,
    val recordsTable: RecordsTable
) {
    fun close() {
        personsTable.close()
        bodyPartsTable.close()
        metricSystemsUnitsTable.close()
        fieldsTable.close()
        recordsTable.close()
    }
}