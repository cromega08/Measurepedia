package cromega.studio.measurepedia.ui.activities.fields

import android.content.res.Resources
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import cromega.studio.measurepedia.R
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
    private val deletedBodyPartsIds: MutableList<Int> = mutableListOf()

    private val deletedFieldsIds: MutableList<Int> = mutableListOf()

    private val bodyPartsState: SnapshotStateList<BodyPart> =
        mutableStateListOf(
            *(
                tablesManager
                    .bodyPartsManager
                    .readAll()
                    .toTypedArray()
                )
        )

    val bodyParts: List<BodyPart> = bodyPartsState

    private val fieldsState: SnapshotStateList<Field> =
        mutableStateListOf(
            *(
                tablesManager
                    .fieldsManager
                    .readAll()
                    .toTypedArray()
                )
        )

    val fields: List<Field> = fieldsState

    fun findBodyPartById(bodyPartId: Int): BodyPart =
        bodyPartsState.find { bodyPart -> bodyPart.id == bodyPartId }!!

    fun filterFieldsByBodyPartId(bodyPartId: Int): List<Field> =
        fields.filter { field -> field.bodyPartId == bodyPartId }

    fun modifyBodyPartName(bodyPartIndex: Int, newName: String)
    {
        bodyPartsState[bodyPartIndex] =
            bodyPartsState[bodyPartIndex]
                .apply {
                    this.name = newName
                }.clone()
    }

    fun modifyBodyPartActiveAndPropagate(bodyPartIndex: Int, active: Boolean)
    {
        modifyBodyPartActive(
            bodyPartIndex = bodyPartIndex,
            isActive = active
        )

        modifyFieldsActiveByBodyPart(
            bodyPartIndex = bodyPartIndex,
            areActive = active
        )
    }

    fun modifyBodyPartActive(bodyPartIndex: Int, isActive: Boolean)
    {
        bodyPartsState[bodyPartIndex] =
            bodyPartsState[bodyPartIndex]
                .apply {
                    this.active = isActive
                }.clone()
    }

    fun removeBodyPartAndPropagate(bodyPart: BodyPart)
    {
        bodyPartsState.remove(bodyPart)

        if (bodyPart.id != 0) deletedBodyPartsIds.add(bodyPart.id)

        removeFieldsByBodyPartId(bodyPartId = bodyPart.id)
    }

     fun createField(bodyPartId: Int) =
         fieldsState
             .add(
                 Field(
                     id = 0,
                     name = resources.getString(R.string.field_name),
                     bodyPartId = bodyPartId,
                     active = false
                 )
             )

    fun modifyFieldsActiveByBodyPart(bodyPartIndex: Int, areActive: Boolean)
    {
        val bodyPart: BodyPart = bodyPartsState[bodyPartIndex]

        fieldsState
            .forEach {field ->
                val fieldIndex: Int = fieldsState.indexOf(field)

                if (field.bodyPartId == bodyPart.id)
                    modifyFieldActive(
                        fieldIndex = fieldIndex,
                        isActive = areActive
                    )
            }
    }

    fun modifyFieldName(fieldIndex: Int, newName: String)
    {
        fieldsState[fieldIndex] =
            fieldsState[fieldIndex]
                .apply {
                    this.name = newName
                }.clone()
    }

    fun modifyFieldActiveAndPropagate(fieldIndex: Int, isActive: Boolean)
    {
        modifyBodyPartActive(
            bodyPartIndex =
                bodyPartsState
                    .indexOf(
                        findBodyPartById(
                            fieldsState[fieldIndex].bodyPartId
                        )
                    ),
            isActive = isActive
        )

        modifyFieldActive(
            fieldIndex = fieldIndex,
            isActive = isActive
        )
    }

    fun modifyFieldActive(fieldIndex: Int, isActive: Boolean)
    {
        fieldsState[fieldIndex] =
            fieldsState[fieldIndex]
                .apply {
                    this.active = isActive
                }.clone()
    }

    fun removeFieldsByBodyPartId(bodyPartId: Int) =
        filterFieldsByBodyPartId(bodyPartId)
            .forEach { field -> removeField(field) }

    fun removeField(field: Field)
    {
        fieldsState.remove(field)

        if (field.id != 0) deletedFieldsIds.add(field.id)
    }

    fun updateData()
    {
        flushDeletes()
        flushUpdates()
    }

    fun flushDeletes()
    {
        tablesManager
            .bodyPartsManager
            .deleteByIds(ids = deletedBodyPartsIds)

        tablesManager
            .fieldsManager
            .deleteByIds(ids = deletedFieldsIds)
    }

    fun flushUpdates()
    {
        flushUpdateBodyParts()
        flushUpdateFields()
    }

    fun flushUpdateBodyParts() =
        bodyPartsState
            .forEach {bodyPart ->
                tablesManager
                    .bodyPartsManager
                    .run {
                        if (bodyPart.id == 0)
                            insert(
                                name = bodyPart.name,
                                active = bodyPart.active
                            )
                        else
                            update(
                                id = bodyPart.id,
                                name = bodyPart.name,
                                active = bodyPart.active
                            )
                    }
            }

    fun flushUpdateFields() =
        fieldsState
            .forEach { field ->
                tablesManager
                    .fieldsManager
                    .run {
                        if (field.id == 0)
                            insert(
                                name = field.name,
                                bodyPartId = field.bodyPartId,
                                active = field.active
                            )
                        else
                            update(
                                id = field.id,
                                name = field.name,
                                bodyPartId = field.bodyPartId,
                                active = field.active
                            )
                    }
            }

    fun openHomeActivity() = openHomeFunction()
}