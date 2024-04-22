package rk.first.saathi.ui.presentation

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import rk.first.saathi.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Learn(navController: NavController, state:State, viewModel: SaathiViewModel) {
    val interactionSource = remember { MutableInteractionSource() }
    viewModel.updatePageState(navController.currentDestination?.route?.lowercase())
    Scaffold(
        bottomBar = {
            HomeFooter2(navController = navController,viewModel)
        },
        ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFFEE990)),
        )
        {
            LLMDisplay(interactionSource,state, viewModel = viewModel,navController)
        }
    }

}

@Composable
fun LLMDisplay(interactionSource: MutableInteractionSource,state: State,viewModel: SaathiViewModel,navController: NavController)
{
    val gradient = Brush.linearGradient(
        listOf(Color(0XFFFEE990),Color(0xFFF2D660))
    )

    val isPressed by interactionSource.collectIsPressedAsState()

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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Image(
                painter = painterResource(id = R.drawable.file),
                contentDescription = null,
                modifier = Modifier
                    .height(256.dp)
                    .width(311.dp)
            )
        }

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
                },
                interactionSource = interactionSource,
                shape = CircleShape,
                containerColor = Color.White,
                modifier = Modifier
                    .clearAndSetSemantics {
                        contentDescription = "Learn Mic Button. Double tap and hold to Speak. swipe when speaking to stop"
                    }.height(120.dp).width(120.dp)
            ) {
                if(isPressed){
                    if(!viewModel.tts.isSpeaking){
                        var mediaPlayer = MediaPlayer.create(LocalContext.current, R.raw.click)
                        mediaPlayer.start() // no need to call prepare(); create() does that for you
                    }
                    Icon(painter = painterResource(id = R.drawable.mic2), "Mic"
                        , modifier = Modifier.height(55.dp))
                    viewModel.learnListen()
                }else{
                    Icon(painter = painterResource(id = R.drawable.mic), "Mic"
                        , modifier = Modifier.height(55.dp))

                    viewModel.speechRecognizer.stopListening()
                }
            }
        }
    }
}
