package cromega.studio.measurepedia.ui.activities.home

import android.content.res.Resources
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cromega.studio.measurepedia.R
import cromega.studio.measurepedia.data.models.instances.Person
import cromega.studio.measurepedia.enums.DateOrder
import cromega.studio.measurepedia.enums.MeasuredOrder
import cromega.studio.measurepedia.extensions.atLeastOneIs
import cromega.studio.measurepedia.ui.activities.generic.ActivityScreen
import cromega.studio.measurepedia.ui.components.elements.AddIcon
import cromega.studio.measurepedia.ui.components.elements.EditIcon
import cromega.studio.measurepedia.ui.components.elements.FaceIcon
import cromega.studio.measurepedia.ui.components.elements.KebabMenuIcon
import cromega.studio.measurepedia.ui.components.elements.RoundedCornerButton
import cromega.studio.measurepedia.ui.components.elements.SearchBar
import cromega.studio.measurepedia.ui.components.elements.SettingsIcon
import cromega.studio.measurepedia.ui.components.elements.SpacerHorizontalLine
import cromega.studio.measurepedia.ui.components.elements.SpacerVerticalMedium
import cromega.studio.measurepedia.ui.components.elements.SpacerVerticalSmall
import cromega.studio.measurepedia.ui.components.elements.StringTextField
import cromega.studio.measurepedia.ui.components.elements.TextLeftAligned
import cromega.studio.measurepedia.ui.components.elements.TextRightAligned
import cromega.studio.measurepedia.ui.components.elements.TextSmall
import cromega.studio.measurepedia.ui.components.elements.TextSubtitle
import cromega.studio.measurepedia.ui.components.elements.TextTitle
import cromega.studio.measurepedia.ui.components.elements.VerticalIconButton
import cromega.studio.measurepedia.ui.components.elements.WarningIcon
import cromega.studio.measurepedia.ui.components.layouts.CardConstraintLayout
import cromega.studio.measurepedia.ui.components.layouts.ColumnOrderedDialog
import cromega.studio.measurepedia.ui.components.layouts.Dropdown
import cromega.studio.measurepedia.ui.components.layouts.FinalBackgroundBox
import cromega.studio.measurepedia.ui.components.layouts.GenericBodyLazyColumn
import cromega.studio.measurepedia.ui.components.layouts.GenericFooterRow
import cromega.studio.measurepedia.ui.components.layouts.GenericHeaderColumn

