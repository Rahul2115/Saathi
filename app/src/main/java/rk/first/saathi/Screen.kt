package rk.first.saathi

sealed class Screen(val route:String){
    object MainScreen: Screen("mainScreen")
    object Home: Screen("home")
}