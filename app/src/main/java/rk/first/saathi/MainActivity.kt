package rk.first.saathi

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import dagger.hilt.android.AndroidEntryPoint
import rk.first.saathi.ui.presentation.SaathiViewModel
import rk.first.saathi.ui.theme.SaathiTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO),
            0)

        val viewModel: SaathiViewModel by viewModels()

        setContent {
            SaathiTheme {
                Navigation(viewModel)
            }
        }
    }
}

