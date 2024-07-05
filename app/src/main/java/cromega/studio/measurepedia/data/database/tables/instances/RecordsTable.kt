package cromega.studio.measurepedia.data.database.tables.instances

import android.content.ContentValues
import android.content.Context
import cromega.studio.measurepedia.data.database.tables.generic.Table
import cromega.studio.measurepedia.data.models.Record
import cromega.studio.measurepedia.extensions.isNotNull

open class RecordsTable(context: Context) : Table<Record>(context) {
    override val TABLE_INFO: RecordsTableInfo
        get() = RecordsTableInfo()
    override val ON_INIT_QUERIES: Array<String>
        get() = arrayOf(
            "create table if not exists ${TABLE_INFO.TABLE}(" +
                    "${TABLE_INFO.COLUMN_ID} integer primary key, " +
                    "${TABLE_INFO.COLUMN_PERSON_ID} integer not null, " +
                    "${TABLE_INFO.COLUMN_FIELD_ID} integer not null, " +
                    "${TABLE_INFO.COLUMN_MEASURE} real not null default 0, " +
                    "${TABLE_INFO.COLUMN_METRIC_SYSTEM_UNIT_ID} integer not null, " +
                    "foreign key (${TABLE_INFO.COLUMN_PERSON_ID}) references ${TABLE_INFO.REFERENCE_TABLE_PERSONS}(${TABLE_INFO.COLUMN_ID}), " +
                    "foreign key (${TABLE_INFO.COLUMN_FIELD_ID}) references ${TABLE_INFO.REFERENCE_TABLE_FIELDS}(${TABLE_INFO.COLUMN_ID}), " +
                    "foreign key (${TABLE_INFO.COLUMN_METRIC_SYSTEM_UNIT_ID}) references ${TABLE_INFO.REFERENCE_TABLE_METRIC_SYSTEMS_UNITS}(${TABLE_INFO.COLUMN_ID})" +
                    ");",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_PERSON_ID}, ${TABLE_INFO.COLUMN_FIELD_ID}, ${TABLE_INFO.COLUMN_MEASURE}, ${TABLE_INFO.COLUMN_METRIC_SYSTEM_UNIT_ID}) values (1, 1, 1.68, 1)",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_PERSON_ID}, ${TABLE_INFO.COLUMN_FIELD_ID}, ${TABLE_INFO.COLUMN_METRIC_SYSTEM_UNIT_ID}) values (1, 2, 1)",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_PERSON_ID}, ${TABLE_INFO.COLUMN_FIELD_ID}, ${TABLE_INFO.COLUMN_METRIC_SYSTEM_UNIT_ID}) values (1, 3, 1)",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_PERSON_ID}, ${TABLE_INFO.COLUMN_FIELD_ID}, ${TABLE_INFO.COLUMN_METRIC_SYSTEM_UNIT_ID}) values (1, 4, 1)",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_PERSON_ID}, ${TABLE_INFO.COLUMN_FIELD_ID}, ${TABLE_INFO.COLUMN_METRIC_SYSTEM_UNIT_ID}) values (1, 5, 1)",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_PERSON_ID}, ${TABLE_INFO.COLUMN_FIELD_ID}, ${TABLE_INFO.COLUMN_METRIC_SYSTEM_UNIT_ID}) values (1, 6, 1)",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_PERSON_ID}, ${TABLE_INFO.COLUMN_FIELD_ID}, ${TABLE_INFO.COLUMN_METRIC_SYSTEM_UNIT_ID}) values (1, 7, 1)",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_PERSON_ID}, ${TABLE_INFO.COLUMN_FIELD_ID}, ${TABLE_INFO.COLUMN_METRIC_SYSTEM_UNIT_ID}) values (1, 8, 1)",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_PERSON_ID}, ${TABLE_INFO.COLUMN_FIELD_ID}, ${TABLE_INFO.COLUMN_METRIC_SYSTEM_UNIT_ID}) values (1, 9, 1)",
            "insert into ${TABLE_INFO.TABLE}(${TABLE_INFO.COLUMN_PERSON_ID}, ${TABLE_INFO.COLUMN_FIELD_ID}, ${TABLE_INFO.COLUMN_METRIC_SYSTEM_UNIT_ID}) values (1, 10, 1)",
        )

    override val COMPLETE_PROJECTION: Array<String>
        get() = TABLE_INFO.COLUMNS

    override fun afterInit() = readAll()

    override fun generateModel(
        index: Int,
        columnsData: Map<String, MutableList<Any>>
    ): Record =
        Record(
            id = columnsData[TABLE_INFO.COLUMN_ID]?.get(index) as Int,
            personId = columnsData[TABLE_INFO.COLUMN_PERSON_ID]?.get(index) as Int,
            fieldId = columnsData[TABLE_INFO.COLUMN_FIELD_ID]?.get(index) as Int,
            measure = columnsData[TABLE_INFO.COLUMN_MEASURE]?.get(index) as Float,
            metricSystemUnitId = columnsData[TABLE_INFO.COLUMN_METRIC_SYSTEM_UNIT_ID]?.get(index) as Int
        )
    
    override fun readAll() = read().toTypedArray()

    fun insert(personId: Int, fieldId: Int, measure: Float? = null, metricSystemUnitId: Int) =
        insertQuery(generateContentValue(personId, fieldId, measure, metricSystemUnitId))

    fun update(id: Int, personId: Int, fieldId: Int, measure: Float, metricSystemUnitId: Int) =
        updateQuery(id, generateContentValue(personId, fieldId, measure, metricSystemUnitId))

    private fun generateContentValue(
        personId: Int? = null,
        fieldId: Int? = null,
        measure: Float? = null,
        metricSystemUnitId: Int? = null
    ) = ContentValues().apply {
            if (personId.isNotNull()) put(TABLE_INFO.COLUMN_PERSON_ID, personId)
            if (personId.isNotNull()) put(TABLE_INFO.COLUMN_FIELD_ID, fieldId)
            if (personId.isNotNull()) put(TABLE_INFO.COLUMN_MEASURE, measure)
            if (personId.isNotNull()) put(TABLE_INFO.COLUMN_METRIC_SYSTEM_UNIT_ID, metricSystemUnitId)
        }

    protected inner class RecordsTableInfo : TableInfo() {
        override val TABLE: String
            get() = "records"

        override val COLUMNS: Array<String>
            get() = arrayOf(
                COLUMN_ID,
                COLUMN_PERSON_ID,
                COLUMN_FIELD_ID,
                COLUMN_MEASURE,
                COLUMN_METRIC_SYSTEM_UNIT_ID
            )

        val COLUMN_PERSON_ID: String
            get() = "person_id"

        val COLUMN_FIELD_ID: String
            get() = "field_id"

        val COLUMN_MEASURE: String
            get() = "measure"

        val COLUMN_METRIC_SYSTEM_UNIT_ID: String
            get() = "metric_system_unit_id"

        val REFERENCE_TABLE_PERSONS: String
            get() = "persons"

        val REFERENCE_TABLE_FIELDS: String
            get() = "fields"

        val REFERENCE_TABLE_METRIC_SYSTEMS_UNITS: String
            get() = "metric_systems_units"
    }
}