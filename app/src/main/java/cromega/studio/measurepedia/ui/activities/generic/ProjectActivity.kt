package cromega.studio.measurepedia.ui.activities.generic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import cromega.studio.measurepedia.data.database.FieldsTable
import cromega.studio.measurepedia.data.database.PersonsTable
import cromega.studio.measurepedia.data.database.RecordsTable
import cromega.studio.measurepedia.utils.ResourcesUtils
import cromega.studio.measurepedia.utils.TablesUtils

open class ProjectActivity: ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ResourcesUtils.updateInstance(resources)

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}