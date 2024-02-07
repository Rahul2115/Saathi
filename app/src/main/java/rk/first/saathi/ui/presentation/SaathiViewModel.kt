package rk.first.saathi.ui.presentation

import android.app.Application
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.util.Locale
import javax.inject.Inject

@Suppress("DEPRECATION")
@HiltViewModel
class SaathiViewModel @Inject constructor(
    private val app : Application,
) :ViewModel() {
    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    val speechRecognizer: SpeechRecognizer by lazy {
        SpeechRecognizer.createSpeechRecognizer(
            app
        )
    }

    val model = GenerativeModel(
        // Use a model that's applicable for your use case (see "Implement basic use cases" below)
        modelName = "gemini-pro",
        // Access your API key as a Build Configuration variable (see "Set up your API key" above)
        apiKey = "AIzaSyD6mf_Sf8XwWR_PxY_WTdcHuNnOhblbc3g"
    )

    val generativeModel = GenerativeModel(
        // For text-and-images input (multimodal), use the gemini-pro-vision model
        modelName = "gemini-pro-vision",
        // Access your API key as a Build Configuration variable (see "Set up your API key" above)
        apiKey = "AIzaSyDv4swdBfusrDH1wPvSoTJ4iFSz90o1PYM"
    )

    fun generateDesc(image: Uri)
    {
        val prompt = "Describe the Scenario in the Image"

        //val bitmap = BitmapFactory.decodeFile(image.toString())

        val bitmap = MediaStore.Images.Media.getBitmap(app.contentResolver,image)

        val inputContent = content {
            image(bitmap)
            text(prompt)
        }

        viewModelScope.launch(Dispatchers.IO) {
            val response = generativeModel.generateContent(inputContent)
            print(response.text)
            Log.d("Output", response.text.toString())
        }
    }

    fun ScenerioDesc(
        controller: LifecycleCameraController
    ){
        controller.takePicture(
            ContextCompat.getMainExecutor(app),
            object : OnImageCapturedCallback(){
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)
                    Log.d("Clicked", "Image Captured ${image.imageInfo}")

                    viewModelScope.launch {
                        val image1 = image.toBitmap()

                        val outputStream = ByteArrayOutputStream()

                        val prompt = "Describe the Scenario in the Image"

                        var quality = 90 // Initial quality

                        Log.d("Image Resolution","${image1.width},${image1.height}")

                        val resizedBitmap = if (image1.width > 1500 || image1.height > 1500) {
                            val aspectRatio = image1.width.toFloat() /image1.height.toFloat()
                            val newWidth = if (image1.width > image1.height) 1500 else (1500 * aspectRatio).toInt()
                            val newHeight = if (image1.height > image1.width) 1500 else (1500 / aspectRatio).toInt()
                            Bitmap.createScaledBitmap(image1, newWidth, newHeight, false)
                        } else {
                            image1
                        }

                        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)

                        Log.d("Image Size","${outputStream.toByteArray().size}")

                        while (outputStream.toByteArray().size > 1 * 1024 *1024 && quality > 0) {
                            // Reduce quality until the image size is less than 4 MB
                            Log.d("Image Size","${outputStream.toByteArray().size}")
                            quality -= 10 // Adjust compression quality as needed
                            outputStream.reset()
                            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
                        }

                        Log.d("Image Size Final","${outputStream.toByteArray().size}")

                        Log.d("Image Resolution Final","${resizedBitmap.width},${resizedBitmap.height}")

                        val inputContent = content {
                            image(resizedBitmap)
                            text(prompt)
                        }

                        val response = generativeModel.generateContent(inputContent)
                        print(response.text)
                        Log.d("Output", response.text.toString())
                    }

//                    val image1 = image.toBitmap()
//
//                    val outputStream = ByteArrayOutputStream()
//                    var quality = 90 // Initial quality
//
//                    Log.d("Image Size","${image1.width},${image1.height}")
//
//                    val resizedBitmap = if (image1.width > 3072 || image1.height > 3072) {
//                        val aspectRatio = image1.width.toFloat() /image1.height.toFloat()
//                        val newWidth = if (image1.width > image1.height) 3072 else (3072 * aspectRatio).toInt()
//                        val newHeight = if (image1.height > image1.width) 3072 else (3072 / aspectRatio).toInt()
//                        Bitmap.createScaledBitmap(image1, newWidth, newHeight, false)
//                    } else {
//                        image1
//                    }
//
//                    resizedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
//
//                    while (outputStream.toByteArray().size > 1 * 1024 && quality > 0) {
//                        // Reduce quality until the image size is less than 4 MB
//                        Log.d("Image Size","${outputStream.toByteArray().size}")
//                        quality -= 10 // Adjust compression quality as needed
//                        outputStream.reset()
//                        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
//                    }
//
//                    Log.d("Image Size","${outputStream.toByteArray().size}")
//
//                    Log.d("Image Size","${resizedBitmap.width},${resizedBitmap.height}")
//
//                    val inputContent = content {
//                        image(resizedBitmap)
//                        text("Describe the Scenario in the Image")
//                    }
//
//
//                    viewModelScope.launch {
//                        val response = generativeModel.generateContent(inputContent)
//                        print(response.text)
//                        Log.d("Output", response.text.toString())
//                    }

                    //onPhotoTaken(image.toBitmap())
                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    Log.e("Camera","Couldn't take Photo",exception)
                }
            }
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

