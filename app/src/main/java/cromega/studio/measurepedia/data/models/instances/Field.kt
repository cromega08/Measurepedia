package cromega.studio.measurepedia.data.models.instances

import cromega.studio.measurepedia.data.models.generic.Model

class Field(
    id: Int,
    var name: String,
    var bodyPartId: Int,
    var active: Boolean
): Model(id = id)
