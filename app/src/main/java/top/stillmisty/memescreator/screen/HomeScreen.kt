package top.stillmisty.memescreator.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import top.stillmisty.memescreator.components.MakerCard
import top.stillmisty.memescreator.data.viewModel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val memeList = viewModel.uiState.collectAsState().value
    val listState = viewModel.lazyListState
    Box {
        LazyColumn(
            state = listState,
            modifier = modifier
        ) {
            items(memeList ?: emptyList()) { meme ->
                MakerCard(navController, meme)
            }
        }

        var showButton by remember { mutableStateOf(false) }
        LaunchedEffect( listState.firstVisibleItemIndex ) {
            showButton = listState.firstVisibleItemIndex > 5
        }

        if (showButton) {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(0)
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset((-8).dp, (-8).dp)
            ) {
                Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "Scroll to top")
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController(), viewModel = HomeViewModel())
}