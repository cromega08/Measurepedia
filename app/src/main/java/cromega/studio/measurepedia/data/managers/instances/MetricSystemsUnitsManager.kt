package cromega.studio.measurepedia.data.managers.instances

import cromega.studio.measurepedia.data.database.tables.instances.MetricSystemsUnitsTable
import cromega.studio.measurepedia.data.database.tables.instances.RecordsTable
import cromega.studio.measurepedia.data.models.instances.MetricSystemUnit
import cromega.studio.measurepedia.data.models.instances.Record

class MetricSystemsUnitsManager(
    private val metricSystemsUnitsTable: MetricSystemsUnitsTable,
    private val recordsTable: RecordsTable
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

    fun delete(id: Int)
    {
        val metricSystemUnits: List<MetricSystemUnit> =
            metricSystemsUnitsTable.readAll()

        val records: List<Record> =
            recordsTable.readAll()

        val defaultMetricSystemUnit: MetricSystemUnit =
            metricSystemUnits.find { it.id != id }!!

        records
            .forEach { record ->
                if (record.metricSystemUnitId == id)
                    recordsTable
                        .update(
                            id = record.id,
                            personId = record.personId,
                            fieldId = record.fieldId,
                            measure = record.measure,
                            metricSystemUnitId = defaultMetricSystemUnit.id
                        )
            }

        metricSystemsUnitsTable.delete(id = id)
    }

    fun deleteByIds(ids: List<Int>) =
        ids.forEach { metricSystemUnitIndex -> delete(id = metricSystemUnitIndex) }
}