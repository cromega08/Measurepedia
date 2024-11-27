package cromega.studio.measurepedia.ui.activities.measures

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.Dimension
import cromega.studio.measurepedia.R
import cromega.studio.measurepedia.data.models.instances.BodyPart
import cromega.studio.measurepedia.data.models.instances.Field
import cromega.studio.measurepedia.data.models.instances.Person
import cromega.studio.measurepedia.data.models.instances.Record
import cromega.studio.measurepedia.extensions.extractIds
import cromega.studio.measurepedia.extensions.findOrDefault
import cromega.studio.measurepedia.resources.utils.ResourcesUtils
import cromega.studio.measurepedia.ui.components.elements.RoundedCornerButton
import cromega.studio.measurepedia.ui.components.elements.SpacerHorizontalLine
import cromega.studio.measurepedia.ui.components.elements.SpacerVerticalSmall
import cromega.studio.measurepedia.ui.components.elements.TextSubtitle
import cromega.studio.measurepedia.ui.components.elements.TextTitle
import cromega.studio.measurepedia.ui.components.elements.VerticalArrowsIcon
import cromega.studio.measurepedia.ui.components.layouts.CardConstraintLayout
import cromega.studio.measurepedia.ui.components.layouts.Dropdown
import cromega.studio.measurepedia.ui.components.layouts.GenericBodyLazyColumn
import cromega.studio.measurepedia.ui.components.layouts.GenericFooterRow
import cromega.studio.measurepedia.ui.components.layouts.GenericHeaderColumn

internal object MeasuresScreen
{
    @Composable
    fun Screen() =
        Scaffold(
            topBar = { Header() },
            content = { Main(it) },
            bottomBar = { Footer() }
        )

    @Composable
    fun Header() =
        GenericHeaderColumn(
            modifier =
                Modifier.background(
                    color = Color.White,
                    shape = RectangleShape
                )
        ) {
            Column(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .background(color = Color.LightGray, shape = RoundedCornerShape(10.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val selectedPerson: Person = MeasuresState.selectedPerson

                SpacerVerticalSmall()

                TextTitle(text = selectedPerson.getName())

                if (selectedPerson.hasAlias())
                    TextSubtitle(text = selectedPerson.getAlias())

                SpacerVerticalSmall()
            }
        }

    @Composable
    fun Main(paddingValues: PaddingValues) =
        GenericBodyLazyColumn(
            contentPadding = paddingValues
        ) {
            val metricSystemsUnits = MeasuresState.metricSystemsUnits
            val bodyParts: Array<BodyPart> = MeasuresState.bodyParts

            items(bodyParts.size) { bodyPartIndex ->
                val bodyPart: BodyPart = bodyParts[bodyPartIndex]

                CardConstraintLayout(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val (bodyPartRef, separationRef, iconRef, fieldsRef) = createRefs()

                    Text(
                        modifier =
                        Modifier
                            .constrainAs(bodyPartRef) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                            },
                        fontWeight = FontWeight.Bold,
                        text = bodyPart.getName()
                    )

                    Box(
                        modifier =
                        Modifier
                            .constrainAs(iconRef) {
                                top.linkTo(bodyPartRef.top)
                                bottom.linkTo(bodyPartRef.bottom)
                                end.linkTo(parent.end)
                            },
                        contentAlignment = Alignment.Center,
                        content = { VerticalArrowsIcon() }
                    )

                    SpacerHorizontalLine(
                        modifier =
                        Modifier
                            .height(2.5.dp)
                            .constrainAs(separationRef) {
                                top.linkTo(bodyPartRef.top)
                                bottom.linkTo(bodyPartRef.bottom)
                                start.linkTo(anchor = bodyPartRef.end, margin = 7.5.dp)
                                end.linkTo(anchor = iconRef.start, margin = 5.dp)

                                width = Dimension.fillToConstraints
                            },
                        color = Color.White
                    )

                    Column (
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .constrainAs(fieldsRef) {
                                top.linkTo(anchor = bodyPartRef.bottom, margin = 5.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)

                                width = Dimension.fillToConstraints
                            }
                    ) {
                        val bodyPartFields: Array<Field> =
                            MeasuresState
                                .fields
                                .filter { it.bodyPartId == bodyPart.id }
                                .toTypedArray()
                        
                        val bodyPartFieldsIds: Array<Int> = bodyPartFields.extractIds()
                        
                        val records: MutableList<Record> =
                            MeasuresState
                                .records
                                .filter { bodyPartFieldsIds.contains(it.fieldId) }
                                .toMutableList()

                        bodyPartFields.forEach{ field ->

                            val currentFieldGeneralIndex: Int = MeasuresState.fields.indexOf(field)

                            val record: Record = records.find { it.fieldId == field.id }!!

                            val metricSystemUnit =
                                metricSystemsUnits
                                    .findOrDefault(
                                        predicate = { record.metricSystemUnitId == it.id },
                                        generateDefault = { metricSystemsUnits[0] }
                                    )

                            Row(
                                modifier =
                                Modifier
                                    .fillParentMaxWidth()
                                    .padding(horizontal = 10.dp, vertical = 5.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = field.getTitledName(),
                                    overflow = TextOverflow.Clip
                                )

                                Spacer(modifier = Modifier.weight(0.25f))

                                TextField(
                                    modifier =
                                    Modifier
                                        .widthIn(max = 100.dp)
                                        .weight(0.83f)
                                        .onFocusChanged {focusState ->
                                            if (!focusState.isFocused)
                                            {
                                                MeasuresState
                                                    .updateRecordMeasureByPrintable(recordId = record.id)
                                            }
                                        },
                                    value = record.measurePrintable,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                    onValueChange = {userInput ->
                                        MeasuresState
                                            .updateRecordMeasurePrintable(
                                                recordId = record.id,
                                                newMeasurePrintable = userInput
                                            )
                                    }
                                )
                                Dropdown(
                                    modifier = Modifier.weight(0.83f),
                                    expanded = MeasuresState.fieldsMeasureSelectorExpanded[currentFieldGeneralIndex],
                                    option = metricSystemUnit,
                                    options = metricSystemsUnits,
                                    extractOptionName = { selectedUnit -> selectedUnit.abbreviation },
                                    onOptionSelected = {selectedUnit ->
                                        MeasuresState
                                            .updateRecordMetricSystemUnitId(
                                                recordId = record.id,
                                                newMetricSystemUnitId = selectedUnit.id
                                            )

                                        MeasuresState.invertFieldMetricSystemUnitState(currentFieldGeneralIndex)
                                    },
                                    onClickMenu = {
                                        MeasuresState.invertFieldMetricSystemUnitState(currentFieldGeneralIndex)
                                    }
                                )

                            }
                        }
                    }
                }
            }
        }

    @Composable
    fun Footer() =
        GenericFooterRow {
            RoundedCornerButton(
                modifier =
                    Modifier
                        .fillMaxWidth(0.8f),
                onClick = {
                    MeasuresState.flushRecordsForUpdates()
                    MeasuresState.openHomeActivity()
                }
            ) {
                Column {
                    SpacerVerticalSmall()

                    Text(
                        modifier = Modifier.scale(1.5f),
                        text = ResourcesUtils.getString(R.string.update_measures)
                    )

                    SpacerVerticalSmall()
                }
            }
        }
}