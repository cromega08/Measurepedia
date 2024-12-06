package cromega.studio.measurepedia.data.models.instances

import cromega.studio.measurepedia.data.models.generic.Model
import cromega.studio.measurepedia.extensions.titlecase

class BodyPart(
    id: Int,
    name: String,
    var active: Boolean
) : Model(id = id)
{
    private var _name: String

    var name: String
        get() = _name.titlecase()
        set(value) { _name = value }

    init {
        this._name = name
    }

    override fun clone(): BodyPart {
        return BodyPart(
            id = id,
            name = _name,
            active = active
        )
    }
}
