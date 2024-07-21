package cromega.studio.measurepedia.ui.activities.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import cromega.studio.measurepedia.R
import cromega.studio.measurepedia.data.models.Person
import cromega.studio.measurepedia.enums.DateOrder
import cromega.studio.measurepedia.enums.MeasuredOrder
import cromega.studio.measurepedia.extensions.atLeastOneIs
import cromega.studio.measurepedia.resources.utils.ResourcesUtils
import cromega.studio.measurepedia.resources.utils.TablesUtils
import cromega.studio.measurepedia.ui.components.elements.AddIcon
import cromega.studio.measurepedia.ui.components.elements.DownloadIcon
import cromega.studio.measurepedia.ui.components.elements.FaceIcon
import cromega.studio.measurepedia.ui.components.elements.SearchBar
import cromega.studio.measurepedia.ui.components.elements.SettingsIcon
import cromega.studio.measurepedia.ui.components.elements.SpacerHorizontalSmall
import cromega.studio.measurepedia.ui.components.elements.SpacerVerticalLine
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

internal object HomeScreen
{
    @Composable
    fun Screen() =
        Scaffold(
            topBar = { Header() },
            content = { Main(it) },
            bottomBar = { Footer() },
            floatingActionButton = { FloatingActionButton(onClick = { /*TODO*/ }) { AddIcon() } },
            floatingActionButtonPosition = FabPosition.Center
        )

    @Composable
    fun Header()
    {
        GenericHeaderColumn {
            val focusManager: FocusManager = LocalFocusManager.current

            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                hint = ResourcesUtils.getString(R.string.search),
                query = HomeState.getSearchText(),
                onQueryChange = { HomeState.setSearchText(it) },
                onSearch = { focusManager.clearFocus() }
            )

            SpacerVerticalSmall()

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextSmall(text = ResourcesUtils.getString(R.string.order_by))

                SpacerHorizontalSmall()

                Dropdown(
                    expanded = HomeState.getDateFilterExpanded(),
                    option = HomeState.getDateOrderOption(),
                    options = arrayOf(DateOrder.RECENT, DateOrder.OLDEST, DateOrder.CREATION),
                    extractOptionName = { ResourcesUtils.getString(it.textStringId) },
                    onOptionSelected = { HomeState.setDateOrderOption(it); HomeState.setDateFilterExpanded(false) },
                    onClickMenu = { HomeState.invertDateFilterExpanded() }
                )

                SpacerHorizontalSmall()

                TextSmall(text = ResourcesUtils.getString(R.string.and_if))

                SpacerHorizontalSmall()

                Dropdown(
                    expanded = HomeState.getMeasuredFilterExpanded(),
                    option = HomeState.getMeasuredOrderOption(),
                    options = arrayOf(MeasuredOrder.MEASURED, MeasuredOrder.NOT_MEASURED, MeasuredOrder.MEASURED_OR_NOT),
                    extractOptionName = { ResourcesUtils.getString(it.textStringId) },
                    onOptionSelected = { HomeState.setMeasuredOrderOption(it); HomeState.setMeasuredFilterExpanded(false) },
                    onClickMenu = { HomeState.invertMeasuredFilterExpanded() }
                )
            }
        }
    }

    @Composable
    fun Main(paddingValues: PaddingValues) =
        GenericBodyLazyColumn(
            contentPadding = paddingValues
        ) {
            val persons: Array<Person> =
                when (HomeState.getDateOrderOption())
                {
                    DateOrder.RECENT -> TablesUtils.personsTable.readOrderedByUpdatedRecent()
                    DateOrder.OLDEST -> TablesUtils.personsTable.readOrderedByUpdatedOldest()
                    else -> TablesUtils.personsTable.readAll()
                }

            items(persons.size)
            {
                val person: Person = persons[it]
                val personSearch: String = person.getSearchablePersonIdentifier()
                val searchText: String = HomeState.getSearchText().lowercase()
                val searchValidations: BooleanArray =
                    booleanArrayOf(
                        searchText.isBlank(),
                        personSearch.contains(searchText.trim())
                    )
                val measuredValidation: Boolean =
                    when (HomeState.getMeasuredOrderOption())
                    {
                        MeasuredOrder.MEASURED -> person.isMeasured()
                        MeasuredOrder.NOT_MEASURED -> !person.isMeasured()
                        else -> true
                    }

                if ((searchValidations atLeastOneIs true) && measuredValidation )
                {
                    /*
                    * TODO: Required to include a long press option for the persons, with options like:
                    *  - Change Person Information
                    *  - Take measures
                    *  - Export Person Information
                    *  - Export Person Measures (Should send user to measures details view and show a bottom dialog to confirm the data sharing
                    * */

                    CardConstraintLayout(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val (nameRef, aliasRef, measuredRef, updateRef, middleSpaceRef) = createRefs()

                        TextTitle(
                            modifier = Modifier
                                .fillMaxWidth()
                                .constrainAs(nameRef) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                },
                            text = person.getName()
                        )

                        TextSubtitle(
                            modifier = Modifier
                                .fillMaxWidth()
                                .constrainAs(aliasRef) {
                                    top.linkTo(nameRef.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                },
                            text = person.getAlias()
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
                            text = person getUpdatedAsString "dd—MM—yyyy"
                        )

                        TextRightAligned(
                            modifier = Modifier
                                .constrainAs(measuredRef) {
                                    top.linkTo(middleSpaceRef.bottom)
                                    bottom.linkTo(parent.bottom)
                                    end.linkTo(parent.end)
                                },
                            text = person
                                .getMeasuredTexts(
                                    measuredText = ResourcesUtils.getString(R.string.measured),
                                    notMeasuredText = ResourcesUtils.getString(R.string.not_measured)
                                )
                        )
                    }
                }
            }
        }

    @Composable
    fun Footer()
    {
        /*
        * TODO: Include functionalities for different Buttons
        * */
        GenericFooterRow {
           Row(
               modifier =
               Modifier
                   .fillMaxWidth()
                   .border(color = Color.Black, width = 2.5.dp, shape = RoundedCornerShape(10.dp))
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
                   Text(text = ResourcesUtils.getString(R.string.fields))
               }

               SpacerVerticalLine(modifier = Modifier.fillMaxHeight())

               VerticalIconButton(
                   modifier =
                   Modifier
                       .fillMaxHeight()
                       .weight(1f),
                   onClick = { /*TODO*/ }
               ) {
                   DownloadIcon()
                   Text(text = ResourcesUtils.getString(R.string.import_data))
               }

               SpacerVerticalLine(modifier = Modifier.fillMaxHeight())

               VerticalIconButton(
                   modifier =
                   Modifier
                       .fillMaxHeight()
                       .weight(1f),
                   onClick = { /*TODO*/ }
               ) {
                   UploadIcon()
                   Text(text = ResourcesUtils.getString(R.string.export))
               }

               SpacerVerticalLine(modifier = Modifier.fillMaxHeight())

               VerticalIconButton(
                   modifier =
                   Modifier
                       .fillMaxHeight()
                       .weight(1f),
                   onClick = { /*TODO*/ }
               ) {
                   SettingsIcon()
                   Text(text = ResourcesUtils.getString(R.string.settings))
               }
           }
        }
    }
}