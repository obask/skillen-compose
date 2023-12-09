import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }
    MaterialTheme(darkColors()) {
        Row(Modifier.fillMaxSize()) {
            Column(Modifier.fillMaxHeight().width(150.dp).background(Color.Gray)) {
                Button(onClick = {}, modifier = Modifier.height(50.dp).padding(2.dp)) {
                    Text("Section 1")
                }
                Button(onClick = {}, modifier = Modifier.height(50.dp).padding(2.dp)) {
                    Text("Section 2")
                }
                Button(onClick = {}, modifier = Modifier.height(50.dp).padding(2.dp)) {
                    Text("Section 3")
                }
                Button(onClick = {}, modifier = Modifier.height(50.dp).padding(2.dp)) {
                    Text("Section 4")
                }
            }
            Column(Modifier.fillMaxHeight()) {
                Button(onClick = {
                    text = "Hello, Desktop!"
                }) {
                    Text(text)
                }
            }
        }

    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
