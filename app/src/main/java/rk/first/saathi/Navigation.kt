package rk.first.saathi


import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import rk.first.saathi.ui.presentation.Home
import rk.first.saathi.ui.presentation.MainScreen
import rk.first.saathi.ui.presentation.Ocr
import rk.first.saathi.ui.presentation.SaathiViewModel


@Composable
fun Navigation(viewModel: SaathiViewModel) {
    val navController = rememberNavController()

    val uiState by viewModel.state.collectAsState()

    NavHost(navController = navController, startDestination = Screen.MainScreen.route ){
        composable(route = Screen.MainScreen.route) {
                MainScreen(navController=navController)
        }
        composable(route = Screen.Home.route){
                Home(navController=navController)
        }
        composable(route = Screen.Ocr.route){
                Ocr(navController=navController,uiState,viewModel)
        }
    }
}