package top.stillmisty.memescreator.data.viewModel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import top.stillmisty.memescreator.data.api.Api
import top.stillmisty.memescreator.data.model.MemeInfo
import java.io.File
import java.io.InputStream
import java.io.OutputStream

class MakerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<MemeInfo?>(null)
    val uiState: MutableStateFlow<MemeInfo?> = _uiState

    val imageUris = mutableStateListOf<Uri>()
    var textInput = mutableStateListOf<String>()

    val outputImage = mutableStateOf<Bitmap?>(null)

    fun getMemeInfo(memeKey: String) {
        viewModelScope.launch {
            try {
                val memeInfo = Api.retrofitService.getMemeInfo(memeKey)
                _uiState.value = memeInfo
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun createMeme(key: String, context: Context) {
        viewModelScope.launch {
            try {
                val imageParts = imageUris.map { uri ->
                    val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                    val file = File(context.cacheDir, "tempImage")
                    val outputStream: OutputStream = file.outputStream()
                    inputStream?.copyTo(outputStream)
                    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("images", file.name, requestFile)
                }

                val texts = textInput.joinToString().toRequestBody("text/plain".toMediaTypeOrNull())

                val args =
                    "{\"user_infos\": []}".toRequestBody("application/json".toMediaTypeOrNull())

                val response = Api.retrofitService.createMeme(key, imageParts, texts, args)

                val inputStream: InputStream = response.byteStream()
                outputImage.value = Bitmap.createBitmap(BitmapFactory.decodeStream(inputStream))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}