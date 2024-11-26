package cromega.studio.measurepedia.data.models.instances

import cromega.studio.measurepedia.data.models.generic.Model
import cromega.studio.measurepedia.extensions.titlecase

class Field(
    id: Int,
    var name: String,
    var bodyPartId: Int,
    var active: Boolean
): Model(id = id)
{
    fun getTitledName(): String = name.titlecase()
    override fun clone(): Model
    {
        return Field(
            id = id,
            name = name,
            bodyPartId = bodyPartId,
            active = active
        )
    }
}
