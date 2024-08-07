package cromega.studio.measurepedia.extensions

import cromega.studio.measurepedia.data.models.generic.Model

inline fun <reified Instance : Model> Array<Instance>.extractIds(): Array<Int> =
    this.map { it.id }.toTypedArray()

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
