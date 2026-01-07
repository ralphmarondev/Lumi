package com.ralphmarondev.core.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.TextFields
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun LumiTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeHolderText: String = "",
    labelText: String = "",
    supportingText: String = "",
    leadingIconImageVector: ImageVector = Icons.Outlined.TextFields,
    isError: Boolean = false,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        shape = RoundedCornerShape(20),
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.secondary
        ),
        placeholder = {
            Text(
                text = placeHolderText,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        label = {
            Text(text = labelText)
        },
        singleLine = singleLine,
        leadingIcon = {
            Icon(
                imageVector = leadingIconImageVector,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
        },
        trailingIcon = {
            AnimatedVisibility(visible = value.isNotEmpty()) {
                IconButton(
                    onClick = { onValueChange("") }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = "Clear $placeHolderText",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        },
        isError = isError,
        supportingText = {
            Text(
                text = supportingText,
                color = MaterialTheme.colorScheme.error
            )
        },
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions
    )
}