package cromega.studio.measurepedia.data.database.tables.instances

import android.content.ContentValues
import android.content.Context
import cromega.studio.measurepedia.data.database.tables.generic.Table
import cromega.studio.measurepedia.data.models.Person
import cromega.studio.measurepedia.extensions.isNotNull
import cromega.studio.measurepedia.extensions.isNotNullOrBlank
import cromega.studio.measurepedia.extensions.toBoolean
import cromega.studio.measurepedia.extensions.toDateWithFormat
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale

open class PersonsTable(context: Context) : Table<Person>(context)
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

    override val COMPLETE_PROJECTION: Array<String>
        get() = TABLE_INFO.COLUMNS

    override fun afterInit() = readAll()

    override fun generateModel(
        index: Int,
        columnsData: Map<String, MutableList<Any>>
    ): Person 
    {
        val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

        return Person(
            id = columnsData[TABLE_INFO.COLUMN_ID]?.get(index) as Int,
            name = columnsData[TABLE_INFO.COLUMN_NAME]?.get(index) as String,
            alias = columnsData[TABLE_INFO.COLUMN_ALIAS]?.get(index) as? String?,
            updated = (columnsData[TABLE_INFO.COLUMN_UPDATED]?.get(index) as String) toDateWithFormat dateFormat,
            measured = (columnsData[TABLE_INFO.COLUMN_MEASURED]?.get(index) as Int).toBoolean()
        )
    }

    override fun readAll(): Array<Person> = read().toTypedArray()

    fun readOrderedByDate(): Array<Person> = read(sortOrder = "${TABLE_INFO.COLUMN_UPDATED} desc").toTypedArray()

    fun insert(name: String, alias: String? = null, updated: Date? = null) =
        insertQuery(generateContentValue(name, alias, updated))

    fun update(id: Int, name: String, alias: String? = null, updated: Date? = null, measured: Boolean? = null) =
        updateQuery(id, generateContentValue(name, alias, updated, measured))

    private fun generateContentValue(
        name: String? = null,
        alias: String? = null,
        updated: Date? = null,
        measured: Boolean? = null
    ): ContentValues =
        ContentValues().apply {
            if (name.isNotNullOrBlank()) put(TABLE_INFO.COLUMN_NAME, name)
            if (alias.isNotNullOrBlank()) put(TABLE_INFO.COLUMN_ALIAS, alias)
            if (updated.isNotNull()) put(TABLE_INFO.COLUMN_UPDATED, DATE_FORMAT.format(updated))
            if (measured.isNotNull()) put(TABLE_INFO.COLUMN_MEASURED, measured)
        }

    protected inner class PersonsTableInfo : TableInfo()
    {
        override val TABLE: String
            get() = "persons"

        override val COLUMNS: Array<String>
            get() = arrayOf(
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_ALIAS,
                COLUMN_UPDATED,
                COLUMN_MEASURED
            )

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