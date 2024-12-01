package cromega.studio.measurepedia.ui.activities.fields

import android.content.res.Resources
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import cromega.studio.measurepedia.data.managers.general.TablesManager
import cromega.studio.measurepedia.data.models.instances.BodyPart
import cromega.studio.measurepedia.data.models.instances.Field
import cromega.studio.measurepedia.ui.activities.generic.ActivityViewModel

class FieldsViewModel(
    tablesManager: TablesManager,
    resources: Resources,
    private val openHomeFunction: () -> Unit
): ActivityViewModel(
    tablesManager = tablesManager,
    resources = resources
) {
    private val bodyPartsState: SnapshotStateList<BodyPart> =
        mutableStateListOf(
            *(
                tablesManager
                    .bodyPartsManager
                    .readAll()
                )
        )

    val bodyParts: Array<BodyPart> = bodyPartsState.toTypedArray()

    private val fieldsState: SnapshotStateList<Field> =
        mutableStateListOf(
            *(
                tablesManager
                    .fieldsManager
                    .readAll()
                )
        )

    val fields: Array<Field> = fieldsState.toTypedArray()

    fun openHomeActivity() = openHomeFunction()
}