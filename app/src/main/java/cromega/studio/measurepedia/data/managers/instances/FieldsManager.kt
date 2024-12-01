package cromega.studio.measurepedia.data.managers.instances

import cromega.studio.measurepedia.data.database.tables.instances.FieldsTable
import cromega.studio.measurepedia.data.models.instances.Field
import cromega.studio.measurepedia.extensions.isNeitherNullOrEmpty
import cromega.studio.measurepedia.extensions.isNotNull
import cromega.studio.measurepedia.extensions.isNull

class FieldsManager(
    private val fieldsTable: FieldsTable
) {
   fun readAll(): Array<Field> = fieldsTable.readAll()

    fun readFilteredBy(
        active: Boolean? = null,
        bodyPartsIds: Array<Int>? = null
    ): Array<Field>
    {
        return when
        {
            active.isNotNull() && bodyPartsIds.isNullOrEmpty() ->
                fieldsTable.readByActive(active = active!!)

            active.isNull() && bodyPartsIds.isNeitherNullOrEmpty() ->
                fieldsTable.readByBodyParts(bodyPartsIds = bodyPartsIds!!)

            active.isNotNull() && bodyPartsIds.isNeitherNullOrEmpty() ->
                fieldsTable.readByActiveAndBodyParts(
                    active = active!!,
                    bodyPartsIds = bodyPartsIds!!
                )

            else -> throw IllegalArgumentException("All arguments are null")
        }
    }

    fun insert(name: String) = fieldsTable.insert(name = name)

    fun update(
        id: Int,
        name: String,
        available: Boolean
    ) =
        fieldsTable
            .update(
                id = id,
                name = name,
                available = available
            )
}