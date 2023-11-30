package rk.first.saathi


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import rk.first.saathi.ui.presentation.Home
import rk.first.saathi.ui.presentation.MainScreen


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route ){
        composable(route = Screen.MainScreen.route) {
                MainScreen(navController=navController)
        }
        composable(route = Screen.Home.route){
            Home()
        }
    }
}