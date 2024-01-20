package rk.first.saathi.ui.presentation

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@Suppress("DEPRECATION")
@HiltViewModel
class SaathiViewModel @Inject constructor(
    private val app : Application
) :ViewModel() {
    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    val speechRecognizer: SpeechRecognizer by lazy {
        SpeechRecognizer.createSpeechRecognizer(
            app
        )
    }

    fun startListen() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        speechRecognizer.setRecognitionListener(object: RecognitionListener {
            override fun onReadyForSpeech(p0: Bundle?) {
            }

            override fun onBeginningOfSpeech() {
            }

            override fun onRmsChanged(p0: Float) {

            }

            override fun onBufferReceived(p0: ByteArray?) {

            }

            override fun onEndOfSpeech() {

            }

            override fun onError(p0: Int) {

            }

            override fun onResults(bundle: Bundle?) {
                Log.d("Voice Input", "In result")
                bundle?.let {
                    val result = it.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    result?.get(0)?.let { it1 ->
                        Log.d("Voice Input", it1)
                        Toast.makeText(app, it1, Toast.LENGTH_SHORT).show()

                        if (it1.toLowerCase() == "stop") {
                            if(tts.isSpeaking)
                            {
                                tts.stop()
                            }
                            else
                            {

                            }

                        } else {

                            val model = GenerativeModel(
                                // Use a model that's applicable for your use case (see "Implement basic use cases" below)
                                modelName = "gemini-pro",
                                // Access your API key as a Build Configuration variable (see "Set up your API key" above)
                                apiKey = "AIzaSyD6mf_Sf8XwWR_PxY_WTdcHuNnOhblbc3g"
                            )

                            val prompt = it1 + "in short"

                            viewModelScope.launch {
                                val response = model.generateContent(prompt)
                                Log.d("Output", response.text.toString())
                                speak(response.text.toString())
                            }

                            //                        when(it1.toLowerCase(Locale.getDefault())){
                            //                            "turn on bluetooth" -> {
                            //                                if(!state.value.btState){
                            //                                    btActionChange()
                            //                                } else {
                            //
                            //                                }
                            //                            }
                            //
                            //                            else ->{
                            //                                Log.d("Voice Input", it1)
                            //                            }
                            //                        }
                        }
                    }
                }
            }

            override fun onPartialResults(p0: Bundle?) {

            }

            override fun onEvent(p0: Int, p1: Bundle?) {

            }
        })
        speechRecognizer.startListening(intent)
    }

    private val tts : TextToSpeech by lazy {
        TextToSpeech(app) {
            if (it != TextToSpeech.ERROR) {
                tts.language = Locale.UK
            }
        }
    }
    fun speak(text:String){
        tts.speak(text,TextToSpeech.QUEUE_FLUSH, null)
    }

    fun update(value: String){
        _state.update {
            it.copy(
                text = value
            )
        }
    }
}