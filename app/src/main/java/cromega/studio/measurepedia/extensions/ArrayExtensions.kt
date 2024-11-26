package cromega.studio.measurepedia.extensions

import cromega.studio.measurepedia.data.models.generic.Model

inline fun <reified Instance : Model> Array<Instance>.extractIds(): Array<Int> =
    this.map { it.id }.toTypedArray()

inline fun <T> Array<T>.findOrDefault(
    predicate: (T) -> Boolean,
    generateDefault: (Map<String, Any>) -> T,
    dataForDefault: Map<String, Any> = mapOf()
): T
{
    return firstOrNull(predicate) ?: generateDefault(dataForDefault)
}

inline fun <E, T> Array<E>.generateSimpleMap(
    constructFunction: () -> T
): Map<E, T>
{
    val toReturn: MutableMap<E, T> = mutableMapOf()

    this.forEach {
        toReturn[it] = constructFunction()
    }

    return toReturn.toMap()
}

inline fun <E, T> Array<E>.generateLogicMap(
    constructFunction: (E) -> T
): Map<E, T>
{
    val toReturn: MutableMap<E, T> = mutableMapOf()

    this.forEach {
        toReturn[it] = constructFunction(it)
    }

    return toReturn.toMap()
}

infix fun <T> Array<T>.itIsTheLast(toCheck: T): Boolean = this.last() == toCheck

infix fun <T> Array<T>.itIsNotTheLast(toCheck: T): Boolean = !(this itIsTheLast toCheck)

fun <T> Array<T>.toText(
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
