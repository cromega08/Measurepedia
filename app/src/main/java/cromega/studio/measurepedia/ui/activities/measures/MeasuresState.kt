package cromega.studio.measurepedia.ui.activities.measures

import androidx.compose.runtime.Composable
import cromega.studio.measurepedia.data.models.instances.BodyPart
import cromega.studio.measurepedia.data.models.instances.Field
import cromega.studio.measurepedia.data.models.instances.Person
import cromega.studio.measurepedia.data.models.instances.Record

internal object MeasuresState
{
    lateinit var selectedPerson: Person
    lateinit var bodyParts: Array<BodyPart>
    lateinit var fields: Array<Field>
    lateinit var records: Array<Record>
    private lateinit var openHomeFunction: () -> Unit

    @Composable
    fun initialize(
        selectedPerson: Person,
        bodyParts: Array<BodyPart>,
        fields: Array<Field>,
        records: Array<Record>,
        openHomeFunction: () -> Unit
    ) {
        this.selectedPerson = selectedPerson
        this.bodyParts = bodyParts
        this.fields = fields
        this.records = records
        this.openHomeFunction = openHomeFunction
    }

    fun openHomeActivity() = openHomeFunction()
}