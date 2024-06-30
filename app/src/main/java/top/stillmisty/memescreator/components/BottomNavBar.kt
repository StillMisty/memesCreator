package top.stillmisty.memescreator.components

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import top.stillmisty.memescreator.data.model.BottomNavItems


@Composable
fun BottomNavBar(navController: NavController, items: List<BottomNavItems>) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route)
                },
                icon = {
                    Text(text = item.label)
                },
            )
        }
    }
}