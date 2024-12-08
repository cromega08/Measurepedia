package cromega.studio.measurepedia.data.managers.instances

import cromega.studio.measurepedia.data.database.tables.instances.BodyPartsTable
import cromega.studio.measurepedia.data.database.tables.instances.FieldsTable
import cromega.studio.measurepedia.data.database.tables.instances.RecordsTable
import cromega.studio.measurepedia.data.models.instances.BodyPart
import cromega.studio.measurepedia.extensions.extractIds

class BodyPartsManager(
    private val bodyPartsTable: BodyPartsTable,
    private val fieldsTable: FieldsTable,
    private val recordsTable: RecordsTable
) {
    fun readAll(): List<BodyPart> = bodyPartsTable.readAll()

    fun readByActive(active: Boolean = true): List<BodyPart> =
        bodyPartsTable.readByActive(active = active)

    fun insert(name: String, active: Boolean? = null) =
        bodyPartsTable.insert(
            name = name,
            active = active
        )

    fun update(id: Int, name: String, active: Boolean) =
        bodyPartsTable.update(
            id = id,
            name = name,
            active = active
        )

    fun delete(id: Int)
    {
        val fieldsIds: List<Int> =
            fieldsTable.readByBodyParts(bodyPartsIds = listOf(id)).extractIds()

        val recordsIds: List<Int> =
            recordsTable.readByFields(fieldsIds = fieldsIds).extractIds()

        recordsTable.deleteByIds(recordsIds)

        fieldsTable.deleteByIds(fieldsIds)

        bodyPartsTable.delete(id = id)
    }

    fun deleteByIds(ids: List<Int>) = ids.forEach { id -> delete(id = id) }
}