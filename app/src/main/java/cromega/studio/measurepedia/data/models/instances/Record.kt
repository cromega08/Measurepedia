package cromega.studio.measurepedia.data.models.instances

import cromega.studio.measurepedia.data.models.generic.Model

class Record(
    id: Int,
    val personId: Int,
    val fieldId: Int,
    val measure: Float,
    val metricSystemUnitId: Int
): Model(id = id)
