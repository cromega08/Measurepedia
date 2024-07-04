package cromega.studio.measurepedia.extensions

import java.text.SimpleDateFormat

inline fun String?.isNotNullOrBlank() = !this.isNullOrBlank()

infix fun String.toDateWithFormat(dateFormat: SimpleDateFormat) =
    dateFormat.parse(this)!!
