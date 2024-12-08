package cromega.studio.measurepedia.ui.activities.fields

import android.content.res.Resources
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import cromega.studio.measurepedia.R
import cromega.studio.measurepedia.data.models.instances.BodyPart
import cromega.studio.measurepedia.data.models.instances.Field
import cromega.studio.measurepedia.ui.activities.generic.ActivityScreen
import cromega.studio.measurepedia.ui.components.elements.AddIcon
import cromega.studio.measurepedia.ui.components.elements.ClearIcon
import cromega.studio.measurepedia.ui.components.elements.RoundedCornerButton
import cromega.studio.measurepedia.ui.components.elements.SpacerHorizontalLine
import cromega.studio.measurepedia.ui.components.elements.SpacerVerticalSmall
import cromega.studio.measurepedia.ui.components.layouts.CardConstraintLayout
import cromega.studio.measurepedia.ui.components.layouts.GenericBodyLazyColumn
import cromega.studio.measurepedia.ui.components.layouts.GenericFooterRow

class FieldsScreen(
    viewModel: FieldsViewModel,
    resources: Resources
): ActivityScreen<FieldsViewModel>(
    viewModel = viewModel,
    resources = resources
) {
    @Composable
    override fun Header() = Spacer(modifier = Modifier.height(40.dp))

    @Composable
    override fun Main(paddingValues: PaddingValues) =
        GenericBodyLazyColumn(
            contentPadding = paddingValues
        ) {
            val bodyParts: List<BodyPart> = viewModel.bodyParts

            items(bodyParts.size) {bodyPartIndex ->
                val bodyPart: BodyPart = bodyParts[bodyPartIndex]

                CardConstraintLayout(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val (bodyPartRef, bodyPartActiveRef, deleteBodyPartRef, separationRef, fieldsRef, createFieldRef) = createRefs()

                    Box(
                        modifier =
                        Modifier
                            .padding(horizontal = 67.5.dp)
                            .constrainAs(bodyPartRef) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(anchor = bodyPartActiveRef.start, margin = 5.dp)
                            }
                    ) {
                        TextField(
                            value = bodyPart.name,
                            onValueChange = { userInput ->
                                viewModel
                                    .modifyBodyPartName(
                                        bodyPartIndex = bodyPartIndex,
                                        newName = userInput
                                    )
                            },
                            singleLine = true,
                            maxLines = 1
                        )
                    }

                    Switch(
                        modifier =
                            Modifier
                                .constrainAs(bodyPartActiveRef) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(bodyPartRef.bottom)
                                    end.linkTo(anchor = deleteBodyPartRef.start, margin = 5.dp)
                                },
                        checked = bodyPart.active,
                        onCheckedChange = {
                            viewModel
                                .modifyBodyPartActiveAndPropagate(
                                    bodyPartIndex = bodyPartIndex,
                                    active = it
                                )
                        }
                    )

                    FilledIconButton(
                        modifier =
                        Modifier
                            .constrainAs(deleteBodyPartRef) {
                                top.linkTo(bodyPartActiveRef.top)
                                bottom.linkTo(bodyPartActiveRef.bottom)
                                end.linkTo(anchor = parent.end, margin = 7.5.dp)
                            },
                        onClick = { viewModel.removeBodyPartAndPropagate(bodyPart = bodyPart) },
                        content = { ClearIcon() }
                    )

                    SpacerHorizontalLine(
                        modifier =
                        Modifier
                            .height(2.5.dp)
                            .constrainAs(separationRef) {
                                top.linkTo(anchor = bodyPartRef.bottom, margin = 5.dp)
                                start.linkTo(anchor = parent.start, margin = 7.5.dp)
                                end.linkTo(anchor = parent.end, margin = 7.5.dp)

                                width = Dimension.fillToConstraints
                            },
                        color = Color.White
                    )

                    Column(
                        modifier =
                            Modifier
                                .constrainAs(fieldsRef) {
                                    top.linkTo(anchor = separationRef.bottom, margin = 5.dp)
                                    start.linkTo(anchor = parent.start, margin = 7.5.dp)
                                    end.linkTo(anchor = parent.end, margin = 7.5.dp)

                                    width = Dimension.fillToConstraints
                                }
                    ) {
                        val fields: List<Field> = viewModel.filterFieldsByBodyPartId(bodyPartId = bodyPart.id)

                        fields.forEach {field ->

                            val generalFieldIndex: Int = viewModel.fields.indexOf(field)

                            ConstraintLayout(
                                modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp)
                            ) {
                                val (fieldRef, fieldActiveRef, deleteFieldRef) = createRefs()

                                Box(
                                    modifier =
                                    Modifier
                                        .padding(horizontal = 60.dp)
                                        .constrainAs(fieldRef) {
                                            top.linkTo(parent.top)
                                            start.linkTo(parent.start)
                                            end.linkTo(anchor = fieldActiveRef.start, margin = 8.dp)
                                        }
                                ) {
                                    TextField(
                                        value = field.name,
                                        onValueChange = { userInput ->
                                            viewModel
                                                .modifyFieldName(
                                                    fieldIndex = generalFieldIndex,
                                                    newName = userInput
                                                )
                                        },
                                        singleLine = true,
                                        maxLines = 1
                                    )
                                }

                                Switch(
                                    modifier =
                                    Modifier
                                        .constrainAs(fieldActiveRef) {
                                            top.linkTo(parent.top)
                                            bottom.linkTo(fieldRef.bottom)
                                            end.linkTo(anchor = deleteFieldRef.start, margin = 5.dp)
                                        },
                                    checked = field.active,
                                    onCheckedChange = { isChecked ->
                                        if (isChecked && !bodyPart.active)
                                        {
                                            viewModel
                                                .modifyFieldActiveAndPropagate(
                                                    fieldIndex = generalFieldIndex,
                                                    isActive = true
                                                )
                                        }
                                        else
                                        {
                                            viewModel
                                                .modifyFieldActive(
                                                    fieldIndex = generalFieldIndex,
                                                    isActive = isChecked
                                                )
                                        }
                                    }
                                )

                                FilledIconButton(
                                    modifier =
                                    Modifier
                                        .constrainAs(deleteFieldRef) {
                                            top.linkTo(fieldActiveRef.top)
                                            bottom.linkTo(fieldActiveRef.bottom)
                                            end.linkTo(anchor = parent.end)
                                        },
                                    onClick = { viewModel.removeField(field = field) },
                                    content = { ClearIcon() }
                                )
                            }
                        }
                    }

                    RoundedCornerButton(
                        modifier =
                            Modifier
                                .constrainAs(createFieldRef) {
                                    top.linkTo(anchor = fieldsRef.bottom, margin = 5.dp)
                                    bottom.linkTo(anchor = parent.bottom, margin = 5.dp)
                                    start.linkTo(anchor = parent.start, margin = 7.5.dp)
                                    end.linkTo(anchor = parent.end, margin = 7.5.dp)

                                    width = Dimension.fillToConstraints
                                },
                        onClick = { viewModel.createField(bodyPartId = bodyPart.id) }
                    ) { AddIcon() }
                }
            }
        }

    @Composable
    override fun Footer() =
        GenericFooterRow {
            RoundedCornerButton(
                modifier =
                Modifier
                    .fillMaxWidth(0.8f),
                onClick = {
                    viewModel.updateData()
                    viewModel.openHomeActivity()
                }
            ) {
                Column {
                    SpacerVerticalSmall()

                    Text(
                        modifier = Modifier.scale(1.5f),
                        text = resources.getString(R.string.update_fields)
                    )

                    SpacerVerticalSmall()
                }
            }
        }
}