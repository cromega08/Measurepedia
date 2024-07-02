package cromega.studio.measurepedia.data.database.tables.generic

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.util.Log
import cromega.studio.measurepedia.data.database.connection.MeasurepediaDatabase

abstract class Table(context: Context) : MeasurepediaDatabase(context)
{
    protected abstract val TABLE_INFO : TableInfo
    protected abstract val ON_INIT_QUERIES: Array<String>

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

    protected fun read(
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

    protected fun insert(toInsert: ContentValues) =
        writableDatabase.insert(
            TABLE_INFO.TABLE,
            null,
            toInsert
        )

    protected fun update(
        id: Int,
        toUpdate: ContentValues,
        selection: String = "${TABLE_INFO.COLUMN_ID} = ?",
        selectionArgs: Array<String> = arrayOf(id.toString())
    )
    {
        writableDatabase.update(
            TABLE_INFO.TABLE,
            toUpdate,
            selection,
            selectionArgs
        )
    }

    fun delete(id: Int)
    {
        val selection: String = "${TABLE_INFO.COLUMN_ID} = ?"
        val selectionArgs: Array<String> = arrayOf(id.toString())

        writableDatabase.delete(
            TABLE_INFO.TABLE,
            selection, selectionArgs
        )
    }

    protected abstract fun afterInit()

    protected abstract inner class TableInfo : BaseColumns
    {
        abstract val TABLE: String
        val COLUMN_ID = BaseColumns._ID
    }
}