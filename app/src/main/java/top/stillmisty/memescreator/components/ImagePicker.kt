package top.stillmisty.memescreator.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import top.stillmisty.memescreator.data.viewModel.MakerViewModel

@Composable
fun ImagePicker(maxImage: Int, imageUris: SnapshotStateList<Uri>, modifier: Modifier = Modifier) {

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uris: Uri? ->
            uris?.let {
                if (imageUris.size >= maxImage) {
                    imageUris.removeAt(0)
                }
                imageUris.add(it)
            }
        }

    Column(
        modifier = modifier
    ) {
        ElevatedButton(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 4.dp),
            onClick = { launcher.launch("image/*") }) {
            Text(text = "选取图片")
        }
        Row {
            imageUris.forEach { imageUri ->
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = "image",
                    modifier = Modifier.padding(0.dp, 0.dp, 4.dp, 0.dp).height(160.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImagePickerPreview() {
    ImagePicker(2, MakerViewModel().imageUris)
}

