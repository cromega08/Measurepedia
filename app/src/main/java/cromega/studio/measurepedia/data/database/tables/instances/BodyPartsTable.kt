package cromega.studio.measurepedia.data.database.tables.instances

import android.content.ContentValues
import android.content.Context
import cromega.studio.measurepedia.data.database.tables.generic.Table
import cromega.studio.measurepedia.data.models.BodyPart
import cromega.studio.measurepedia.extensions.isNotNull
import cromega.studio.measurepedia.extensions.isNotNullOrBlank
import cromega.studio.measurepedia.extensions.toBoolean

open class BodyPartsTable(context: Context) : Table<BodyPart>(context)
{
    override val TABLE_INFO: BodyPartsTableInfo
        get() = BodyPartsTableInfo()
    override val ON_INIT_QUERIES: Array<String>
        get() = arrayOf(
            "create table if not exists ${TABLE_INFO.TABLE}(" +
                    "${TABLE_INFO.COLUMN_ID} integer primary key, " +
                    "${TABLE_INFO.COLUMN_NAME} text not null unique, " +
                    "${TABLE_INFO.COLUMN_ACTIVE} boolean not null default true" +
                    ");",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_NAME}) values ('complete body');",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_NAME}) values ('trunk');",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_NAME}) values ('arms');",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_NAME}) values ('pelvis');",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_NAME}) values ('legs');",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_NAME}) values ('foots');"
        )

    override val COMPLETE_PROJECTION: Array<String>
        get() = TABLE_INFO.COLUMNS

    override fun afterInit() = readAll()

    override fun readAll(): Array<BodyPart> =
        (read { i, map ->
            BodyPart(
                id = map[TABLE_INFO.COLUMN_ID]?.get(i) as Int,
                name = map[TABLE_INFO.COLUMN_NAME]?.get(i) as String,
                active = (map[TABLE_INFO.COLUMN_ACTIVE]?.get(i) as Int).toBoolean()
            )
        }).toTypedArray()

    fun insert(name: String, active: Boolean? = null) =
        insertQuery(generateContentValue(name, active))

    fun update(id: Int, name: String, active: Boolean) =
        updateQuery(id, generateContentValue(name, active))

    private fun generateContentValue(name: String? = null, active: Boolean? = null) =
        ContentValues().apply {
            if (name.isNotNullOrBlank()) put(TABLE_INFO.COLUMN_NAME, name)
            if (active.isNotNull()) put(TABLE_INFO.COLUMN_ACTIVE, active)
        }

    protected inner class BodyPartsTableInfo : TableInfo()
    {
        override val TABLE: String
            get() = "body_parts"

        override val COLUMNS: Array<String>
            get() =
                arrayOf(
                    COLUMN_ID,
                    COLUMN_NAME,
                    COLUMN_ACTIVE
                )

        val COLUMN_NAME: String
            get() = "name"

        val COLUMN_ACTIVE: String
            get() = "active"
    }
}