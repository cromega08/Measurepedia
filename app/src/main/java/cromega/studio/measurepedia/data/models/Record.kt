package cromega.studio.measurepedia.data.models

data class Record(
    val id: Int,
    val personId: Int,
    val fieldId: Int,
    val measure: Float,
    val metricSystemUnitId: Int
)
