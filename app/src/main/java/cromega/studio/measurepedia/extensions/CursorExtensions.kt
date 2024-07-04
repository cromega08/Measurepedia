package cromega.studio.measurepedia.extensions

import android.database.Cursor
import androidx.annotation.IntRange
import androidx.core.database.getBlobOrNull
import androidx.core.database.getFloatOrNull
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull

inline fun Cursor.get(
    @IntRange(from = 0) columnIndex: Int
): Any
{
    return when(getType(columnIndex)) {
        Cursor.FIELD_TYPE_INTEGER -> getInt(columnIndex)
        Cursor.FIELD_TYPE_FLOAT -> getFloat(columnIndex)
        Cursor.FIELD_TYPE_STRING -> getString(columnIndex)
        Cursor.FIELD_TYPE_BLOB -> getBlob(columnIndex)
        else -> Any()
    }
}

inline fun Cursor.getOrNull(
    @IntRange(from = 0) columnIndex: Int
): Any?
{
    return when(getType(columnIndex)) {
        Cursor.FIELD_TYPE_INTEGER -> getIntOrNull(columnIndex)
        Cursor.FIELD_TYPE_FLOAT -> getFloatOrNull(columnIndex)
        Cursor.FIELD_TYPE_STRING -> getStringOrNull(columnIndex)
        Cursor.FIELD_TYPE_BLOB -> getBlobOrNull(columnIndex)
        else -> null
    }
}