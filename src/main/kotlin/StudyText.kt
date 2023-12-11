import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.sp

@Composable
fun StudyTextArea(caption: String, textContent: String) {
    var showDialog by remember { mutableStateOf(false) }
    var text1 by remember { mutableStateOf("TEXT:$caption..............................") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            title = {
                Text("My Dialog")
            },
            text = {
                Text("This is a modal window, which contains some very useful information")
            },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Another Close")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Close")
                }
            }
        )
    }

    val text2 = tokenizeFrenchText(textContent)

    var pos = 0
    val text3: List<Pair<SomeThing, Pair<Int, String?>>> = text2.map { tokenOrText ->
        when (tokenOrText) {
            is RawText -> {
                pos += tokenOrText.value.length
                tokenOrText to (pos to "")
            }

            is Token -> {
                val translation = dictionary[tokenOrText.token]?.takeIf { it.second > 42 }?.let { " (${it.first})" }
                pos += tokenOrText.original.length + (translation?.length ?: 0)
                tokenOrText to (pos to translation)
            }
        }
    }

    val annotatedString = buildAnnotatedString {
        pushStyle(SpanStyle(fontSize = 20.sp))
        for ((t, info) in text3) {
            when (t) {
                is RawText -> append(t.value)
                is Token -> {
                    if (info.second?.isNotBlank() == true) {
                        append(t.original)
                        pushStyle(SpanStyle(color = Color(0, 100, 0)))
                        append(info.second)
                        pop()
                    } else {
                        append(t.original)
                    }
                }
            }
        }
        addStyle(SpanStyle(color = Color.Red), this.length - 10, this.length)

        toAnnotatedString()
    }
    Text("*(I don't have a good dictionary, so it shows a token you've clicked on)")
    Button(onClick = {
        text1 = "Hello, Compose for Desktop!"
        showDialog = true
    },
        Modifier.fillMaxWidth()
    ) {
        Text(text1)
    }
    Divider(Modifier)
    ClickableText(annotatedString) { p ->
        val selectedWord = text3.dropWhile { it.second.first < p }.first()
//        val tr = dictionary[(selectedWord as? Token)?.token]?.first
//        if (tr != "pitch") {
            text1 = "TRANSLATION: ${selectedWord.first}"
//        }
//        showDialog = true
    }
    Divider()
}
