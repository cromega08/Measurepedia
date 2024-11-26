package cromega.studio.measurepedia.data.models.instances

import cromega.studio.measurepedia.data.models.generic.Model

class Record(
    id: Int,
    val personId: Int,
    val fieldId: Int,
    var measure: Float,
    var metricSystemUnitId: Int,
    var measurePrintable: String = measure.toString()
): Model(id = id)
{
    fun requestMeasureUpdate()
    {
        measure = measurePrintable.toFloat()
        measurePrintable = measure.toString()
    }

    override fun clone(): Record
    {
        return Record(
            id = id,
            personId = personId,
            fieldId = fieldId,
            measure = measure,
            metricSystemUnitId = metricSystemUnitId,
            measurePrintable = measurePrintable
        )
    }
}
