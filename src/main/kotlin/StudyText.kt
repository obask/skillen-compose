import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString

@Composable
fun StudyTextArea(caption: String, textContent: String) {
    var showDialog by remember { mutableStateOf(false) }
    var text1 by remember { mutableStateOf(caption) }

    if (showDialog) {
        // Alert dialog is shown when showDialog is true
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            title = {
                Text("Alert Dialog")
            },
            text = {
                Text("This is an example of an alert dialog.")
            },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    val text2 = tokenizeFrenchText(textContent)

    val annotatedString = buildAnnotatedString {
        for (t in text2) {
            when (t) {
                is Text -> append(t.value)
                is Token -> {
                    pushStyle(SpanStyle(color = Color.Blue))
                    append(t.original)
                    pop()
                }
            }
        }
        addStyle(SpanStyle(color = Color.Red), this.length - 10, this.length)

        toAnnotatedString()
    }
    Button(onClick = {
        text1 = "Hello, Desktop!"
    }) {
        Text(text1)
    }
    Divider(Modifier)
    Text("BEFORE")
    ClickableText(annotatedString) { pos ->
        text1 = "CLICK POSITION: $pos"
        showDialog = true
    }
    Divider()
    Text("AFTER")
}
