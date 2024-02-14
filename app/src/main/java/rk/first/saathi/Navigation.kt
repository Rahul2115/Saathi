package rk.first.saathi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import rk.first.saathi.ui.presentation.BottomNavItem
import rk.first.saathi.ui.presentation.Home
import rk.first.saathi.ui.presentation.LOOK
import rk.first.saathi.ui.presentation.Learn
import rk.first.saathi.ui.presentation.Login
import rk.first.saathi.ui.presentation.MainScreen
import rk.first.saathi.ui.presentation.READ
import rk.first.saathi.ui.presentation.SaathiViewModel
import rk.first.saathi.ui.presentation.Setting


@Composable
fun Navigation(viewModel: SaathiViewModel) {
    val navController = rememberNavController()
    val uiState by viewModel.state.collectAsState()
    val logState by viewModel.loginState.collectAsState()

    NavHost(navController = navController, startDestination = if(viewModel.auth.currentUser!=null){Screen.Home.route}else{Screen.MainScreen.route}){
        composable(route = Screen.MainScreen.route) {
                MainScreen(navController=navController)
        }
        composable(route = Screen.Login.route) {
                Login(navController=navController,viewModel,logState)
        }
        composable(route = Screen.Home.route){
                Home(navController=navController,viewModel,uiState)
        }
        composable(route = Screen.Read.route){
                READ(navController=navController,uiState,viewModel)
        }
        composable(route = Screen.Learn.route){
                Learn(navController=navController,uiState,viewModel)
        }
        composable(route = Screen.LOOK.route){
                LOOK(navController = navController, uiState, viewModel)
        }
        composable(route = Screen.Setting.route){
                Setting(navController = navController,viewModel,uiState)
        }
    }
}