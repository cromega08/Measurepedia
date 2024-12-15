package cromega.studio.measurepedia.ui.activities.settings

import android.content.res.Resources
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import cromega.studio.measurepedia.R
import cromega.studio.measurepedia.enums.Languages
import cromega.studio.measurepedia.ui.activities.generic.ActivityScreen
import cromega.studio.measurepedia.ui.components.elements.RoundedCornerButton
import cromega.studio.measurepedia.ui.components.elements.SpacerVerticalMedium
import cromega.studio.measurepedia.ui.components.elements.SpacerVerticalSmall
import cromega.studio.measurepedia.ui.components.layouts.Dropdown
import cromega.studio.measurepedia.ui.components.layouts.GenericFooterRow

class SettingsScreen(
    viewModel: SettingsViewModel,
    resources: Resources
): ActivityScreen<SettingsViewModel>(
    viewModel = viewModel,
    resources = resources
) {
    @Composable
    override fun Header() = Spacer(modifier = Modifier
        .width(width = 1.dp)
        .height(50.dp))

    @Composable
    override fun Main(paddingValues: PaddingValues) =
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SpacerVerticalMedium()

            Row(
                modifier =
                Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = resources.getString(R.string.default_language))

                Dropdown(
                    expanded = viewModel.languageDropdownExpanded,
                    option = viewModel.language,
                    options = Languages.asArray(),
                    extractOptionName = { language -> language.localeAcronym },
                    onOptionSelected = { language ->
                        viewModel.language = language
                        viewModel.languageDropdownExpanded = false
                                       },
                    onClickMenu = { viewModel.invertLanguageDropdownExpanded() }
                )
            }

            SpacerVerticalMedium()

            Row(
                modifier =
                Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = resources.getString(R.string.dark_theme))

                Switch(
                    checked = viewModel.darkTheme,
                    onCheckedChange = { viewModel.invertDarkTheme() }
                )
            }
        }

    @Composable
    override fun Footer() =
        GenericFooterRow(
            modifier =
            Modifier.background(
                color = Color.White,
                shape = RectangleShape
            )
        ) {
            RoundedCornerButton(
                modifier = Modifier.weight(0.8f),
                onClick = {
                    viewModel.updateData()
                    viewModel.openHomeActivity()
                }
            ) {
                Column {
                    SpacerVerticalSmall()

                    Text(
                        modifier = Modifier.scale(1.5f),
                        text = resources.getString(R.string.update_settings)
                    )

                    SpacerVerticalSmall()
                }
            }
        }
}