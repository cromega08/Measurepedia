package cromega.studio.measurepedia.ui.activities.settings

import android.content.res.Resources
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cromega.studio.measurepedia.R
import cromega.studio.measurepedia.enums.Languages
import cromega.studio.measurepedia.ui.activities.generic.ActivityScreen
import cromega.studio.measurepedia.ui.components.elements.RoundedCornerButton
import cromega.studio.measurepedia.ui.components.elements.SpacerVerticalMedium
import cromega.studio.measurepedia.ui.components.elements.SpacerVerticalSmall
import cromega.studio.measurepedia.ui.components.elements.TextSubtitle
import cromega.studio.measurepedia.ui.components.elements.WarningIcon
import cromega.studio.measurepedia.ui.components.layouts.Dropdown
import cromega.studio.measurepedia.ui.components.layouts.FinalBackgroundBox
import cromega.studio.measurepedia.ui.components.layouts.GenericFooterRow

class SettingsScreen(
    viewModel: SettingsViewModel,
    resources: Resources,
    darkTheme: Boolean
): ActivityScreen<SettingsViewModel>(
    viewModel = viewModel,
    resources = resources,
    darkTheme = darkTheme
) {
    @Composable
    override fun Screen() =
        Scaffold(
            topBar = { Header() },
            content = {
                Main(paddingValues = it)

                if (viewModel.updateRecordsDialogOpen)
                    ValidateRecordsUpdateDialog()
            },
            bottomBar = { Footer() }
        )

    @Composable
    override fun Header() =
        Spacer(
            modifier =
            Modifier
                .width(width = 1.dp)
                .height(50.dp)
        )

    @Composable
    override fun Main(paddingValues: PaddingValues) =
        FinalBackgroundBox(
            backgroundColor = mainColor,
            oppositeColor = contrastColor
        ) {
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
                        backgroundColor = tertiaryColor,
                        textColor = mainColor,
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
                        checked = viewModel.darkTheme,
                        onCheckedChange = { viewModel.invertDarkTheme() }
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
                    Text(
                        modifier = Modifier.weight(1f),
                        text = resources.getString(R.string.update_all_records_unit)
                    )

                    RoundedCornerButton(
                        modifier = Modifier.weight(1f),
                        buttonColors =
                        ButtonColors(
                            containerColor = Color.Red,
                            contentColor = contrastColor,
                            disabledContentColor = Color.Red,
                            disabledContainerColor = contrastColor
                        ),
                        onClick = { viewModel.invertUpdateRecordsDialogOpen() }
                    ) {
                        Text(text = resources.getString(R.string.update_data))
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
                modifier = Modifier.weight(0.8f),
                buttonColors =
                    ButtonColors(
                        containerColor = tertiaryColor,
                        contentColor = contrastColor,
                        disabledContentColor = tertiaryColor,
                        disabledContainerColor = contrastColor
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
                        text = resources.getString(R.string.update_settings),
                        color = mainColor
                    )

                    SpacerVerticalSmall()
                }
            }
        }

    @Composable
    fun ValidateRecordsUpdateDialog() =
        AlertDialog(
            containerColor = secondaryColor,
            iconContentColor = contrastColor,
            textContentColor = contrastColor,
            onDismissRequest = { viewModel.updateRecordsDialogOpen = false },
            confirmButton = {
                RoundedCornerButton(
                    modifier = Modifier.fillMaxWidth(0.48f),
                    buttonColors = ButtonColors(
                        containerColor = tertiaryColor,
                        contentColor = mainColor,
                        disabledContentColor = tertiaryColor,
                        disabledContainerColor = mainColor
                    ),
                    onClick = {
                        viewModel.updateAllRecordsMetricSystemUnitIdToDefault()
                        viewModel.updateRecordsDialogOpen = false
                    }
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
                    onClick = { viewModel.updateRecordsDialogOpen = false }
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
                    text = resources.getString(R.string.action_replace_all_records_units)
                )
            }
        )
}