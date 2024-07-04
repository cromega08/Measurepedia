package cromega.studio.measurepedia.data.database.connection

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Locale


abstract class MeasurepediaDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
    init { onInit() }

    override fun onCreate(db: SQLiteDatabase)
    {
        Log.i(this.javaClass.name, "$DATABASE_NAME was created successfully")
        afterCreation(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    abstract fun onInit()
    abstract fun afterCreation(db: SQLiteDatabase)
    companion object
    {
        const val DATABASE_NAME: String = "measurepedia.db"
        const val DATABASE_VERSION: Int = 1
    }
}