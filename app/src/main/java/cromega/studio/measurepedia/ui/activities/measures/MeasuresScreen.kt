package cromega.studio.measurepedia.ui.activities.measures

import android.content.res.Resources
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.Dimension
import cromega.studio.measurepedia.R
import cromega.studio.measurepedia.data.models.instances.BodyPart
import cromega.studio.measurepedia.data.models.instances.Field
import cromega.studio.measurepedia.data.models.instances.Person
import cromega.studio.measurepedia.data.models.instances.Record
import cromega.studio.measurepedia.ui.activities.generic.ActivityScreen
import cromega.studio.measurepedia.ui.components.elements.FloatTextField
import cromega.studio.measurepedia.ui.components.elements.RoundedCornerButton
import cromega.studio.measurepedia.ui.components.elements.SpacerHorizontalLine
import cromega.studio.measurepedia.ui.components.elements.SpacerHorizontalSmall
import cromega.studio.measurepedia.ui.components.elements.SpacerVerticalSmall
import cromega.studio.measurepedia.ui.components.elements.TextSubtitle
import cromega.studio.measurepedia.ui.components.elements.TextTitle
import cromega.studio.measurepedia.ui.components.layouts.CardConstraintLayout
import cromega.studio.measurepedia.ui.components.layouts.Dropdown
import cromega.studio.measurepedia.ui.components.layouts.FinalBackgroundBox
import cromega.studio.measurepedia.ui.components.layouts.GenericBodyLazyColumn
import cromega.studio.measurepedia.ui.components.layouts.GenericFooterRow
import cromega.studio.measurepedia.ui.components.layouts.GenericHeaderColumn

class MeasuresScreen(
    viewModel: MeasuresViewModel,
    resources: Resources,
    darkTheme: Boolean
): ActivityScreen<MeasuresViewModel>(
    viewModel = viewModel,
    resources = resources,
    darkTheme = darkTheme
) {
    @Composable
    override fun Header() =
        GenericHeaderColumn(
            modifier =
                Modifier.background(
                    color = contrastColor,
                    shape = RectangleShape
                )
        ) {
            Column(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .background(color = contrastColor, shape = RoundedCornerShape(10.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val selectedPerson: Person = viewModel.selectedPerson

                SpacerVerticalSmall()

                TextTitle(text = selectedPerson.name, textColor = mainColor)

                if (selectedPerson.hasAlias)
                    TextSubtitle(text = selectedPerson.alias, textColor = mainColor)

                SpacerVerticalSmall()
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
            ) {
                val metricSystemsUnits = viewModel.metricSystemsUnits
                val bodyParts: List<BodyPart> = viewModel.bodyParts

                items(bodyParts.size) { bodyPartIndex ->
                    val bodyPart: BodyPart = bodyParts[bodyPartIndex]

                    if (bodyPart.active)
                    {
                        CardConstraintLayout(
                            modifier = Modifier.fillMaxWidth(),
                            backgroundColor = secondaryColor
                        ) {
                            val (bodyPartRef, separationRef, fieldsRef) = createRefs()

                            Text(
                                modifier =
                                Modifier
                                    .constrainAs(bodyPartRef) {
                                        top.linkTo(parent.top)
                                        start.linkTo(parent.start)
                                    },
                                fontWeight = FontWeight.Bold,
                                text = bodyPart.name
                            )

                            SpacerHorizontalLine(
                                modifier =
                                Modifier
                                    .height(2.5.dp)
                                    .constrainAs(separationRef) {
                                        top.linkTo(bodyPartRef.top)
                                        bottom.linkTo(bodyPartRef.bottom)
                                        start.linkTo(anchor = bodyPartRef.end, margin = 7.5.dp)
                                        end.linkTo(anchor = parent.end, margin = 7.5.dp)

                                        width = Dimension.fillToConstraints
                                    },
                                color = Color.White
                            )

                            Column (
                                modifier =
                                Modifier
                                    .constrainAs(fieldsRef) {
                                        top.linkTo(anchor = bodyPartRef.bottom, margin = 5.dp)
                                        bottom.linkTo(anchor = parent.bottom, margin = 5.dp)
                                        start.linkTo(anchor = parent.start, margin = 7.5.dp)
                                        end.linkTo(anchor = parent.end, margin = 7.5.dp)

                                        width = Dimension.fillToConstraints
                                    }
                            ) {
                                val bodyPartFields: List<Field> = viewModel.filterFieldsByBodyPartId(bodyPartId = bodyPart.id)

                                bodyPartFields.forEach{ field ->

                                    if (field.active)
                                    {
                                        val currentFieldGeneralIndex: Int = viewModel.findArrayIndexOfField(field = field)

                                        val record: Record = viewModel.findRecordByFieldId(fieldId = field.id)

                                        val metricSystemUnit =
                                            viewModel.findMetricSystemUnitById(metricSystemUnitId = record.metricSystemUnitId)

                                        Row(
                                            modifier =
                                            Modifier
                                                .fillParentMaxWidth()
                                                .padding(horizontal = 20.dp, vertical = 5.dp),
                                            horizontalArrangement = Arrangement.SpaceEvenly,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {

                                            Text(
                                                modifier = Modifier.weight(1f),
                                                text = field.name,
                                                overflow = TextOverflow.Clip
                                            )

                                            Spacer(modifier = Modifier.weight(0.1f))

                                            FloatTextField(
                                                modifier =
                                                Modifier
                                                    .widthIn(max = 100.dp)
                                                    .weight(0.8f)
                                                    .onFocusChanged { focusState ->
                                                        if (!focusState.isFocused) {
                                                            viewModel
                                                                .updateRecordMeasureByPrintable(
                                                                    recordId = record.id
                                                                )
                                                        }
                                                    },
                                                backgroundColor = mainColor,
                                                textColor = contrastColor,
                                                value = record.measurePrintable,
                                                onValueChange = {userInput ->
                                                    viewModel
                                                        .updateRecordMeasurePrintable(
                                                            recordId = record.id,
                                                            newMeasurePrintable = userInput
                                                        )
                                                }
                                            )

                                            SpacerHorizontalSmall(modifier = Modifier.weight(0.2f))

                                            Dropdown(
                                                modifier = Modifier.weight(0.6f),
                                                backgroundColor = tertiaryColor,
                                                textColor = mainColor,
                                                expanded = viewModel.metricSystemsUnitsSelectorsExpanded[currentFieldGeneralIndex],
                                                option = metricSystemUnit,
                                                options = metricSystemsUnits.toTypedArray(),
                                                extractOptionName = { selectedUnit -> selectedUnit.abbreviation },
                                                onOptionSelected = {selectedUnit ->
                                                    viewModel
                                                        .updateRecordMetricSystemUnitId(
                                                            recordId = record.id,
                                                            newMetricSystemUnitId = selectedUnit.id
                                                        )

                                                    viewModel.invertMetricSystemUnitSelector(currentFieldGeneralIndex)
                                                },
                                                onClickMenu = {
                                                    viewModel.invertMetricSystemUnitSelector(currentFieldGeneralIndex)
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
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
                        .fillMaxWidth(0.8f),
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
                        text = resources.getString(R.string.update_measures)
                    )

                    SpacerVerticalSmall()
                }
            }
        }
}