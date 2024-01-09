package rk.first.saathi

sealed class Screen(val route:String){
    data object MainScreen: Screen("mainScreen")
    data object Home: Screen("home")
    data object Ocr: Screen("ocr")
    data object LLM: Screen("llm")
}