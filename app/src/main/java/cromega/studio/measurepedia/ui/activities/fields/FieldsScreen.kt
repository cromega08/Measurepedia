package cromega.studio.measurepedia.ui.activities.fields

import android.content.res.Resources
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import cromega.studio.measurepedia.R
import cromega.studio.measurepedia.data.models.instances.BodyPart
import cromega.studio.measurepedia.data.models.instances.Field
import cromega.studio.measurepedia.data.models.instances.MetricSystemUnit
import cromega.studio.measurepedia.ui.activities.generic.ActivityScreen
import cromega.studio.measurepedia.ui.components.elements.AddIcon
import cromega.studio.measurepedia.ui.components.elements.ClearIcon
import cromega.studio.measurepedia.ui.components.elements.PersonIcon
import cromega.studio.measurepedia.ui.components.elements.RoundedCornerButton
import cromega.studio.measurepedia.ui.components.elements.SpacerHorizontalLine
import cromega.studio.measurepedia.ui.components.elements.SpacerHorizontalSmall
import cromega.studio.measurepedia.ui.components.elements.SpacerVerticalMedium
import cromega.studio.measurepedia.ui.components.elements.SpacerVerticalSmall
import cromega.studio.measurepedia.ui.components.elements.StringTextField
import cromega.studio.measurepedia.ui.components.layouts.CardConstraintLayout
import cromega.studio.measurepedia.ui.components.layouts.ColumnOrderedDialog
import cromega.studio.measurepedia.ui.components.layouts.FinalBackgroundBox
import cromega.studio.measurepedia.ui.components.layouts.GenericBodyLazyColumn
import cromega.studio.measurepedia.ui.components.layouts.GenericFooterRow
import cromega.studio.measurepedia.ui.components.layouts.GenericHeaderColumn

