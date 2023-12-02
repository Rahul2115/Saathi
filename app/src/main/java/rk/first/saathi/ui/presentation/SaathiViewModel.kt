package rk.first.saathi.ui.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SaathiViewModel:ViewModel() {
    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    fun update(value: String){
        _state.update {
            it.copy(
                text = value
            )
        }
    }

    fun gettext():String{
        return state.value.text
    }
}