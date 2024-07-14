package cromega.studio.measurepedia.data.models

import cromega.studio.measurepedia.extensions.toStringWithFormat
import java.util.Date
import java.util.Locale

data class Person(
    val id: Int,
    private val name: String,
    private val alias: String?,
    private val updated: Date,
    val measured: Boolean
) {
    private val locale: Locale = Locale.US

    fun getName(): String =
        name
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale = locale) else it.toString() }

    fun getAlias(): String =
        alias?.
        replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale = locale) else it.toString() }
            ?: ""

    fun getSearchablePersonIdentifier(): String =
        String
            .format(locale = locale, "%s %s", getName(), getAlias())
            .lowercase(locale = locale)

    infix fun getUpdatedAsString(format: String): String = updated toStringWithFormat format

    fun isMeasured(): Boolean = measured

    fun getMeasuredTexts(measuredText: String, notMeasuredText: String) =
        if (measured) measuredText else notMeasuredText
}
