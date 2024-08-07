package cromega.studio.measurepedia.ui.activities.measures

import android.os.Bundle
import androidx.activity.compose.setContent
import cromega.studio.measurepedia.data.models.instances.BodyPart
import cromega.studio.measurepedia.data.models.instances.Field
import cromega.studio.measurepedia.data.models.instances.Person
import cromega.studio.measurepedia.data.models.instances.Record
import cromega.studio.measurepedia.extensions.extractIds
import cromega.studio.measurepedia.resources.utils.TablesUtils
import cromega.studio.measurepedia.ui.activities.generic.Activity
import cromega.studio.measurepedia.ui.activities.home.HomeActivity
import cromega.studio.measurepedia.ui.theme.MeasurepediaTheme

class MeasuresActivity : Activity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selectedPersonId: Int =
            intent.getIntExtra("selectedPersonId", 0)

        val selectedPerson: Person =
            TablesUtils
                .personsTable
                .readPerson(id = selectedPersonId)

        val bodyParts: Array<BodyPart> =
            TablesUtils
                .bodyPartsTable
                .readByActive()

        val fields: Array<Field> =
            TablesUtils
                .fieldsTable
                .readByActiveAndBodyParts(
                    bodyPartsIds = bodyParts.extractIds()
                )

        val records: Array<Record> =
            TablesUtils
                .recordsTable
                .readByPersonAndFields(
                    personId = selectedPersonId,
                    fieldIds = fields.extractIds()
                )

        setContent {
            MeasurepediaTheme {
                MeasuresState.initialize(
                    selectedPerson = selectedPerson,
                    bodyParts = bodyParts,
                    fields = fields,
                    records = records,
                    openHomeFunction = { changeActivity(HomeActivity::class) }
                )
                MeasuresScreen.Screen()
            }
        }
    }
}