class FieldsScreen(
    viewModel: FieldsViewModel,
    resources: Resources,
    darkTheme: Boolean,
    private val showToastFunction: (Int) -> Unit
): ActivityScreen<FieldsViewModel>(
    viewModel = viewModel,
    resources = resources,
    darkTheme = darkTheme
) {
    @Composable
    override fun Screen() =
        Scaffold(
            topBar = { Header() },
            content = {
                Main(it)

                if (viewModel.hasEditingBodyPart) BodyPartEditorDialog()
            },
            bottomBar = { Footer() }
        )

    @Composable
    override fun Header() =
        GenericHeaderColumn(
            modifier =
                Modifier
                    .background(
                        color = contrastColor,
                        shape = RectangleShape
                    )
        ) {
            val selectedTabIndex: Int = viewModel.tabIndex

            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = contrastColor,
                contentColor = mainColor
            ) {
                viewModel.tabs.forEachIndexed { index, title ->
                    Tab(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                        selectedContentColor = secondaryColor,
                        unselectedContentColor = secondaryColor,
                        selected = (selectedTabIndex == index),
                        onClick = { viewModel.tabIndex = index },
                        content = { Text(text = resources.getString(title)) }
                    )
                }
            }
        }

    @Composable
    override fun Main(paddingValues: PaddingValues) =
        FinalBackgroundBox(
            backgroundColor = mainColor,
            oppositeColor = contrastColor
        ) {
            GenericBodyLazyColumn(
                contentPadding = paddingValues
            ){
                when(viewModel.tabIndex)
                {
                    0 -> BodyPartsAndFieldsTab(lazyListScope = this)
                    1 -> MetricSystemsUnitsTab(lazyListScope = this)
                }
            }
        }

    @Composable
    override fun Footer() =
        GenericFooterRow(
            modifier =
                Modifier.background(
                    color = mainColor,
                    shape = RectangleShape
                )
        ) {
            RoundedCornerButton(
                modifier =
                    Modifier
                        .weight(0.8f),
                buttonColors =
                ButtonColors(
                    containerColor = tertiaryColor,
                    contentColor = mainColor,
                    disabledContentColor = tertiaryColor,
                    disabledContainerColor = mainColor
                ),
                onClick = {
                    viewModel.updateData()
                    viewModel.openHomeActivity()
                }
            ) {
                Column {
                    SpacerVerticalSmall()

                    Text(
                        modifier = Modifier.scale(1.5f),
                        text = resources.getString(R.string.update_data)
                    )

                    SpacerVerticalSmall()
                }
            }

            SpacerHorizontalSmall(modifier = Modifier.weight(0.1f))

            RoundedCornerButton(
                modifier =
                    Modifier
                        .weight(0.2f),
                buttonColors =
                ButtonColors(
                    containerColor = tertiaryColor,
                    contentColor = mainColor,
                    disabledContentColor = tertiaryColor,
                    disabledContainerColor = mainColor
                ),
                onClick = {
                    if (viewModel.tabIndex == 0) viewModel.openEditorDialogWithEmptyBodyPart()
                    else viewModel.createMetricSystemUnit()
                }
            ) {
                Column {
                    SpacerVerticalSmall()

                    AddIcon()

                    SpacerVerticalSmall()
                }
            }
        }

    private fun BodyPartsAndFieldsTab(lazyListScope: LazyListScope) =
        lazyListScope.run {
            val bodyParts: List<BodyPart> = viewModel.bodyParts

            items(bodyParts.size) {bodyPartIndex ->
                val bodyPart: BodyPart = bodyParts[bodyPartIndex]

                CardConstraintLayout(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = secondaryColor
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
                        StringTextField(
                            backgroundColor = mainColor,
                            textColor = contrastColor,
                            labelText = resources.getString(R.string.body_part_name),
                            value = bodyPart.name,
                            onValueChange = { userInput ->
                                viewModel
                                    .modifyBodyPartName(
                                        bodyPartIndex = bodyPartIndex,
                                        newName = userInput
                                    )
                            }
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
                        colors = SwitchColors(
                            checkedThumbColor = mainColor,
                            checkedTrackColor = secondaryColor,
                            checkedBorderColor = contrastColor,
                            checkedIconColor = contrastColor,
                            uncheckedThumbColor = mainColor,
                            uncheckedTrackColor = tertiaryColor,
                            uncheckedBorderColor = contrastColor,
                            uncheckedIconColor = contrastColor,
                            disabledCheckedThumbColor = mainColor,
                            disabledCheckedTrackColor = secondaryColor,
                            disabledCheckedBorderColor = contrastColor,
                            disabledCheckedIconColor = contrastColor,
                            disabledUncheckedThumbColor = mainColor,
                            disabledUncheckedTrackColor = tertiaryColor,
                            disabledUncheckedBorderColor = contrastColor,
                            disabledUncheckedIconColor = contrastColor
                        ),
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
                        colors = IconButtonColors(
                            containerColor = tertiaryColor,
                            contentColor = mainColor,
                            disabledContainerColor = tertiaryColor,
                            disabledContentColor = mainColor
                        ),
                        onClick = {
                            if (viewModel.validateBodyParts())
                                viewModel.removeBodyPartAndPropagate(bodyPart = bodyPart)
                            else showToastFunction(R.string.insufficient_body_parts)
                                  },
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
                                    StringTextField(
                                        backgroundColor = mainColor,
                                        textColor = contrastColor,
                                        labelText = resources.getString(R.string.field_name),
                                        value = field.name,
                                        onValueChange = { userInput ->
                                            viewModel
                                                .modifyFieldName(
                                                    fieldIndex = generalFieldIndex,
                                                    newName = userInput
                                                )
                                        }
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
                                    colors = SwitchColors(
                                        checkedThumbColor = mainColor,
                                        checkedTrackColor = secondaryColor,
                                        checkedBorderColor = contrastColor,
                                        checkedIconColor = contrastColor,
                                        uncheckedThumbColor = mainColor,
                                        uncheckedTrackColor = tertiaryColor,
                                        uncheckedBorderColor = contrastColor,
                                        uncheckedIconColor = contrastColor,
                                        disabledCheckedThumbColor = mainColor,
                                        disabledCheckedTrackColor = secondaryColor,
                                        disabledCheckedBorderColor = contrastColor,
                                        disabledCheckedIconColor = contrastColor,
                                        disabledUncheckedThumbColor = mainColor,
                                        disabledUncheckedTrackColor = tertiaryColor,
                                        disabledUncheckedBorderColor = contrastColor,
                                        disabledUncheckedIconColor = contrastColor
                                    ),
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
                                    colors = IconButtonColors(
                                        containerColor = tertiaryColor,
                                        contentColor = mainColor,
                                        disabledContainerColor = tertiaryColor,
                                        disabledContentColor = mainColor
                                    ),
                                    onClick = {
                                        if (viewModel.validateFields(bodyPart = bodyPart))
                                            viewModel.removeField(field = field)
                                        else showToastFunction(R.string.insufficient_fields)
                                              },
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
                        buttonColors =
                        ButtonColors(
                            containerColor = tertiaryColor,
                            contentColor = mainColor,
                            disabledContentColor = tertiaryColor,
                            disabledContainerColor = mainColor
                        ),
                        onClick = { viewModel.createField(bodyPartId = bodyPart.id) }
                    ) { AddIcon() }
                }
            }
        }

    private fun MetricSystemsUnitsTab(lazyListScope: LazyListScope) =
        lazyListScope.run {
            val metricSystemUnits: List<MetricSystemUnit> = viewModel.metricSystemUnits

            items(metricSystemUnits.size) {metricSystemUnitIndex ->
                val metricSystemUnit: MetricSystemUnit = metricSystemUnits[metricSystemUnitIndex]

                Column(
                    modifier =
                    Modifier
                        .background(color = secondaryColor, shape = RoundedCornerShape(15.dp))
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    val widthFraction: Float = 0.9f

                    SpacerVerticalMedium()

                    StringTextField(
                        modifier =
                        Modifier
                            .fillMaxWidth(fraction = widthFraction),
                        backgroundColor = mainColor,
                        textColor = contrastColor,
                        labelText = resources.getString(R.string.metric_system_unit_name),
                        value = metricSystemUnit.name,
                        onValueChange = { userInput ->
                            viewModel
                                .modifyMetricSystemUnitName(
                                    fieldIndex = metricSystemUnitIndex,
                                    newName = userInput
                                )
                        }
                    )

                    SpacerVerticalSmall()

                    StringTextField(
                        modifier =
                        Modifier
                            .fillMaxWidth(fraction = widthFraction),
                        backgroundColor = mainColor,
                        textColor = contrastColor,
                        labelText = resources.getString(R.string.metric_system_unit_abbreviation),
                        maxCharacters = 7,
                        value = metricSystemUnit.abbreviation,
                        onValueChange = { userInput ->
                            viewModel
                                .modifyMetricSystemUnitAbbreviation(
                                    fieldIndex = metricSystemUnitIndex,
                                    newAbbreviation = userInput
                                )
                        }
                    )

                    SpacerVerticalSmall()
                    SpacerHorizontalLine(
                        modifier = Modifier.fillMaxWidth(widthFraction),
                        height = 2.5.dp,
                        color = tertiaryColor
                    )
                    SpacerVerticalSmall()

                    Row(
                        modifier =
                        Modifier
                            .fillMaxWidth(fraction = widthFraction - 0.1f),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = resources.getString(R.string.set_as_default), color = contrastColor)

                        Switch(
                            colors = SwitchColors(
                                checkedThumbColor = mainColor,
                                checkedTrackColor = secondaryColor,
                                checkedBorderColor = contrastColor,
                                checkedIconColor = contrastColor,
                                uncheckedThumbColor = mainColor,
                                uncheckedTrackColor = tertiaryColor,
                                uncheckedBorderColor = contrastColor,
                                uncheckedIconColor = contrastColor,
                                disabledCheckedThumbColor = mainColor,
                                disabledCheckedTrackColor = secondaryColor,
                                disabledCheckedBorderColor = contrastColor,
                                disabledCheckedIconColor = contrastColor,
                                disabledUncheckedThumbColor = mainColor,
                                disabledUncheckedTrackColor = tertiaryColor,
                                disabledUncheckedBorderColor = contrastColor,
                                disabledUncheckedIconColor = contrastColor
                            ),
                            checked = viewModel.defaultMetricSystemUnitId == metricSystemUnit.id,
                            onCheckedChange = {
                                viewModel.defaultMetricSystemUnitId = metricSystemUnit.id
                                viewModel.defaultMetricSystemUnitIndex = metricSystemUnitIndex
                            }
                        )
                    }

                    SpacerVerticalSmall()

                    Row(
                        modifier =
                        Modifier
                            .fillMaxWidth(fraction = widthFraction - 0.1f),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = resources.getString(R.string.delete_unit), color = contrastColor)

                        FilledIconButton(
                            onClick = {
                                if (viewModel.validateMetricSystemUnits())
                                    viewModel.removeMetricSystemUnit(metricSystemUnit = metricSystemUnit)
                                else showToastFunction(R.string.insufficient_metric_systems_units)
                                      },
                            colors = IconButtonColors(
                                containerColor = tertiaryColor,
                                contentColor = mainColor,
                                disabledContainerColor = tertiaryColor,
                                disabledContentColor = mainColor
                            ),
                            content = { ClearIcon() }
                        )
                    }

                    SpacerVerticalMedium()
                }
            }
        }

    @Composable
    private fun BodyPartEditorDialog() =
        ColumnOrderedDialog(
            backgroundColor = secondaryColor,
            columnModifier = Modifier.fillMaxWidth(),
            onDismissRequest = { viewModel.editingBodyPart = null }
        ) {
            val bodyPart: BodyPart = viewModel.editingBodyPart!!
            val missingName: Boolean = viewModel.editingBodyPartNameMissing

            SpacerVerticalMedium()

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) { PersonIcon() }

            SpacerVerticalSmall()

            StringTextField(
                modifier = Modifier.fillMaxWidth(0.8f),
                backgroundColor = mainColor,
                textColor = contrastColor,
                hasError = missingName,
                labelText = resources.getString(R.string.body_part_name),
                value = bodyPart.name,
                onValueChange = { userInput -> viewModel.updateEditingBodyPartName(newName = userInput) },
            )

            Row(
                modifier = Modifier.fillMaxWidth(0.7f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = resources.getString(R.string.active))

                RadioButton(
                    selected = viewModel.editingBodyPart!!.active,
                    onClick = { viewModel.invertEditingBodyPartActive() }
                )
            }

            SpacerVerticalSmall()

            RoundedCornerButton(
                modifier =
                Modifier.fillMaxWidth(0.8f),
                buttonColors =
                ButtonColors(
                    containerColor = tertiaryColor,
                    contentColor = mainColor,
                    disabledContentColor = tertiaryColor,
                    disabledContainerColor = mainColor
                ),
                onClick = {
                    if (viewModel.editingBodyPart!!.name.isBlank())
                        viewModel.editingBodyPartNameMissing = true
                    else
                        viewModel.finishUpdateEditingBodyPart()
                }
            ) {
                Column {
                    SpacerVerticalSmall()

                    Text(
                        modifier = Modifier.scale(1.5f),
                        text = resources.getString(R.string.create_body_part)
                    )

                    SpacerVerticalSmall()
                }
            }

            SpacerVerticalMedium()
        }
}