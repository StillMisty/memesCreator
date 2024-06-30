package top.stillmisty.memescreator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import top.stillmisty.memescreator.components.BottomNavBar
import top.stillmisty.memescreator.components.TopNavBar
import top.stillmisty.memescreator.data.model.BottomNavItems
import top.stillmisty.memescreator.data.viewModel.HomeViewModel
import top.stillmisty.memescreator.data.viewModel.MakerViewModel
import top.stillmisty.memescreator.screen.HomeScreen
import top.stillmisty.memescreator.screen.MemeMaker
import top.stillmisty.memescreator.screen.MyScreen
import top.stillmisty.memescreator.ui.theme.MemesCreatorTheme

class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by lazy { HomeViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MemesCreatorTheme {
                val navController = rememberNavController()
                val currentRoute =
                    navController.currentBackStackEntryAsState().value?.destination?.route
                var isFabVisible by remember { mutableStateOf(currentRoute != BottomNavItems.Home.route) }
                LaunchedEffect(currentRoute) {
                    isFabVisible = currentRoute in BottomNavItems.entries.map { it.route }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        if (!isFabVisible) {
                            TopNavBar(navController)
                        }
                    },
                    bottomBar = {
                        if (isFabVisible) {
                            BottomNavBar(navController, BottomNavItems.entries)
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = BottomNavItems.Home.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(BottomNavItems.Home.route) {
                            HomeScreen(navController, homeViewModel)
                        }
                        composable(BottomNavItems.My.route) {
                            MyScreen(navController)
                        }
                        composable("maker/{memeKey}") {
                            val memeKey = it.arguments?.getString("memeKey") ?: ""
                            val makerViewModel = MakerViewModel()
                            MemeMaker(navController, makerViewModel, memeKey)
                        }
                    }
                }
            }
        }
    }
}

