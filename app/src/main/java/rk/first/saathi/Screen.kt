package rk.first.saathi

sealed class Screen(val route:String){
    object MainScreen: Screen("mainScreen")
    object NameScreen: Screen("nameScreen")
}