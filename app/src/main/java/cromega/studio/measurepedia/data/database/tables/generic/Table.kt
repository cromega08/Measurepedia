package cromega.studio.measurepedia.data.database.tables.generic

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.util.Log
import cromega.studio.measurepedia.data.database.connection.MeasurepediaDatabase
import cromega.studio.measurepedia.data.models.generic.Model
import cromega.studio.measurepedia.extensions.addIfNotNull
import cromega.studio.measurepedia.extensions.generateLogicMap
import cromega.studio.measurepedia.extensions.generateSimpleMap
import cromega.studio.measurepedia.extensions.get
import java.text.SimpleDateFormat
import java.util.Locale

abstract class Table<M: Model>(context: Context) : MeasurepediaDatabase(context)
{
    protected val DATE_FORMAT: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    protected abstract val TABLE_INFO : TableInfo
    protected abstract val ON_INIT_QUERIES: Array<String>
    protected abstract val COMPLETE_PROJECTION : Array<String>

    override fun onInit()
    {
        if (currentTableNotExist()) createCurrentTable()

        afterInit()
    }

    override fun afterCreation(db: SQLiteDatabase) = createCurrentTable(db)
    private fun currentTableNotExist(): Boolean
    {
        val sql = "SELECT COUNT(*) exist FROM sqlite_master WHERE type='table' AND name='${TABLE_INFO.TABLE}'"
        val cursor: Cursor = readableDatabase.rawQuery(sql, null)
        cursor.moveToFirst()
        val result: Boolean = cursor.getInt(0) > 0
        cursor.close()
        return !result
    }

    private fun createCurrentTable(db: SQLiteDatabase = writableDatabase)
    {
        ON_INIT_QUERIES.forEach { db.execSQL(it) }
        Log.i(this.javaClass.name, "${TABLE_INFO.TABLE} created in $DATABASE_NAME")
    }

    protected fun extractColumnsData(cursor: Cursor): Map<String, MutableList<Any>> {
        val toReturn: Map<String, MutableList<Any>> = TABLE_INFO.generateColumnsDataMap()

        val columns: Map<String, Int> = TABLE_INFO.generateColumnsToIndexMap(cursor)

        if (cursor.moveToFirst())
        {
            do
            {
                TABLE_INFO.COLUMNS.forEach {
                    toReturn[it]
                        ?.addIfNotNull(cursor.get(columns[it]!!))
                }
            } while (cursor.moveToNext())
        }

        return toReturn
    }

    protected fun read(
        selection: String? = null,
        selectionArgs: Array<String>? = null,
        sortOrder: String? = null
    ): MutableList<M>
    {
        val toReturn: MutableList<M> = mutableListOf()

        val rowsData: Cursor =
            readQuery(
                projection = COMPLETE_PROJECTION,
                selection = selection,
                selectionArgs = selectionArgs,
                sortOrder = sortOrder
            )

        val rowsCount: Int = rowsData.count

        val columnsData: Map<String, MutableList<Any>> = extractColumnsData(rowsData)

        for(index in 0 until rowsCount)
        {
            toReturn.add(generateModel(index, columnsData))
        }

        return toReturn
    }

    protected fun readQuery(
        projection: Array<String>,
        selection: String? = null,
        selectionArgs: Array<String>? = null,
        sortOrder: String? = null
    ): Cursor
    {
        return readableDatabase.query(
            TABLE_INFO.TABLE,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )
    }

    protected fun insertQuery(toInsert: ContentValues): Long =
        writableDatabase.insert(
            TABLE_INFO.TABLE,
            null,
            toInsert
        )

    protected fun updateQuery(
        id: Int = 0,
        toUpdate: ContentValues,
        selection: String = "${TABLE_INFO.COLUMN_ID} = ?",
        selectionArgs: Array<String> = arrayOf(id.toString())
    ) =
        writableDatabase.update(
            TABLE_INFO.TABLE,
            toUpdate,
            selection,
            selectionArgs
        )

    protected fun deleteQuery(
        id: Int = 0,
        selection: String = "${TABLE_INFO.COLUMN_ID} = ?",
        selectionArgs: Array<String> = arrayOf(id.toString())
    ) =
        writableDatabase.delete(
            TABLE_INFO.TABLE,
            selection, selectionArgs
        )

    protected abstract fun afterInit(): Any

    protected abstract fun generateModel(index: Int, columnsData: Map<String, MutableList<Any>>): M

    abstract fun readAll(): List<M>

    protected abstract inner class TableInfo : BaseColumns
    {
        abstract val TABLE: String
        abstract val COLUMNS: Array<String>
        val COLUMN_ID = BaseColumns._ID

        fun generateColumnsDataMap(): Map<String, MutableList<Any>> =
            COLUMNS.generateSimpleMap(constructFunction = ::mutableListOf)

        fun generateColumnsToIndexMap(cursor: Cursor): Map<String, Int> =
            COLUMNS.generateLogicMap { cursor.getColumnIndex(it) }
    }
}