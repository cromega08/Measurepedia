package cromega.studio.measurepedia.data.models.instances

import cromega.studio.measurepedia.data.models.generic.Model
import cromega.studio.measurepedia.extensions.isNotNullOrBlank
import cromega.studio.measurepedia.extensions.titlecase
import cromega.studio.measurepedia.extensions.toStringWithFormat
import java.util.Date
import java.util.Locale

class Person(
    id: Int,
    name: String,
    var alias: String,
    var updated: Date,
    var measured: Boolean
): Model(id = id)
{
    private val locale: Locale = Locale.US

    var name: String
        get() = field.titlecase()

    val hasAlias: Boolean = alias.isNotNullOrBlank()

    val searchableIdentifier: String =
        String
            .format(locale = locale, "%s %s", name.lowercase(), alias.lowercase())
            .lowercase(locale = locale)

    val updatedAsString: String =
        updated toStringWithFormat "dd-MM-yyyy"

    init {
        this.name = name
    }

    override fun clone(): Person {
        return Person(
            id = id,
            name = name,
            alias = alias,
            updated = updated,
            measured = measured
        )
    }
}
