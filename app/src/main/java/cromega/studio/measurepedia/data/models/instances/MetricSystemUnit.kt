package cromega.studio.measurepedia.data.models.instances

import cromega.studio.measurepedia.data.models.generic.Model
import cromega.studio.measurepedia.extensions.titlecase

class MetricSystemUnit(
    id: Int,
    name: String,
    var abbreviation: String
): Model(id = id)
{
    private var _name: String

    init {
        this._name = name}

    var name: String
        get() = _name.titlecase()
        set(value) { _name = value }

    override fun clone(): MetricSystemUnit
    {
        return MetricSystemUnit(
            id = id,
            name = _name,
            abbreviation = abbreviation
        )
    }
}
