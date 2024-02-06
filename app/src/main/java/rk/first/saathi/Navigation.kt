package rk.first.saathi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import rk.first.saathi.ui.presentation.DESC
import rk.first.saathi.ui.presentation.Home
import rk.first.saathi.ui.presentation.LLM
import rk.first.saathi.ui.presentation.Login
import rk.first.saathi.ui.presentation.MainScreen
import rk.first.saathi.ui.presentation.Ocr
import rk.first.saathi.ui.presentation.SaathiViewModel


@Composable
fun Navigation(viewModel: SaathiViewModel) {
    val navController = rememberNavController()

    val uiState by viewModel.state.collectAsState()
    val logState by viewModel.loginState.collectAsState()

    NavHost(navController = navController, startDestination = Screen.MainScreen.route ){
        composable(route = Screen.MainScreen.route) {
                MainScreen(navController=navController)
        }
        composable(route = Screen.Login.route) {
                Login(navController=navController,viewModel,logState)
        }
        composable(route = Screen.Home.route){
                Home(navController=navController,viewModel)
        }
        composable(route = Screen.Ocr.route){
                Ocr(navController=navController,uiState,viewModel)
        }
        composable(route = Screen.LLM.route){
                LLM(navController=navController,uiState,viewModel)
        }
        composable(route = Screen.DESC.route){
                DESC(navController=navController,uiState,viewModel)
        }
    }
}