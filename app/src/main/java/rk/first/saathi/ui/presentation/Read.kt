package rk.first.saathi.ui.presentation

import android.util.Log
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import rk.first.saathi.R
import rk.first.saathi.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Read(navController: NavController, state:State, viewModel: SaathiViewModel) {
    val interactionSource = remember { MutableInteractionSource() }

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
            val itemList = listOf(
                BottomNavItem.LOOK,
                BottomNavItem.Read,
                BottomNavItem.Setting,
            )
            HomeFooter(itemslist = itemList,navController,viewModel)
        },
        floatingActionButton = {
            HomeHelp()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFFEE990)),
        )
        {
            ReadDisplay(interactionSource,state, viewModel = viewModel,controller,navController)
        }
    }
}

@Composable
fun ReadDisplay(interactionSource: MutableInteractionSource,state: State,viewModel: SaathiViewModel,controller:LifecycleCameraController,navController: NavController)
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
                .padding(start = 20.dp, bottom = 20.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        )
        {
            LargeFloatingActionButton(
                onClick = {
                    viewModel.changeScreenSpeak("home")
                    viewModel.updateScreen(Screen.Home.route)
                    navController.navigate(Screen.Home.route)
                },
                shape = CircleShape,
                containerColor = Color(0xFF2B0E48),
                contentColor = Color(0xFFFEE990)
            ) {
                Icon(painter = painterResource(id = R.drawable.home), "Large floating action button"
                    , modifier = Modifier.height(50.dp))
            }

            LargeFloatingActionButton(
                onClick = {
                    viewModel.TextRecognition(controller,viewModel)
                },
                shape = CircleShape,
                containerColor = Color.White,
                modifier = Modifier.padding(start = 30.dp)
            ) {
                Icon(painter = painterResource(id = R.drawable.mic), "Large floating action button"
                    , modifier = Modifier.height(50.dp))
            }
        }
    }
}