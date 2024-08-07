package cromega.studio.measurepedia.data.models.instances

import cromega.studio.measurepedia.data.models.generic.Model

class MetricSystemUnit(
    id: Int,
    val name: String,
    val abbreviation: String
): Model(id = id)
