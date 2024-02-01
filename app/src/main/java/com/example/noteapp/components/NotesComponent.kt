package com.example.noteapp.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NotesInputText(
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit,
    text: String,
    label: String,
    maxLine: Int = 1,
    isSingleLine: Boolean,
    onImeAction: () -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = text,
        onValueChange = onTextChange,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp),
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        singleLine = isSingleLine,
        maxLines = maxLine,
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
            keyboardController?.hide()
        })
    )
}

@Composable
fun NotesButton(
    modifier: Modifier = Modifier,
    onClicked: () -> Unit = {},
    text: String,
    isEnabled: Boolean,
) {
    Button(
        onClick = { onClicked.invoke() },
        shape = CircleShape,
        enabled = isEnabled,
        modifier = modifier
            .padding(top = 12.dp, start = 32.dp, end = 32.dp)
            .width(150.dp)
            .height(50.dp),
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontWeight = FontWeight.ExtraBold,
                color = Color.Yellow,
                fontSize = 16.sp
            )
        )
    }
}