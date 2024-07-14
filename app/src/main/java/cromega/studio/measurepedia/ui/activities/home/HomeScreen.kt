package cromega.studio.measurepedia.ui.activities.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import cromega.studio.measurepedia.data.models.Person
import cromega.studio.measurepedia.extensions.atLeastOneIs
import cromega.studio.measurepedia.resources.utils.ResourcesUtils
import cromega.studio.measurepedia.resources.utils.TablesUtils
import cromega.studio.measurepedia.ui.components.SpacerVerticalSmall
import cromega.studio.measurepedia.ui.components.TextLeftAligned
import cromega.studio.measurepedia.ui.components.TextRightAligned
import cromega.studio.measurepedia.ui.components.TextSubtitle
import cromega.studio.measurepedia.ui.components.TextTitle
import cromega.studio.measurepedia.R
import cromega.studio.measurepedia.utils.ResourcesUtils

internal object HomeScreen
{
    @Composable
    fun Screen() =
        Scaffold(
            topBar = { Header() },
            content = { Main(it) },
            bottomBar = { Footer() }
        )

    @Composable
    fun Header()
    {
        Column {
            Text(text = ResourcesUtils.getString(R.string.app_name))
        }
    }

    @Composable
    fun Main(paddingValues: PaddingValues) =
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            userScrollEnabled = true
        ) {
            val persons: Array<Person> = TablesUtils.personsTable.readOrderedByDate()

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

                if (searchValidations atLeastOneIs true)
                {
                    /*
                    * TODO: Required to include a long press option for the persons, with options like:
                    *  - Change Person Information
                    *  - Take measures
                    *  - Export Person Information
                    *  - Export Person Measures (Should send user to measures details view and show a bottom dialog to confirm the data sharing
                    * */

                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.Gray, shape = RoundedCornerShape(15.dp))
                            .padding(horizontal = 20.dp, vertical = 15.dp)
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
        Column {
            Text(text = ResourcesUtils.getString(R.string.developer_name))
        }
    }
}