class HomeScreen(
    viewModel: HomeViewModel,
    resources: Resources,
    darkTheme: Boolean
): ActivityScreen<HomeViewModel>(
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
                when
                {
                    viewModel.hasSelectedPerson -> PersonOptionsDialog()
                    viewModel.hasEditingPerson -> PersonEditorDialog()
                    viewModel.hasDeletingPerson -> PersonDeleterDialog()
                }
            },
            bottomBar = { Footer() },
            floatingActionButton = { FAB() },
            floatingActionButtonPosition = FabPosition.Center
        )

    @Composable
    override fun Header() =
        GenericHeaderColumn(
            modifier =
            Modifier.background(
                color = contrastColor,
                shape = RectangleShape
            )
        ) {
            val focusManager: FocusManager = LocalFocusManager.current

            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = mainColor,
                textColor = contrastColor,
                hint = resources.getString(R.string.search),
                query = viewModel.searchText,
                onQueryChange = { viewModel.searchText = it },
                onSearch = { focusManager.clearFocus() }
            )

            SpacerVerticalSmall()

            Row(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .background(color = secondaryColor, shape = RoundedCornerShape(5.dp))
                    .padding(start = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextSmall(text = resources.getString(R.string.order_by))

                Dropdown(
                    expanded = viewModel.dateFilterExpanded,
                    backgroundColor = tertiaryColor,
                    textColor = mainColor,
                    option = viewModel.dateOrderOption,
                    options = DateOrder.asArray(),
                    extractOptionName = { resources.getString(it.textStringId) },
                    onOptionSelected = {
                        viewModel.dateOrderOption = it
                        viewModel.dateFilterExpanded = false
                        viewModel.requestUpdateOfPersons()
                                       },
                    onClickMenu = { viewModel.invertDateFilterExpanded() }
                )

                TextSmall(text = resources.getString(R.string.and_if))

                Dropdown(
                    expanded = viewModel.measuredFilterExpanded,
                    backgroundColor = tertiaryColor,
                    textColor = mainColor,
                    option = viewModel.measuredOrderOption,
                    options = MeasuredOrder.asArray(),
                    extractOptionName = { resources.getString(it.textStringId) },
                    onOptionSelected = {
                        viewModel.measuredOrderOption = it
                        viewModel.measuredFilterExpanded = false
                        viewModel.requestUpdateOfPersons()
                                       },
                    onClickMenu = { viewModel.invertMeasuredFilterExpanded() }
                )
            }
        }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Main(paddingValues: PaddingValues) =
        FinalBackgroundBox(
            backgroundColor = mainColor,
            oppositeColor = contrastColor
        ) {
            GenericBodyLazyColumn(
                contentPadding = paddingValues
            ) {
                val persons: List<Person> = viewModel.persons

                items(persons.size)
                {
                    val person: Person = persons[it]
                    val searchValidations: BooleanArray =
                        booleanArrayOf(
                            viewModel.searchText.lowercase().isBlank(),
                            person.searchableIdentifier.contains(viewModel.searchText.lowercase().trim())
                        )

                    if (searchValidations atLeastOneIs true)
                    {
                        CardConstraintLayout(
                            modifier =
                            Modifier
                                .fillMaxWidth()
                                .combinedClickable(
                                    onLongClick = { viewModel.setSelectedPersonOnly(person = person) },
                                    onDoubleClick = { viewModel.setSelectedPersonOnly(person = person) },
                                    onClick = { viewModel.openMeasuresActivity(person = person) }
                                ),
                            backgroundColor = secondaryColor
                        ) {
                            val (nameRef, aliasRef, optionsRef, measuredRef, updateRef, middleSpaceRef) = createRefs()

                            TextTitle(
                                modifier = Modifier
                                    .fillMaxWidth(0.85f)
                                    .constrainAs(nameRef) {
                                        top.linkTo(parent.top)
                                        start.linkTo(parent.start)
                                    },
                                text = person.name
                            )

                            TextSubtitle(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .constrainAs(aliasRef) {
                                        top.linkTo(nameRef.bottom)
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)
                                    },
                                text = person.alias
                            )

                            IconButton(
                                modifier =
                                Modifier
                                    .constrainAs(optionsRef) {
                                        top.linkTo(parent.top)
                                        bottom.linkTo(aliasRef.top)
                                        start.linkTo(nameRef.end)
                                        end.linkTo(parent.end)
                                    },
                                onClick = { viewModel.setSelectedPersonOnly(person = person) },
                                content = { KebabMenuIcon() }
                            )

                            SpacerVerticalSmall(
                                modifier =
                                Modifier
                                    .constrainAs(middleSpaceRef) {
                                        top.linkTo(aliasRef.bottom)
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)
                                    }
                            )

                            TextLeftAligned(
                                modifier = Modifier
                                    .constrainAs(updateRef) {
                                        top.linkTo(middleSpaceRef.bottom)
                                        bottom.linkTo(parent.bottom)
                                        start.linkTo(parent.start)
                                    },
                                text = person.updatedAsString
                            )

                            TextRightAligned(
                                modifier = Modifier
                                    .constrainAs(measuredRef) {
                                        top.linkTo(middleSpaceRef.bottom)
                                        bottom.linkTo(parent.bottom)
                                        end.linkTo(parent.end)
                                    },
                                text =
                                    if (person.measured) resources.getString(R.string.measured)
                                    else resources.getString(R.string.not_measured)
                            )
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
            Row(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .background(color = secondaryColor, shape = RoundedCornerShape(10.dp)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                VerticalIconButton(
                    modifier =
                    Modifier
                        .fillMaxHeight()
                        .weight(0.3f),
                    onClick = { viewModel.openFieldsActivity() }
                ) {
                    EditIcon()
                    Text(text = resources.getString(R.string.fields))
                }

                Spacer(modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.3f))

                VerticalIconButton(
                    modifier =
                    Modifier
                        .fillMaxHeight()
                        .weight(0.3f),
                    onClick = { viewModel.openSettingsActivity() }
                ) {
                    SettingsIcon()
                    Text(text = resources.getString(R.string.settings))
                }
            }
        }

    @Composable
    private fun FAB() =
        FloatingActionButton(
            modifier =
            Modifier
                .offset(y = 90.dp)
                .scale(1.5f),
            containerColor = tertiaryColor,
            contentColor = secondaryColor,
            elevation = FloatingActionButtonDefaults.elevation(0.dp),
            onClick = { viewModel.openEditorDialogWithEmptyPerson() },
            content = { AddIcon() }
        )

    @Composable
    private fun PersonOptionsDialog() =
        ColumnOrderedDialog(
            columnModifier = Modifier.fillMaxWidth(),
            backgroundColor = secondaryColor,
            onDismissRequest = { viewModel.selectedPerson = null }
        ) {
            val person: Person = viewModel.selectedPerson!!

            SpacerVerticalMedium()

            TextTitle(
                modifier = Modifier.fillMaxWidth(0.7f),
                textAlign = TextAlign.Center,
                text = person.name,
                textColor = contrastColor
            )

            if (person.hasAlias)
                TextSubtitle(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    textAlign = TextAlign.Center,
                    text = person.alias,
                    textColor = contrastColor
                )

            SpacerVerticalSmall()
            SpacerHorizontalLine(modifier = Modifier.fillMaxWidth(0.7f))
            SpacerVerticalSmall()

            RoundedCornerButton(
                modifier = Modifier
                    .scale(1.25f)
                    .fillMaxWidth(0.7f),
                buttonColors =
                    ButtonColors(
                        containerColor = tertiaryColor,
                        contentColor = mainColor,
                        disabledContentColor = tertiaryColor,
                        disabledContainerColor = mainColor
                    ),
                onClick = { viewModel.setEditingPersonOnly(person = person) }
            ) {
                Text(text = resources.getString(R.string.update_person_info))
            }

            SpacerVerticalSmall()

            RoundedCornerButton(
                modifier = Modifier
                    .scale(1.25f)
                    .fillMaxWidth(0.7f),
                buttonColors =
                ButtonColors(
                    containerColor = tertiaryColor,
                    contentColor = mainColor,
                    disabledContentColor = tertiaryColor,
                    disabledContainerColor = mainColor
                ),
                onClick = { viewModel.openMeasuresActivity(person = person) }
            ) {
                Text(text = resources.getString(R.string.take_measures))
            }

            SpacerVerticalSmall()

            RoundedCornerButton(
                modifier = Modifier
                    .scale(1.25f)
                    .fillMaxWidth(0.7f),
                buttonColors =
                ButtonColors(
                    containerColor = tertiaryColor,
                    contentColor = mainColor,
                    disabledContentColor = tertiaryColor,
                    disabledContainerColor = mainColor
                ),
                onClick = { viewModel.sharePersonInfo(person = person) }
            ) {
                Text(text = resources.getString(R.string.share))
            }

            SpacerVerticalSmall()

            RoundedCornerButton(
                modifier = Modifier
                    .scale(1.25f)
                    .fillMaxWidth(0.7f),
                buttonColors =
                ButtonColors(
                    containerColor = tertiaryColor,
                    contentColor = mainColor,
                    disabledContentColor = tertiaryColor,
                    disabledContainerColor = mainColor
                ),
                onClick = { viewModel.setDeletingPersonOnly(person = person) }
            ) {
                Text(text = resources.getString(R.string.delete_person))
            }

            SpacerVerticalMedium()
        }

    @Composable
    private fun PersonEditorDialog() =
        ColumnOrderedDialog(
            columnModifier = Modifier.fillMaxWidth(),
            backgroundColor = secondaryColor,
            onDismissRequest = { viewModel.editingPerson = null }
        ) {
            val person: Person = viewModel.editingPerson!!
            val missingName: Boolean = viewModel.editingPersonMissingName

            SpacerVerticalMedium()

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) { FaceIcon() }

            SpacerVerticalSmall()

            StringTextField(
                modifier = Modifier.fillMaxWidth(0.8f),
                backgroundColor = mainColor,
                textColor = contrastColor,
                hasError = missingName,
                labelText = resources.getString(R.string.person_name),
                value = person.name,
                onValueChange = { userInput -> viewModel.updateEditingPersonName(newName = userInput) },
            )

            SpacerVerticalSmall()

            StringTextField(
                modifier = Modifier.fillMaxWidth(0.8f),
                backgroundColor = mainColor,
                textColor = contrastColor,
                labelText = resources.getString(R.string.person_alias_optional),
                value = person.alias,
                onValueChange = { userInput -> viewModel.updateEditingPersonAlias(newAlias = userInput) },
            )

            SpacerVerticalSmall()

            val stringId: Int =
                if(person.id == 0) R.string.create_person
                else R.string.update_person

            RoundedCornerButton(
                modifier = Modifier.fillMaxWidth(0.8f),
                buttonColors = ButtonColors(
                    containerColor = tertiaryColor,
                    contentColor = mainColor,
                    disabledContentColor = tertiaryColor,
                    disabledContainerColor = mainColor
                ),
                onClick = {
                    if (viewModel.editingPerson!!.name.isBlank())
                        viewModel.editingPersonMissingName = true
                    else
                        viewModel.finishUpdateEditingPerson()
                }
            ) {
                Column {
                    SpacerVerticalSmall()

                    Text(
                        modifier = Modifier.scale(1.5f),
                        text = resources.getString(stringId)
                    )

                    SpacerVerticalSmall()
                }
            }

            SpacerVerticalMedium()
        }

    @Composable
    private fun PersonDeleterDialog() =
        AlertDialog(
            containerColor = secondaryColor,
            iconContentColor = contrastColor,
            textContentColor = contrastColor,
            onDismissRequest = { viewModel.deletingPerson = null },
            confirmButton = {
                RoundedCornerButton(
                    modifier = Modifier.fillMaxWidth(0.48f),
                    buttonColors = ButtonColors(
                        containerColor = tertiaryColor,
                        contentColor = mainColor,
                        disabledContentColor = tertiaryColor,
                        disabledContainerColor = mainColor
                    ),
                    onClick = { viewModel.finishDeleteDeletingPerson() }
                ) {
                    Column {
                        SpacerVerticalSmall()

                        Text(
                            modifier = Modifier.scale(1.5f),
                            text = resources.getString(R.string.confirm)
                        )

                        SpacerVerticalSmall()
                    }
                }
            },
            dismissButton = {
                RoundedCornerButton(
                    modifier = Modifier.fillMaxWidth(0.48f),
                    buttonColors = ButtonColors(
                        containerColor = tertiaryColor,
                        contentColor = mainColor,
                        disabledContentColor = tertiaryColor,
                        disabledContainerColor = mainColor
                    ),
                    onClick = { viewModel.setSelectedPersonOnly(person = viewModel.deletingPerson!!) }
                ) {
                    Column {
                        SpacerVerticalSmall()

                        Text(
                            modifier = Modifier.scale(1.5f),
                            text = resources.getString(R.string.cancel)
                        )

                        SpacerVerticalSmall()
                    }
                }
            },
            icon = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) { WarningIcon() }
            },
            text = {
                TextSubtitle(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = resources.getString(R.string.confirm_delete_person_text)
                )
            }
        )
}