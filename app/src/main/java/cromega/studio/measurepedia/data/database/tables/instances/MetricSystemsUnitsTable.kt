package cromega.studio.measurepedia.data.database.tables.instances

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import cromega.studio.measurepedia.data.database.tables.generic.Table
import cromega.studio.measurepedia.extensions.isNotNullOrBlank

open class MetricSystemsUnitsTable(context: Context) : Table(context) {
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

    override fun afterInit() = read()

    fun read() {
        val projection: Array<String> =
            arrayOf(
                TABLE_INFO.COLUMN_ID,
                TABLE_INFO.COLUMN_NAME,
                TABLE_INFO.COLUMN_ABBREVIATION
            )

        val result: Cursor = read(projection)
    }

    fun insert(name: String, abbreviation: String) =
        insert(generateContentValue(name, abbreviation))

    fun update(id: Int, name: String, abbreviation: String) =
        update(id, generateContentValue(name, abbreviation))

    private fun generateContentValue(name: String?, abbreviation: String? = null) =
        ContentValues().apply {
            if (name.isNotNullOrBlank()) put(TABLE_INFO.COLUMN_NAME, name)
            if (abbreviation.isNotNullOrBlank()) put(TABLE_INFO.COLUMN_ABBREVIATION, abbreviation)
        }

    protected inner class MetricSystemsUnitsTableInfo : TableInfo()
    {
        override val TABLE: String
            get() = "metric_systems_units"

        val COLUMN_NAME: String
            get() = "name"

        val COLUMN_ABBREVIATION: String
            get() = "abbreviation"
    }
}