//    fun onNameEntered(name : String){
//        _loginState.update {
//            it.copy(name = name)
//        }
//    }

//    fun userName(): String {
//        return loginState.value.name
//    }
//    fun userCountry(): String {
//        return loginState.value.country
//    }
//    fun userNumber(): String {
//        return loginState.value.number.toString()
//    }

    fun checkState(value : Int): Boolean {
        return when (value) {
            1 -> {
                loginState.value.nameConfirmed == 0
            }

            2 -> {
                loginState.value.countryConfirmed == 0
            }

            4 -> {
                loginState.value.yearConfirm == 0
            }

            5 -> {
                loginState.value.monthConfirm == 0
            }

            6 -> {
                loginState.value.dateConfirm == 0
            }
            else -> {
                loginState.value.numberConfirmed == 0
            }
        }
    }
    fun loginListen() {
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

                bundle?.let {

                    val result = it.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)

                    result?.get(0)?.let { it1 ->
                        Log.d("Voice Input", it1)
                        Toast.makeText(app, it1, Toast.LENGTH_SHORT).show()
                        if(loginState.value.name != "" && it1.toLowerCase(Locale.getDefault()) == "confirm" && checkState(1) && checkState(4) && checkState(5) && checkState(6) && checkState(2) && checkState(3))
                        {
                            _loginState.update {
                                it.copy(nameConfirmed = 1)
                           }
                        }
                        else if (checkState(1) && checkState(4) && checkState(5) && checkState(6) && checkState(2) && checkState(3)) {
                            //Validation for name if any validation required name is in it1
                            _loginState.update {
                                it.copy(name = it1)
                            }
                        }

                        else if(loginState.value.yearOB != 0 && it1.toLowerCase(Locale.getDefault()) == "confirm" && !checkState(1) && checkState(4) && checkState(5) && checkState(6) && checkState(2) && checkState(3))
                        {
                            _loginState.update {
                                it.copy(yearConfirm = 1)
                            }
                        }
                        else if (!checkState(1) && checkState(4) && checkState(5) && checkState(6) && checkState(2) && checkState(3)) {
                            // Validation for year of Birth
                            var year = it1
                            year = year.replace(" ","")
                            Log.d("Voice Input", year)
                            _loginState.update {
                                it.copy(yearOB = year.toInt())
                            }
                        }

                        else if(loginState.value.monthOB != 0 && it1.toLowerCase(Locale.getDefault()) == "confirm" && !checkState(1) && !checkState(4) && checkState(5) && checkState(6) && checkState(2) && checkState(3))
                        {
                            _loginState.update {
                                it.copy(monthConfirm = 1)
                            }
                        }
                        else if (!checkState(1) && !checkState(4) && checkState(5) && checkState(6) && checkState(2) && checkState(3)) {
                            var month = it1
                            //Validation for month
                            month = month.replace(" ","")
                            Log.d("Voice Input", month)
                            _loginState.update {
                                it.copy(monthOB = month.toInt())
                            }
                        }

                        else if(loginState.value.dateOB != 0 && it1.toLowerCase(Locale.getDefault()) == "confirm" && !checkState(1) && !checkState(4) && !checkState(5) && checkState(6) && checkState(2) && checkState(3))
                        {
                            _loginState.update {
                                it.copy(dateConfirm = 1)
                            }
                        }
                        else if (!checkState(1) && !checkState(4) && !checkState(5) && checkState(6) && checkState(2) && checkState(3)) {
                            _loginState.update {
                                var date = it1
                                //Validation for date
                                date = date.replace(" ","")
                                Log.d("Voice Input", date)
                                it.copy(dateOB = date.toInt())
                            }
                        }

                        else if (loginState.value.country != "" && it1.toLowerCase(Locale.getDefault()) == "confirm" && !checkState(1) && !checkState(4) && !checkState(5) && !checkState(6) && checkState(2) && checkState(3)){
                            _loginState.update {
                                it.copy(countryConfirmed = 1)
                            }
                        }
                        else if (!checkState(1) && !checkState(4) && !checkState(5) && !checkState(6) && checkState(2) && checkState(3)){
                            //Validation for Country
                            var country = it1
                            _loginState.update {
                                it.copy(country = country)
                            }
                        }
                        else if (loginState.value.number != 0 && it1.toLowerCase(Locale.getDefault()) == "confirm" && !checkState(1) && !checkState(4) && !checkState(5) && !checkState(6) && !checkState(2) && checkState(3)){
                            _loginState.update {
                                it.copy(numberConfirmed = 1)
                            }
                        }
                        else if (!checkState(1) && !checkState(4) && !checkState(5) && !checkState(6) && !checkState(2) && checkState(3)){
                            var number = it1
                            //Validation for mobile number
                            number = number.replace(" ","")
                            Log.d("Voice Input", number)
                            _loginState.update {
                                it.copy(number = number.toInt())
                            }
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
}