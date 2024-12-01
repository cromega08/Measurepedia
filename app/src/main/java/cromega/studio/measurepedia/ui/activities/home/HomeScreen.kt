package cromega.studio.measurepedia.ui.activities.home

import android.content.res.Resources
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
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
import cromega.studio.measurepedia.ui.components.elements.ColumnOrderedDialog
import cromega.studio.measurepedia.ui.components.elements.DownloadIcon
import cromega.studio.measurepedia.ui.components.elements.FaceIcon
import cromega.studio.measurepedia.ui.components.elements.KebabMenuIcon
import cromega.studio.measurepedia.ui.components.elements.RoundedCornerButton
import cromega.studio.measurepedia.ui.components.elements.SearchBar
import cromega.studio.measurepedia.ui.components.elements.SettingsIcon
import cromega.studio.measurepedia.ui.components.elements.SpacerHorizontalLine
import cromega.studio.measurepedia.ui.components.elements.SpacerHorizontalSmall
import cromega.studio.measurepedia.ui.components.elements.SpacerVerticalMedium
import cromega.studio.measurepedia.ui.components.elements.SpacerVerticalSmall
import cromega.studio.measurepedia.ui.components.elements.TextLeftAligned
import cromega.studio.measurepedia.ui.components.elements.TextRightAligned
import cromega.studio.measurepedia.ui.components.elements.TextSmall
import cromega.studio.measurepedia.ui.components.elements.TextSubtitle
import cromega.studio.measurepedia.ui.components.elements.TextTitle
import cromega.studio.measurepedia.ui.components.elements.UploadIcon
import cromega.studio.measurepedia.ui.components.elements.VerticalIconButton
import cromega.studio.measurepedia.ui.components.layouts.CardConstraintLayout
import cromega.studio.measurepedia.ui.components.layouts.Dropdown
import cromega.studio.measurepedia.ui.components.layouts.GenericBodyLazyColumn
import cromega.studio.measurepedia.ui.components.layouts.GenericFooterRow
import cromega.studio.measurepedia.ui.components.layouts.GenericHeaderColumn

