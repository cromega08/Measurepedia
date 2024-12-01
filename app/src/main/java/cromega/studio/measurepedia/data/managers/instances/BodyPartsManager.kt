package cromega.studio.measurepedia.data.managers.instances

import cromega.studio.measurepedia.data.database.tables.instances.BodyPartsTable
import cromega.studio.measurepedia.data.models.instances.BodyPart

class BodyPartsManager(
    private val bodyPartsTable: BodyPartsTable
) {
    fun readAll(): Array<BodyPart> = bodyPartsTable.readAll()

    fun readByActive(active: Boolean = true): Array<BodyPart> =
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
}