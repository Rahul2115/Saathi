package rk.first.saathi

import android.Manifest
import android.content.IntentFilter
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.google.android.gms.auth.api.phone.SmsRetriever
import dagger.hilt.android.AndroidEntryPoint
import rk.first.saathi.ui.presentation.SaathiViewModel
import rk.first.saathi.ui.theme.SaathiTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: SaathiViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO),
            0)
        viewModel.setAct(this)
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        setContent {
            SaathiTheme {
                Navigation(viewModel)
            }
        }
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()
    }

//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
//            if(viewModel.state.value.currentPage == "look"){
//                viewModel.clickStateValue(true)
//            }
//        }
//        return super.onKeyDown(keyCode, event)
//    }
}

