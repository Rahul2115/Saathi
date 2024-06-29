package rk.first.saathi.ui.presentation

import android.util.Log
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import rk.first.saathi.R

@Composable
fun LOOK(navController: NavController, state:State, viewModel: SaathiViewModel) {
    viewModel.updatePageState(navController.currentDestination?.route?.lowercase())

    viewModel.clickStateValue(false)

    val context = LocalContext.current

    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE)
        }
    }

    if(state.clickedState){
        viewModel.ScenerioDesc(controller = controller)
    }

    Scaffold(
        bottomBar = {
            HomeFooter2(navController = navController,viewModel)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFFEE990)),
        )
        {
            DESCDisplay(state, viewModel = viewModel,controller)
        }
    }
}

@Composable
fun DESCDisplay(state: State,viewModel: SaathiViewModel,controller:LifecycleCameraController)
{
    val gradient = Brush.linearGradient(
        listOf(Color(0XFFFEE990),Color(0xFFF2D660))
    )
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(74.dp)
        .background(gradient))
    {
        Log.d("TextRead",state.text)

        Text(
            text = state.text,
            modifier = Modifier.align(Alignment.Center)
        )
        viewModel.speak(text = state.text)
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
    )
    {
        CameraPreview(controller =controller,
            modifier = Modifier.fillMaxSize())

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        )
        {
            LargeFloatingActionButton(
                onClick = {
                    if (viewModel.checkConnectivity()){
                        if(!viewModel.tts.isSpeaking){
                            viewModel.ScenerioDesc(controller = controller)
                        }
                    }else{
                        viewModel.speak("Make sure that WI-FI or mobile data is turned on, then try again")
                    }
                },
                shape = CircleShape,
                containerColor = Color.White,
                modifier = Modifier.height(120.dp).width(120.dp).clearAndSetSemantics {
                    contentDescription = "Look click button. Double-tap to click the picture. swipe when speaking to stop"
                }
            ) {
                Image(painter = painterResource(id = R.drawable.cam), "Click"
                    , modifier = Modifier.height(60.dp))
            }
        }
    }
}



