package rk.first.saathi.ui.presentation

import android.app.Activity
import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.phone.SmsRetriever.SMS_RETRIEVED_ACTION
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.auth
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import rk.first.saathi.R
import java.io.ByteArrayOutputStream
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@Suppress("DEPRECATION")
@HiltViewModel
class SaathiViewModel @Inject constructor(
    private val app : Application):ViewModel() {
    var auth: FirebaseAuth = Firebase.auth
    private lateinit var mActivity: Activity
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    lateinit private var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    val importantKeywords = mutableListOf<String>(
        "hospital",
        "department",
        "computer",
        "electronics",
        "mechanical"
    )

    private val apiKey:String = "AIzaSyAD_Dl8BTTpYNPAIsjf_21wabLc7zHrz7s"

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
        apiKey = apiKey
    )

    val generativeModel = GenerativeModel(
        // For text-and-images input (multimodal), use the gemini-pro-vision model
        modelName = "gemini-pro-vision",
        // Access your API key as a Build Configuration variable (see "Set up your API key" above)
        apiKey = apiKey
    )

    fun ScenerioDesc(
        controller: LifecycleCameraController
    ) {
        _state.update {
            it.copy(clickedState = false)
        }
        controller.takePicture(
            ContextCompat.getMainExecutor(app),
            object : OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)
//                    Log.d("Clicked", "Image Captured ${image.imageInfo}")

                    var mediaPlayer = MediaPlayer.create(app, R.raw.camera)
                    mediaPlayer.start() // no need to call prepare(); create() does that for you

                    viewModelScope.launch {
                        delay(2000L)
                        val image1 = image.toBitmap()

                        val outputStream = ByteArrayOutputStream()

                        val prompt = "Role : You are a personal assistant for a blind individual. Describe the image in such a way that you help him navigate efficiently. If the image has any kind of board like a sign board, information board or direction board, please explain it. Otherwise simply describe the image."

                        var quality = 90 // Initial quality

//                        Log.d("Image Resolution", "${image1.width},${image1.height}")

                        val resizedBitmap = if (image1.width > 1500 || image1.height > 1500) {
                            val aspectRatio = image1.width.toFloat() / image1.height.toFloat()
                            val newWidth =
                                if (image1.width > image1.height) 1500 else (1500 * aspectRatio).toInt()
                            val newHeight =
                                if (image1.height > image1.width) 1500 else (1500 / aspectRatio).toInt()
                            Bitmap.createScaledBitmap(image1, newWidth, newHeight, false)
                        } else {
                            image1
                        }

                        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)

//                        Log.d("Image Size", "${outputStream.toByteArray().size}")

                        while (outputStream.toByteArray().size > 1 * 1024 * 1024 && quality > 0) {
                            // Reduce quality until the image size is less than 4 MB
                            Log.d("Image Size", "${outputStream.toByteArray().size}")
                            quality -= 10 // Adjust compression quality as needed
                            outputStream.reset()
                            resizedBitmap.compress(
                                Bitmap.CompressFormat.JPEG,
                                quality,
                                outputStream
                            )
                        }

//                        Log.d("Image Size Final", "${outputStream.toByteArray().size}")

//                        Log.d(
//                            "Image Resolution Final",
//                            "${resizedBitmap.width},${resizedBitmap.height}"
//                        )

                        val inputContent = content {
                            image(resizedBitmap)
                            text(prompt)
                        }
                        speak("Saathi is Analyzing the Image Please be patient. ")
                        val response = generativeModel.generateContent(inputContent)
//                        print(response.text)
//                        Log.d("Output", response.text.toString())
                        speak(response.text.toString())
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    Log.e("Camera", "Couldn't take Photo", exception)
                }
            }
        )
    }

    fun updateScreen(value: String) {
        _state.update {
            it.copy(gotoScreen = value)
        }
    }

    fun homeListen() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM,
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
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
                        when (it1.trim().toLowerCase()) {
                            "home" -> {
                                // Navigate to the Home Page
                                changeScreenSpeak("home")
                                _state.update {
                                    it.copy(gotoScreen = "home")
                                }
                            }

                            "learn" -> {
                                // Navigate to the LLM Page
                                changeScreenVoiceSpeak("learn")
                                _state.update {
                                    it.copy(gotoScreen = "learn")
                                }
                            }

                            "read" -> {
                                // Navigate to the OCR Page
                                changeScreenVoiceSpeak("read")
                                _state.update {
                                    it.copy(gotoScreen = "read")
                                }
                            }

                            "look" -> {
                                // Navigate to the DESC Page
                                changeScreenVoiceSpeak("look")
                                _state.update {
                                    it.copy(gotoScreen = "look")
                                }
                            }

                            "find" -> {
                                // Navigate to the DESC Page
                                changeScreenVoiceSpeak("find")
                                _state.update {
                                    it.copy(gotoScreen = "find")
                                }
                            }

                            else -> {
                                // Handle unrecognized input
                                // Prompt the user to say it again
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

    fun learnListen() {
        if(tts.isSpeaking){
            tts.stop()
            speak("Stopped Speaking")
        }
        else {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            speechRecognizer.setRecognitionListener(object : RecognitionListener {
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
                            //Toast.makeText(app, it1, Toast.LENGTH_SHORT).show()
                            speak(it1)
                            val prompt = "$it1 "
                            viewModelScope.launch {
                                val response = model.generateContent(prompt)
                                Log.d("Output", response.text.toString())
                                while (tts.isSpeaking) {
                                }
                                speak(response.text.toString())
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

    val tts: TextToSpeech by lazy {
        TextToSpeech(app) {
            if (it != TextToSpeech.ERROR) {
                tts.language = Locale.ENGLISH
            }
        }
    }

    fun speak(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }

    fun updatePageState(route: String?) {
        if (route != null) {
            _state.update {
                it.copy(currentPage = route)
            }
        }
    }

    fun clickStateValue(value: Boolean) {
        _state.update {
            it.copy(clickedState = value)
        }
    }

    fun update(value: String) {
        _state.update {
            it.copy(
                text = value
            )
        }
    }
    fun checkState(value: Int): Boolean {
        return when (value) {
            2 -> {
                loginState.value.countryConfirmed == 0
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

        speechRecognizer.setRecognitionListener(object : RecognitionListener {
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

                        if (loginState.value.country != "" && it1.toLowerCase(Locale.getDefault()) == "confirm" && checkState(
                                2
                            ) && checkState(3)
                        ) {
                            _loginState.update {
                                it.copy(countryConfirmed = 1, invalidInput = 0)
                            }
                        } else if (checkState(2) && checkState(3)) {
                            //Validation for Country
                            val validCountries = listOf(
                                "usa",
                                "canada",
                                "uk",
                                "australia",
                                "germany",
                                "india"
                            ) // Example list of valid countries
                            if (it1.isNotBlank() && it1.toLowerCase(Locale.getDefault()) in validCountries) {
                                _loginState.update {
                                    it.copy(country = it1)
                                }
                            } else {
                                // Handle invalid country input
                                _loginState.update {
                                    it.copy(invalidInput = 1, country = "")
                                }
                                speak("Invalid Input Speak your Country again by pressing the mic button")
                            }
                        } else if (loginState.value.number != 0L && it1.toLowerCase(Locale.getDefault()) == "confirm" && !checkState(
                                2
                            ) && checkState(3)
                        ) {
                            _loginState.update {
                                it.copy(numberConfirmed = 1, invalidInput = 0, codeSent = 1)
                            }
                            otpSend(loginState.value.number)
                        } else if (loginState.value.codeSent == 0 && !checkState(2) && checkState(3)) {
                            val number = it1.replace("\\s+".toRegex(), "") // Remove whitespace
                            val validNumberPattern =
                                Regex("\\d{10}") // Example: 10 digits for a mobile number

                            if (number.matches(validNumberPattern)) {
                                _loginState.update {
                                    it.copy(number = number.toLong())
                                }
                            } else {
                                // Handle invalid mobile number input
                                _loginState.update {
                                    it.copy(invalidInput = 1, number = 0L)
                                }
                                speak("Invalid Input Speak your mobile number again by pressing the mic button")
                            }
                        } else if (loginState.value.otp != "" && it1.toLowerCase(Locale.getDefault()) == "confirm" && !checkState(
                                2
                            ) && !checkState(3)
                        ) {
                            _loginState.update {
                                it.copy(otpConfirmed = 1, invalidInput = 0)
                            }
                            val credential = PhoneAuthProvider.getCredential(
                                state.value.storedVerification,
                                loginState.value.otp
                            )
                            signInWithPhoneAuthCredential(credential)
                        } else if (loginState.value.codeSent == 1 && !checkState(2) && !checkState(3)) {
                            // Validation for OTP
                            val otp = it1.replace("\\s+".toRegex(), "")
                            val validOtpPattern = Regex("\\d{6}") // Regex pattern for 6 digits
                            if (otp.matches(validOtpPattern)) {
                                _loginState.update {
                                    it.copy(otp = otp)
                                }
                            } else {
                                // Handle invalid OTP input
                                _loginState.update {
                                    it.copy(invalidInput = 1, otp = "")
                                }
                                speak("Invalid Input Speak your otp again by pressing the mic button")
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

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Login", "signInWithCredential:success")
                    val user = task.result?.user
                    _loginState.update {
                        it.copy(loginSuccess = 1)
                    }
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w("Login", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Log.w("Login", "Invalid Code", task.exception)
                    }
                    // Update UI
                }
            }
    }

    fun setAct(act: Activity) {
        mActivity = act
    }

    fun otpSend(number: Number) {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("Failed", "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Toast.makeText(app, "Invalid request", Toast.LENGTH_LONG).show()
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Toast.makeText(
                        app,
                        "The SMS quota for the project has been exceeded",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                    // reCAPTCHA verification attempted with null Activity
                    Toast.makeText(
                        app,
                        "reCAPTCHA verification attempted with null Activity",
                        Toast.LENGTH_LONG
                    ).show()
                }

                // Show a message and update the UI
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken,
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
//                Log.d("CodeSend", "onCodeSent:$verificationId")
                _state.update {
                    it.copy(storedVerification = verificationId)
                }
                Log.d(
                    "CodeSend",
                    "${loginState.value.numberConfirmed},${loginState.value.codeSent}"
                )
                resendToken = token
            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91 ${loginState.value.number}") // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(mActivity)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun logOut() {
        Firebase.auth.signOut()
    }

    fun changeScreenSpeak(title: String) {
        if (tts.isSpeaking) {
            tts.stop()
        }
        viewModelScope.launch {
            if (title.lowercase(Locale.getDefault())
                    .equals("home") || title.lowercase(Locale.getDefault()).equals("learn")
            ) {
                delay(1000L)
                speak(title)
            } else {
                delay(3000L)
                //speak(title)
                if (state.value.currentPage.equals(title.lowercase(Locale.getDefault()))) {
                    speak(title)
                }
            }
        }
    }

    fun changeScreenVoiceSpeak(title: String) {
        if (tts.isSpeaking) {
            tts.stop()
        }
        viewModelScope.launch {
            if (title.lowercase(Locale.getDefault())
                    .equals("home") || title.lowercase(Locale.getDefault()).equals("learn")
            ) {
                delay(2500L)
                speak(title)
            } else {
                delay(3000L)
                if (state.value.currentPage.equals(title)) {
                    speak(title)
                }
            }
        }
    }

    fun addKeywords(word: String) {
        if (word != "" && word != " ") {
            importantKeywords.add(word.lowercase())
        }
    }

    fun TextRecognition(controller: LifecycleCameraController, viewModel: SaathiViewModel){
        val options = TextRecognizerOptions.DEFAULT_OPTIONS
        val recognizer = TextRecognition.getClient(options)
        var resultText = "D"

        if(tts.isSpeaking)
        {
            tts.stop()
            speak("stopped speaking")
        }
        else {
            controller.takePicture(
                ContextCompat.getMainExecutor(app),
                object : OnImageCapturedCallback() {
                    override fun onCaptureSuccess(image: ImageProxy) {
                        super.onCaptureSuccess(image)
                        Log.d("Clicked", "Image Captured ${image.imageInfo}")

                        var mediaPlayer = MediaPlayer.create(app, R.raw.camera)
                        mediaPlayer.start() // no need to call prepare(); create() does that for you

                        val inputImage = InputImage.fromBitmap(image.toBitmap(), 0)

                        recognizer.process(inputImage)
                            .addOnSuccessListener { texts ->
                                resultText = texts.text
                                //state.value.text = texts.text
                                //viewModel.update(texts.text)
                                Log.d("Text", resultText)
                                speak(resultText)
                            }
                            .addOnFailureListener { e -> // Task failed with an exception
                                e.printStackTrace()
                            }

                    }

                    override fun onError(exception: ImageCaptureException) {
                        super.onError(exception)
                        Log.e("Camera", "Couldn't take Photo", exception)
                    }
                }
            )
        }
    }
}