package rk.first.saathi

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import rk.first.saathi.ui.theme.SaathiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO),0)
        val context = applicationContext
        setContent {
            SaathiTheme {
                Navigation()
            }
        }
    }
}

