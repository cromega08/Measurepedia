package cromega.studio.measurepedia.data.database.tables.instances

import android.content.ContentValues
import android.content.Context
import cromega.studio.measurepedia.data.database.tables.generic.Table
import cromega.studio.measurepedia.data.models.Field
import cromega.studio.measurepedia.extensions.isNotNull
import cromega.studio.measurepedia.extensions.isNotNullOrBlank
import cromega.studio.measurepedia.extensions.toBoolean

open class FieldsTable(context: Context) : Table<Field>(context)
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
    override val COMPLETE_PROJECTION: Array<String>
        get() = TABLE_INFO.COLUMNS

    override fun afterInit() = readAll()

    override fun generateModel(
        index: Int,
        columnsData: Map<String, MutableList<Any>>
    ): Field =
        Field(
            id = columnsData[TABLE_INFO.COLUMN_ID]?.get(index) as Int,
            name = columnsData[TABLE_INFO.COLUMN_NAME]?.get(index) as String,
            bodyPartId = columnsData[TABLE_INFO.COLUMN_BODY_PART_ID]?.get(index) as Int,
            active = (columnsData[TABLE_INFO.COLUMN_ACTIVE]?.get(index) as Int).toBoolean()
        )

    override fun readAll(): Array<Field> = read().toTypedArray()

    fun insert(name: String) =
        insertQuery(generateContentValue(name))

    fun update(id: Int, name: String, available: Boolean) =
        updateQuery(id, generateContentValue(name, available))

    private fun generateContentValue(name: String? = null, active: Boolean? = null): ContentValues =
        ContentValues().apply {
            if (name.isNotNullOrBlank()) put(TABLE_INFO.COLUMN_NAME, name)
            if (active.isNotNull()) put(TABLE_INFO.COLUMN_ACTIVE, active)
        }

    protected inner class FieldsTableInfo : TableInfo()
    {
        override val TABLE: String
            get() = "fields"

        override val COLUMNS: Array<String>
            get() =
                arrayOf(
                    COLUMN_ID,
                    COLUMN_NAME,
                    COLUMN_BODY_PART_ID,
                    COLUMN_ACTIVE
                )

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