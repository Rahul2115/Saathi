package rk.first.saathi

sealed class Screen(val route:String){
    data object MainScreen: Screen("mainScreen")
    data object Login: Screen("login")
    data object Home: Screen("home")
    data object Find: Screen("find")
    data object Learn: Screen("learn")
    data object LOOK: Screen("look")
    data object Read: Screen("read")
    data object Setting: Screen("setting")
}