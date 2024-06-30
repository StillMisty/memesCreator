package top.stillmisty.memescreator.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import top.stillmisty.memescreator.data.api.Api

@Composable
fun MakerCard(navController: NavController, memeKey: String, modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp).padding(8.dp),
        onClick = {
            navController.navigate("maker/${memeKey}")
        }
    ) {
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment =  androidx. compose. ui. Alignment. CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("${Api.BASE_URL}/memes/${memeKey}/preview")
                    .crossfade(true)
                    .build(),
                contentDescription = memeKey,
                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                modifier = Modifier.fillMaxHeight().fillMaxWidth(0.5f),
            )
            Text(text = memeKey, fontSize = 24.sp, modifier = Modifier.padding(24.dp,16.dp,16.dp,16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MakerCardPreview() {
    MakerCard(navController = rememberNavController(), memeKey = "忍")
}