class HomeScreen(
    viewModel: HomeViewModel,
    resources: Resources
): ActivityScreen<HomeViewModel>(
    viewModel = viewModel,
    resources = resources
) {
    @Composable
    override fun Screen() =
        Scaffold(
            topBar = { Header() },
            content = {
                Main(it)
                if (
                    viewModel.optionsDialogOpened &&
                    viewModel.hasSelectedPerson
                ) PersonsOptionsDialog()
            },
            bottomBar = { Footer() },
            floatingActionButton = { FAB() },
            floatingActionButtonPosition = FabPosition.Center
        )

    @Composable
    override fun Header() =
        GenericHeaderColumn {
            val focusManager: FocusManager = LocalFocusManager.current

            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                hint = resources.getString(R.string.search),
                query = viewModel.searchText,
                onQueryChange = { viewModel.searchText = it },
                onSearch = { focusManager.clearFocus() }
            )

            SpacerVerticalSmall()

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextSmall(text = resources.getString(R.string.order_by))

                SpacerHorizontalSmall()

                Dropdown(
                    expanded = viewModel.dateFilterExpanded,
                    option = viewModel.dateOrderOption,
                    options = DateOrder.asArray(),
                    extractOptionName = { resources.getString(it.textStringId) },
                    onOptionSelected = { viewModel.dateOrderOption = it; viewModel.dateFilterExpanded = false },
                    onClickMenu = { viewModel.invertDateFilterExpanded() }
                )

                SpacerHorizontalSmall()

                TextSmall(text = resources.getString(R.string.and_if))

                SpacerHorizontalSmall()

                Dropdown(
                    expanded = viewModel.measuredFilterExpanded,
                    option = viewModel.measuredOrderOption,
                    options = MeasuredOrder.asArray(),
                    extractOptionName = { resources.getString(it.textStringId) },
                    onOptionSelected = { viewModel.measuredOrderOption = it ; viewModel.measuredFilterExpanded = false
                                       },
                    onClickMenu = { viewModel.invertMeasuredFilterExpanded() }
                )
            }
        }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Main(paddingValues: PaddingValues) =
        GenericBodyLazyColumn(
            contentPadding = paddingValues
        ) {
            val persons: Array<Person> = viewModel.readByDateAndMeasured()

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
                    /*
                    * TODO: Include "Missing Functionalities" related to other Activities
                    * */
                    CardConstraintLayout(
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .combinedClickable(
                                onLongClick = { viewModel.selectedPerson = person ; viewModel.optionsDialogOpened = true },
                                onDoubleClick = { viewModel.selectedPerson = person ; viewModel.optionsDialogOpened = true },
                                onClick = { viewModel.selectedPerson = person ; viewModel.openMeasuresActivity() }
                            )
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
                            onClick = { viewModel.selectedPerson = person; viewModel.optionsDialogOpened = true },
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

    @Composable
    override fun Footer() =
        /*
        * TODO: Include functionalities for different Buttons
        * */
        GenericFooterRow {
            Row(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .background(color = Color.LightGray, shape = RoundedCornerShape(10.dp)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                VerticalIconButton(
                    modifier =
                    Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    onClick = { /*TODO*/ }
                ) {
                    FaceIcon()
                    Text(text = resources.getString(R.string.fields))
                }

                VerticalIconButton(
                    modifier =
                    Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    onClick = { /*TODO*/ }
                ) {
                    DownloadIcon()
                    Text(text = resources.getString(R.string.import_data))
                }

                VerticalIconButton(
                    modifier =
                    Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    onClick = { /*TODO*/ }
                ) {
                    UploadIcon()
                    Text(text = resources.getString(R.string.export))
                }

                VerticalIconButton(
                    modifier =
                    Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    onClick = { /*TODO*/ }
                ) {
                    SettingsIcon()
                    Text(text = resources.getString(R.string.settings))
                }
            }
        }

    @Composable
    private fun FAB() = FloatingActionButton(onClick = { /*TODO*/ }) { AddIcon() }

    @Composable
    private fun PersonsOptionsDialog() =
        /*
         * TODO: Include functionalities for buttons
         * */
        ColumnOrderedDialog(
            columnModifier = Modifier.fillMaxWidth(),
            onDismissRequest = { viewModel.selectedPerson = null ; viewModel.optionsDialogOpened = false }
        ) {
            val selectedPerson: Person = viewModel.selectedPerson!!

            SpacerVerticalMedium()

            TextTitle(
                modifier = Modifier.fillMaxWidth(0.7f),
                textAlign = TextAlign.Center,
                text = selectedPerson.name
            )

            if (selectedPerson.hasAlias)
                TextSubtitle(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    textAlign = TextAlign.Center,
                    text = selectedPerson.alias
                )

            SpacerVerticalSmall()
            SpacerHorizontalLine(modifier = Modifier.fillMaxWidth(0.7f))
            SpacerVerticalSmall()

            RoundedCornerButton(
                modifier = Modifier
                    .scale(1.25f)
                    .fillMaxWidth(0.7f),
                onClick = { /*TODO*/ }
            ) {
                Text(text = resources.getString(R.string.update_person_info))
            }

            SpacerVerticalSmall()

            RoundedCornerButton(
                modifier = Modifier
                    .scale(1.25f)
                    .fillMaxWidth(0.7f),
                onClick = { viewModel.openMeasuresActivity() }
            ) {
                Text(text = resources.getString(R.string.take_measures))
            }

            SpacerVerticalSmall()

            RoundedCornerButton(
                modifier = Modifier
                    .scale(1.25f)
                    .fillMaxWidth(0.7f),
                onClick = { /*TODO*/ }
            ) {
                Text(text = resources.getString(R.string.export_person_info))
            }

            SpacerVerticalSmall()

            RoundedCornerButton(
                modifier = Modifier
                    .scale(1.25f)
                    .fillMaxWidth(0.7f),
                onClick = { /*TODO*/ }
            ) {
                Text(text = resources.getString(R.string.import_person_info))
            }

            SpacerVerticalMedium()
        }
}