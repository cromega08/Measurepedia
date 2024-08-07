package cromega.studio.measurepedia.ui.activities.measures

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cromega.studio.measurepedia.R
import cromega.studio.measurepedia.resources.utils.ResourcesUtils
import cromega.studio.measurepedia.ui.components.elements.RoundedCornerButton
import cromega.studio.measurepedia.ui.components.elements.SpacerVerticalSmall
import cromega.studio.measurepedia.ui.components.elements.TextSubtitle
import cromega.studio.measurepedia.ui.components.elements.TextTitle
import cromega.studio.measurepedia.data.models.instances.Person
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
        GenericHeaderColumn {
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

        }

    @Composable
    fun Footer() =
        GenericFooterRow {
            RoundedCornerButton(
                modifier = Modifier.fillMaxWidth(0.8f),
                onClick = { MeasuresState.openHomeActivity() }
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