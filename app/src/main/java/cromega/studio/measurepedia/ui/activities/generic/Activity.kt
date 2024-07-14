package cromega.studio.measurepedia.ui.activities.generic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import cromega.studio.measurepedia.data.database.tables.instances.BodyPartsTable
import cromega.studio.measurepedia.data.database.tables.instances.FieldsTable
import cromega.studio.measurepedia.data.database.tables.instances.MetricSystemsUnitsTable
import cromega.studio.measurepedia.data.database.tables.instances.PersonsTable
import cromega.studio.measurepedia.data.database.tables.instances.RecordsTable
import cromega.studio.measurepedia.resources.utils.ResourcesUtils
import cromega.studio.measurepedia.resources.utils.TablesUtils

abstract class Activity: ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ResourcesUtils.updateInstance(resources)
        TablesUtils
            .updateInstances(
                personsTable = PersonsTable(applicationContext),
                bodyPartsTable = BodyPartsTable(applicationContext),
                metricSystemsUnitsTable = MetricSystemsUnitsTable(applicationContext),
                fieldsTable = FieldsTable(applicationContext),
                recordsTable = RecordsTable(applicationContext)
            )
    }

    override fun onDestroy()
    {
        super.onDestroy()
        TablesUtils.closeInstances()
    }
}