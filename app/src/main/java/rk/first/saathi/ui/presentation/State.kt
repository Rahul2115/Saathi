package rk.first.saathi.ui.presentation

data class State(
    var text: String = "",
    var storedVerification: String = "",
    var gotoScreen : String = "home",
    var clickedState: Boolean = false,
    var currentPage: String = "",
)
