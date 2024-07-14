package cromega.studio.measurepedia.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

infix fun Date.toStringWithFormat(format: String): String =
    SimpleDateFormat(format, Locale.US).format(this)