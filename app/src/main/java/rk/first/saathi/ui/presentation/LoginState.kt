package rk.first.saathi.ui.presentation

data class LoginState(
    var number: Long = 0L,
    var country: String = "",
    var numberConfirmed: Int = 0,
    var countryConfirmed: Int = 0,
    var otpConfirmed: Int = 0,
    var invalidInput: Int = 0,
    var otp :String = "",
    var codeSent: Int = 0,
    var loginSuccess: Int = 0
)
