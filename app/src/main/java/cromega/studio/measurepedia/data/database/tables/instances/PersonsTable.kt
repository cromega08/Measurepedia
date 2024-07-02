package cromega.studio.measurepedia.data.database.tables.instances

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import cromega.studio.measurepedia.data.database.tables.generic.Table
import cromega.studio.measurepedia.extensions.isNotNull
import cromega.studio.measurepedia.extensions.isNotNullOrBlank
import java.sql.Date

open class PersonsTable(context: Context) : Table(context)
{
    override val TABLE_INFO: PersonsTableInfo
        get() = PersonsTableInfo()
    override val ON_INIT_QUERIES: Array<String>
        get() = arrayOf(
            "create table if not exists ${TABLE_INFO.TABLE}(" +
                    "${TABLE_INFO.COLUMN_ID} integer primary key, " +
                    "${TABLE_INFO.COLUMN_NAME} text not null, " +
                    "${TABLE_INFO.COLUMN_ALIAS} text, " +
                    "${TABLE_INFO.COLUMN_UPDATED} date not null default (date('now')), " +
                    "${TABLE_INFO.COLUMN_MEASURED} boolean not null default false" +
                    ");",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_NAME}, ${TABLE_INFO.COLUMN_ALIAS}) values ('user', 'measurer');",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_NAME}) values ('client');"
        )

    override fun afterInit() = read()

    fun read() {
        val projection: Array<String> =
            arrayOf(
                TABLE_INFO.COLUMN_ID,
                TABLE_INFO.COLUMN_NAME,
                TABLE_INFO.COLUMN_ALIAS,
                TABLE_INFO.COLUMN_UPDATED,
                TABLE_INFO.COLUMN_MEASURED
            )

        val sortOrder: String = "${TABLE_INFO.COLUMN_NAME} desc"

        val result: Cursor = read(
            projection = projection,
            sortOrder = sortOrder
        )
    }

    fun insert(name: String, alias: String? = null, updated: Date? = null) =
        insert(generateContentValue(name, alias, updated))

    fun update(id: Int, name: String, alias: String? = null, updated: Date? = null, measured: Boolean? = null) =
        update(id, generateContentValue(name, alias, updated, measured))

    private fun generateContentValue(name: String? = null, alias: String? = null, updated: Date? = null, measured: Boolean? = null): ContentValues {
        val contentValues: ContentValues = ContentValues()

        if (name.isNotNullOrBlank()) contentValues.put(TABLE_INFO.COLUMN_NAME, name)
        if (alias.isNotNullOrBlank()) contentValues.put(TABLE_INFO.COLUMN_ALIAS, alias)
        if (updated.isNotNull()) contentValues.put(TABLE_INFO.COLUMN_UPDATED, DATE_FORMAT.format(updated))
        if (measured.isNotNull()) contentValues.put(TABLE_INFO.COLUMN_MEASURED, measured)

        return contentValues
    }

    protected inner class PersonsTableInfo : TableInfo()
    {
        override val TABLE: String
            get() = "persons"

        val COLUMN_NAME: String
            get() = "name"

        val COLUMN_ALIAS: String
            get() = "alias"

        val COLUMN_UPDATED: String
            get() = "updated"

        val COLUMN_MEASURED: String
            get() = "measured"
    }
}