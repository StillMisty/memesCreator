@file:OptIn(ExperimentalMaterial3Api::class)

package top.stillmisty.memescreator.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import top.stillmisty.memescreator.components.DisplaySaveImage
import top.stillmisty.memescreator.components.ImagePicker
import top.stillmisty.memescreator.components.MakerTextInput
import top.stillmisty.memescreator.data.api.Api
import top.stillmisty.memescreator.data.viewModel.MakerViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MemeMaker(
    navController: NavController,
    viewModel: MakerViewModel,
    memeKey: String,
    modifier: Modifier = Modifier
) {
    viewModel.getMemeInfo(memeKey)
    val memeInfo = viewModel.uiState.collectAsState().value
    val scroll = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp, 16.dp, 16.dp, 0.dp)
            .verticalScroll(scroll),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("${Api.BASE_URL}/memes/${memeKey}/preview")
                .crossfade(true)
                .build(),
            contentDescription = memeKey,
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .defaultMinSize(200.dp, 200.dp)
        )
        memeInfo?.let { it ->
            Text(text = it.key)
            Text(text = it.keywords.toString())
            HorizontalDivider(
                modifier = Modifier.padding(32.dp, 16.dp)
            )
            if (it.params.max_texts > 0) {
                MakerTextInput(
                    max_texts = it.params.max_texts,
                    modifier = Modifier.fillMaxWidth(),
                    textInput = viewModel.textInput,
                    defaultText = it.params.default_texts
                )
            }
            if (it.params.max_images > 0) {
                ImagePicker(
                    maxImage = it.params.max_images,
                    imageUris = viewModel.imageUris,
                    modifier = Modifier.fillMaxWidth()
                )
            }


            val context = LocalContext.current
            ElevatedButton(
                onClick = {
                    viewModel.createMeme(memeKey, context)
                },
                modifier = Modifier.align(androidx.compose.ui.Alignment.End)
            ) {
                Text(text = "生成")
            }
            viewModel.outputImage.value?.let {
                HorizontalDivider(
                    modifier = Modifier.padding(32.dp, 16.dp)
                )
                DisplaySaveImage(
                    bitmap = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(200.dp, 200.dp)
                )
                Spacer(modifier = Modifier.padding(120.dp))
            }
        }
    }
}