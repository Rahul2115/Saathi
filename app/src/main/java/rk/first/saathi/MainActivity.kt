package rk.first.saathi

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import rk.first.saathi.ui.presentation.SaathiViewModel
import rk.first.saathi.ui.presentation.State
import rk.first.saathi.ui.theme.SaathiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO),0)

        val viewModel: SaathiViewModel by viewModels()

        setContent {
            SaathiTheme {
                Navigation(viewModel)
            }
        }
    }
}

