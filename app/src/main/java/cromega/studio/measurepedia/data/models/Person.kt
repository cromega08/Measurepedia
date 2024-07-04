package cromega.studio.measurepedia.data.models

import java.util.Date

data class Person(
    val id: Int,
    val name: String,
    val alias: String?,
    val updated: Date,
    val measured: Boolean
)
