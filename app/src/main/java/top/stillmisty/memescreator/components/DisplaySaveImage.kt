package top.stillmisty.memescreator.components

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun DisplaySaveImage(bitmap: Bitmap, modifier: Modifier = Modifier) {
    val showDialog = remember { mutableStateOf(false) }
    val imageBitmap = remember {
        bitmap.asImageBitmap()
    }
    Image(
        bitmap = imageBitmap,
        contentDescription = "Output Image",
        modifier = modifier.clickable {
            showDialog.value = true
        }
    )

    if (showDialog.value) {
        Dialog(onDismissRequest = { showDialog.value = false }) {
            val context = LocalContext.current
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    bitmap = imageBitmap,
                    contentDescription = "Output Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .align(androidx.compose.ui.Alignment.Center),
                )
                Row(
                    modifier = Modifier
                        .align(androidx.compose.ui.Alignment.BottomEnd)
                ) {
                    ElevatedButton(
                        modifier = Modifier.padding(8.dp),
                        onClick = {
                            showDialog.value = false
                        }) {
                        Text(text = "取消")
                    }
                    ElevatedButton(
                        modifier = Modifier.padding(8.dp),
                        onClick = {
                            saveImageToGallery(bitmap, context)
                            showDialog.value = false
                        }) {
                        Text(text = "保存")
                    }
                }
            }
        }
    }
}

fun saveImageToGallery(bitmap: Bitmap, context: Context) {
    val filename = "${System.currentTimeMillis()}.jpg"
    val resolver = context.contentResolver
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
        put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/")
    }
    val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    resolver.openOutputStream(imageUri!!)?.use { outputStream ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
    }
    Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show()
}