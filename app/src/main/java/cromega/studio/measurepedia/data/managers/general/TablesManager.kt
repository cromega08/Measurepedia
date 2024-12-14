package cromega.studio.measurepedia.data.managers.general

import android.content.Context
import cromega.studio.measurepedia.data.database.tables.instances.BodyPartsTable
import cromega.studio.measurepedia.data.database.tables.instances.FieldsTable
import cromega.studio.measurepedia.data.database.tables.instances.MetricSystemsUnitsTable
import cromega.studio.measurepedia.data.database.tables.instances.PersonsTable
import cromega.studio.measurepedia.data.database.tables.instances.RecordsTable
import cromega.studio.measurepedia.data.managers.instances.BodyPartsManager
import cromega.studio.measurepedia.data.managers.instances.FieldsManager
import cromega.studio.measurepedia.data.managers.instances.MetricSystemsUnitsManager
import cromega.studio.measurepedia.data.managers.instances.PersonsManager
import cromega.studio.measurepedia.data.managers.instances.RecordsManager

class TablesManager
{
    private lateinit var personsTable: PersonsTable
    private lateinit var metricSystemsUnitsTable: MetricSystemsUnitsTable
    private lateinit var bodyPartsTable: BodyPartsTable
    private lateinit var fieldsTable: FieldsTable
    private lateinit var recordsTable: RecordsTable

    lateinit var personsManager: PersonsManager
    lateinit var recordsManager: RecordsManager
    lateinit var fieldsManager: FieldsManager
    lateinit var bodyPartsManager: BodyPartsManager
    lateinit var metricSystemsUnitsManager: MetricSystemsUnitsManager

    fun instantiate(context: Context)
    {
        instantiateTables(context)
        instantiateManagers()
    }

    private fun instantiateTables(context: Context)
    {
        personsTable = PersonsTable(context = context)
        metricSystemsUnitsTable = MetricSystemsUnitsTable(context = context)
        bodyPartsTable = BodyPartsTable(context = context)
        fieldsTable = FieldsTable(context = context)
        recordsTable = RecordsTable(context = context)
    }

    private fun instantiateManagers()
    {
        personsManager =
            PersonsManager(
                personsTable = personsTable,
                fieldsTable = fieldsTable,
                metricSystemsUnitsTable = metricSystemsUnitsTable,
                recordsTable = recordsTable
            )

        metricSystemsUnitsManager =
            MetricSystemsUnitsManager(
                metricSystemsUnitsTable = metricSystemsUnitsTable,
                recordsTable = recordsTable
            )

        bodyPartsManager =
            BodyPartsManager(
                bodyPartsTable = bodyPartsTable,
                fieldsTable = fieldsTable,
                recordsTable = recordsTable
            )
        fieldsManager =
            FieldsManager(
                fieldsTable = fieldsTable,
                recordsTable = recordsTable,
                personsTable = personsTable,
                metricSystemsUnitsTable = metricSystemsUnitsTable
            )

        recordsManager =
            RecordsManager(
                recordsTable = recordsTable
            )
    }

    fun close()
    {
        personsTable.close()
        metricSystemsUnitsTable.close()
        bodyPartsTable.close()
        fieldsTable.close()
        recordsTable.close()
    }
}