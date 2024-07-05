package cromega.studio.measurepedia.data.database.tables.instances

import android.content.ContentValues
import android.content.Context
import cromega.studio.measurepedia.data.database.tables.generic.Table
import cromega.studio.measurepedia.data.models.MetricSystemUnit
import cromega.studio.measurepedia.extensions.isNotNullOrBlank

open class MetricSystemsUnitsTable(context: Context) : Table<MetricSystemUnit>(context) {
    override val TABLE_INFO: MetricSystemsUnitsTableInfo
        get() = MetricSystemsUnitsTableInfo()
    override val ON_INIT_QUERIES: Array<String>
        get() = arrayOf(
            "create table if not exists ${TABLE_INFO.TABLE}(" +
                    "${TABLE_INFO.COLUMN_ID} integer primary key, " +
                    "${TABLE_INFO.COLUMN_NAME} text not null unique, " +
                    "${TABLE_INFO.COLUMN_ABBREVIATION} text not null unique" +
                    ");",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_NAME}, ${TABLE_INFO.COLUMN_ABBREVIATION}) values ('meters', 'm')",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_NAME}, ${TABLE_INFO.COLUMN_ABBREVIATION}) values ('centimeters', 'cm')",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_NAME}, ${TABLE_INFO.COLUMN_ABBREVIATION}) values ('inches', 'in')",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_NAME}, ${TABLE_INFO.COLUMN_ABBREVIATION}) values ('feet', 'ft')"
        )
    override val COMPLETE_PROJECTION: Array<String>
        get() = TABLE_INFO.COLUMNS

    override fun afterInit() = readAll()

    override fun generateModel(
        index: Int,
        columnsData: Map<String, MutableList<Any>>
    ): MetricSystemUnit =
        MetricSystemUnit(
            id = columnsData[TABLE_INFO.COLUMN_ID]?.get(index) as Int,
            name = columnsData[TABLE_INFO.COLUMN_NAME]?.get(index) as String,
            abbreviation = columnsData[TABLE_INFO.COLUMN_ABBREVIATION]?.get(index) as String
        )
    
    override fun readAll() = read().toTypedArray()

    fun insert(name: String, abbreviation: String) =
        insertQuery(generateContentValue(name, abbreviation))

    fun update(id: Int, name: String, abbreviation: String) =
        updateQuery(id, generateContentValue(name, abbreviation))

    private fun generateContentValue(name: String?, abbreviation: String? = null) =
        ContentValues().apply {
            if (name.isNotNullOrBlank()) put(TABLE_INFO.COLUMN_NAME, name)
            if (abbreviation.isNotNullOrBlank()) put(TABLE_INFO.COLUMN_ABBREVIATION, abbreviation)
        }

    protected inner class MetricSystemsUnitsTableInfo : TableInfo()
    {
        override val TABLE: String
            get() = "metric_systems_units"

        override val COLUMNS: Array<String>
            get() = arrayOf(
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_ABBREVIATION
            )

        val COLUMN_NAME: String
            get() = "name"

        val COLUMN_ABBREVIATION: String
            get() = "abbreviation"
    }
}