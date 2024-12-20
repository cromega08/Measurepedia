package cromega.studio.measurepedia.ui.components.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cromega.studio.measurepedia.extensions.isNotNullOrBlank

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    textColor: Color,
    hint: String,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: KeyboardActionScope.() -> Unit
) = TextField(
    modifier = modifier,
    value = query,
    onValueChange = onQueryChange,
    placeholder = { Text(text = hint) },
    prefix = { Text(text = "  ") },
    trailingIcon = {
        if (query.isNotNullOrBlank())
            IconButton(
                onClick = { onQueryChange("") },
                enabled = true,
                content = { ClearIcon() }
            )
        else SearchIcon()
    },
    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
    keyboardActions = KeyboardActions(onSearch = onSearch),
    singleLine = true,
    maxLines = 1,
    shape = RoundedCornerShape(50.dp),
    colors =
        TextFieldDefaults
            .colors(
                focusedTextColor = textColor,
                unfocusedTextColor = textColor,
                focusedContainerColor = backgroundColor,
                unfocusedContainerColor = backgroundColor
            )
)

@Composable
fun GenericTextField(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    textColor: Color,
    hint: String = "",
    label: @Composable() (() -> Unit)? = null,
    hasError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    maxCharacters: Int = 250,
    value: String,
    onValueChange: (String) -> Unit
) =
    TextField(
        modifier = modifier.background(color = Color.Transparent),
        value = value,
        onValueChange = { userInput ->
            if (userInput.length <= maxCharacters)
                onValueChange(userInput)
        },
        placeholder = { Text(text = hint) },
        label = label,
        isError = hasError,
        keyboardOptions = keyboardOptions,
        singleLine = true,
        maxLines = 1,
        shape = RoundedCornerShape(10.dp),
        colors =
        TextFieldDefaults
            .colors(
                focusedTextColor = textColor,
                unfocusedTextColor = textColor,
                focusedContainerColor = backgroundColor,
                unfocusedContainerColor = backgroundColor
            )
    )

@Composable
fun FloatTextField(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    textColor: Color,
    value: String,
    onValueChange: (String) -> Unit
) =
    GenericTextField(
        modifier = modifier,
        backgroundColor = backgroundColor,
        textColor = textColor,
        keyboardOptions =
        KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done
        ),
        maxCharacters = 15,
        value = value,
        onValueChange = {userInput ->
            onValueChange(
                userInput.filter { it.isDigit() || it == '.' }
            )
        }
    )

@Composable
fun StringTextField(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    textColor: Color,
    hasError: Boolean = false,
    hintText: String = "",
    labelText: String = "",
    maxCharacters: Int = 100,
    value: String,
    onValueChange: (String) -> Unit
) =
    GenericTextField(
        modifier = modifier,
        backgroundColor = backgroundColor,
        textColor = textColor,
        hasError = hasError,
        hint = hintText,
        label = { if (labelText.isNotBlank()) { Text(text = labelText)} },
        keyboardOptions =
            KeyboardOptions(
                imeAction = ImeAction.Done
            ),
        maxCharacters = maxCharacters,
        value = value,
        onValueChange = onValueChange
    )
