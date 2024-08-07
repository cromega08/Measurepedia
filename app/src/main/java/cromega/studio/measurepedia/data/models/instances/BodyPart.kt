package cromega.studio.measurepedia.data.models.instances

import cromega.studio.measurepedia.data.models.generic.Model
import cromega.studio.measurepedia.extensions.titlecase

class BodyPart(
    id: Int,
    private var name: String,
    var active: Boolean
) : Model(id = id)
{
    fun getName() = name.titlecase()
}
