package cromega.studio.measurepedia.data.database.tables.instances

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import cromega.studio.measurepedia.data.database.tables.generic.Table
import cromega.studio.measurepedia.data.models.BodyPart
import cromega.studio.measurepedia.extensions.isNotNull
import cromega.studio.measurepedia.extensions.isNotNullOrBlank

open class BodyPartsTable(context: Context) : Table(context)
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

    override fun afterInit() = read()

    fun read()
    {
        val projection: Array<String> =
            arrayOf(
                TABLE_INFO.COLUMN_ID,
                TABLE_INFO.COLUMN_NAME,
                TABLE_INFO.COLUMN_ACTIVE
            )

        val result: Cursor = read(projection)
    }

    fun insert(name: String, active: Boolean? = null) =
        insert(generateContentValue(name, active))

    fun update(id: Int, name: String, active: Boolean) =
        update(id, generateContentValue(name, active))

    private fun generateContentValue(name: String? = null, active: Boolean? = null) =
        ContentValues().apply {
            if (name.isNotNullOrBlank()) put(TABLE_INFO.COLUMN_NAME, name)
            if (active.isNotNull()) put(TABLE_INFO.COLUMN_ACTIVE, active)
        }

    private fun loadModel(id: Int, name: String, active: Boolean) : BodyPart =
        BodyPart(id, name, active)

    protected inner class BodyPartsTableInfo : TableInfo()
    {
        override val TABLE: String
            get() = "body_parts"

        val COLUMN_NAME: String
            get() = "name"

        val COLUMN_ACTIVE: String
            get() = "active"
    }
}