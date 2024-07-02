package cromega.studio.measurepedia.data.database.tables.instances

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import cromega.studio.measurepedia.data.database.tables.generic.Table
import cromega.studio.measurepedia.extensions.isNotNull
import cromega.studio.measurepedia.extensions.isNotNullOrBlank

open class FieldsTable(context: Context) : Table(context)
{
    override val TABLE_INFO: FieldsTableInfo
        get() = FieldsTableInfo()
    override val ON_INIT_QUERIES: Array<String>
        get() = arrayOf(
            "create table if not exists ${TABLE_INFO.TABLE}(" +
                    "${TABLE_INFO.COLUMN_ID} integer primary key, " +
                    "${TABLE_INFO.COLUMN_NAME} text not null unique, " +
                    "${TABLE_INFO.COLUMN_BODY_PART_ID} integer not null, " +
                    "${TABLE_INFO.COLUMN_ACTIVE} boolean not null default true," +
                    "foreign key (${TABLE_INFO.COLUMN_BODY_PART_ID}) references ${TABLE_INFO.REFERENCE_TABLE_BODY_PARTS}(${TABLE_INFO.COLUMN_ID})" +
                    ");",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_NAME}, ${TABLE_INFO.COLUMN_BODY_PART_ID}) values ('height', 1);",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_NAME}, ${TABLE_INFO.COLUMN_BODY_PART_ID}) values ('shoulders', 2);",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_NAME}, ${TABLE_INFO.COLUMN_BODY_PART_ID}) values ('waist', 2);",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_NAME}, ${TABLE_INFO.COLUMN_BODY_PART_ID}) values ('left arm', 3);",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_NAME}, ${TABLE_INFO.COLUMN_BODY_PART_ID}) values ('right arm', 3);",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_NAME}, ${TABLE_INFO.COLUMN_BODY_PART_ID}) values ('hip', 4);",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_NAME}, ${TABLE_INFO.COLUMN_BODY_PART_ID}) values ('left leg', 5);",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_NAME}, ${TABLE_INFO.COLUMN_BODY_PART_ID}) values ('right leg', 5);",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_NAME}, ${TABLE_INFO.COLUMN_BODY_PART_ID}) values ('left foot', 6);",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_NAME}, ${TABLE_INFO.COLUMN_BODY_PART_ID}) values ('right foot', 6);",
        )

    override fun afterInit() = read()

    fun read() {
        val projection: Array<String> =
            arrayOf(
                TABLE_INFO.COLUMN_ID,
                TABLE_INFO.COLUMN_NAME,
                TABLE_INFO.COLUMN_ACTIVE
            )

        val sortOrder: String = "${TABLE_INFO.COLUMN_NAME} desc"

        val result: Cursor = read(
            projection = projection,
            sortOrder = sortOrder
        )
    }

    fun insert(name: String) =
        insert(generateContentValue(name))

    fun update(id: Int, name: String, available: Boolean) =
        update(id, generateContentValue(name, available))

    private fun generateContentValue(name: String? = null, active: Boolean? = null): ContentValues =
        ContentValues().apply {
            if (name.isNotNullOrBlank()) put(TABLE_INFO.COLUMN_NAME, name)
            if (active.isNotNull()) put(TABLE_INFO.COLUMN_ACTIVE, active)
        }

    protected inner class FieldsTableInfo : TableInfo()
    {
        override val TABLE: String
            get() = "fields"

        val COLUMN_NAME: String
            get() = "name"

        val COLUMN_BODY_PART_ID: String
            get() = "body_part_id"

        val COLUMN_ACTIVE: String
            get() = "active"

        val REFERENCE_TABLE_BODY_PARTS: String
            get() = "body_parts"
    }
}