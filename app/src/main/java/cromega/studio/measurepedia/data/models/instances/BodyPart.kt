package cromega.studio.measurepedia.data.models.instances

import cromega.studio.measurepedia.data.models.generic.Model

class BodyPart(
    id: Int,
    var name: String,
    var active: Boolean
) : Model(id = id)
