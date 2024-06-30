package top.stillmisty.memescreator.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier


@Composable
fun MakerTextInput(
    max_texts: Int,
    modifier: Modifier = Modifier,
    textInput: SnapshotStateList<String>,
    defaultText: List<String>
) {
    while (textInput.size < max_texts) {
        textInput.add("")
    }
    Column(
        modifier = modifier
    ) {
        for (i in 0 until max_texts) {
            OutlinedTextField(
                value = textInput[i],
                onValueChange = { textInput[i] = it },
                singleLine = true,
                label = {
                    defaultText.getOrNull(i)?.let { Text(it) } ?: Text("关键字 $i")
                })
        }
    }
}

