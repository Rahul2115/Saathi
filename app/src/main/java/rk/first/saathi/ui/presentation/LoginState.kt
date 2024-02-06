package rk.first.saathi.ui.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class LoginState(
    var name: String = "",
    var number: Int = 0,
    var country: String = "",
    var yearOB : Int = 0,
    var monthOB : Int = 0,
    var dateOB : Int = 0,
    var nameConfirmed: Int = 0,
    var numberConfirmed: Int = 0,
    var countryConfirmed: Int = 0,
    var yearConfirm: Int = 0,
    var monthConfirm: Int = 0,
    var dateConfirm: Int = 0
)
