package cromega.studio.measurepedia.data.managers.instances

import cromega.studio.measurepedia.data.database.tables.instances.MetricSystemsUnitsTable
import cromega.studio.measurepedia.data.models.instances.MetricSystemUnit

class MetricSystemsUnitsManager(
    private val metricSystemsUnitsTable: MetricSystemsUnitsTable
) {
    fun readAll(): List<MetricSystemUnit> = metricSystemsUnitsTable.readAll()

    fun insert(name: String, abbreviation: String) =
        metricSystemsUnitsTable
            .insert(
                name = name,
                abbreviation = abbreviation
            )

    fun update(id: Int, name: String, abbreviation: String) =
        metricSystemsUnitsTable
            .update(
                id = id,
                name = name,
                abbreviation = abbreviation
            )
}