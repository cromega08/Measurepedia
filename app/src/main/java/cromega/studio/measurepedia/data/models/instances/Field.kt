package cromega.studio.measurepedia.data.models.instances

import cromega.studio.measurepedia.data.models.generic.Model
import cromega.studio.measurepedia.extensions.titlecase

class Field(
    id: Int,
    name: String,
    var bodyPartId: Int,
    var active: Boolean
): Model(id = id)
{
    private var _name: String

    var name: String
        get() = _name.titlecase()
        set(value) { _name = value }

    init {
        this._name = name
    }

    override fun clone(): Field
    {
        return Field(
            id = id,
            name = _name,
            bodyPartId = bodyPartId,
            active = active
        )
    }
}
