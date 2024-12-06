package cromega.studio.measurepedia.extensions

import cromega.studio.measurepedia.data.models.generic.Model

inline fun <reified Instance : Model> List<Instance>.extractIds(): List<Int> =
    this.map { it.id }

infix fun <T> List<T>.itIsTheLast(toCheck: T): Boolean = this.last() == toCheck

infix fun <T> List<T>.itIsNotTheLast(toCheck: T): Boolean = !(this itIsTheLast toCheck)

fun <T> List<T>?.isNeitherNullOrEmpty(): Boolean
{
    return (this.isNotNull() && (this?.isNotEmpty() ?: false))
}

fun <T> List<T>.toText(
    startChar: CharSequence = "(",
    endChar: CharSequence = ")",
    separator: CharSequence = ",",
    elementToString: ((T) -> String)? = null
): String
{
    val string: StringBuilder = StringBuilder()

    string.append(startChar)

    this.forEach { element ->
        val toAppend: String =
            elementToString
                ?.let { function -> function(element) }
                ?: element.toString()

        string.append(toAppend)

        if (this itIsNotTheLast element) string.append(separator)
    }

    string.append(endChar)

    return string.toString()
}
