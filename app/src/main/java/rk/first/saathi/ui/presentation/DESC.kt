@file:Suppress("DEPRECATION")

package rk.first.saathi.ui.presentation

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.AsyncImage
import rk.first.saathi.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DESC(navController: NavController, state:State, viewModel: SaathiViewModel) {
    val interactionSource = remember { MutableInteractionSource() }

    Scaffold(
        bottomBar = {
            val itemList = listOf(
                BottomNavItem.Home,
                BottomNavItem.DESC,
                BottomNavItem.OBJECT,
            )
            HomeFooter(itemslist = itemList,navController)
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
            DESCDisplay(interactionSource,state, viewModel = viewModel)
            //SceneDispaly(interactionSource,state, viewModel = viewModel)
        }
    }
}


@Composable
fun SceneDispaly(interactionSource: MutableInteractionSource,state: State,viewModel: SaathiViewModel)
{
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            selectedImageUri = uri
            selectedImageUri?.let { viewModel.generateDesc(it) }
        }
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }) {
                    Text(text = "Pick one photo")
                }
            }
        }

        item {
            AsyncImage(
                model = selectedImageUri,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun DESCDisplay(interactionSource: MutableInteractionSource,state: State,viewModel: SaathiViewModel)
{
    val context = LocalContext.current

    var textRead by remember {
        mutableStateOf("R")
    }

    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE)
        }
    }

    //controller.cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

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
                onClick = {},
                shape = CircleShape,
                containerColor = Color(0xFF2B0E48),
                contentColor = Color(0xFFFEE990)
            ) {
                Icon(painter = painterResource(id = R.drawable.home), "Large floating action button"
                    , modifier = Modifier.height(50.dp))
            }

            LargeFloatingActionButton(
                onClick = {
                        //ScenerioDesc()
                          viewModel.ScenerioDesc(controller = controller)
                },
                shape = CircleShape,
                containerColor = Color.White,
                modifier = Modifier.padding(start = 30.dp)
            ) {
                Icon(painter = painterResource(id = R.drawable.mic), "Large floating action button"
                    , modifier = Modifier.height(50.dp))
            }

//            LargeFloatingActionButton(
//                onClick = {
//                },
//                interactionSource = interactionSource,
//                shape = CircleShape,
//                containerColor = Color.White,
//                modifier = Modifier.padding(start = 30.dp)
//            ) {
//                Icon(painter = painterResource(id = R.drawable.mic), "Large floating action button"
//                    , modifier = Modifier.height(50.dp))
//            }
        }
    }
}



//@Composable
//fun DESCDisplay(interactionSource: MutableInteractionSource,state: State,viewModel: SaathiViewModel)
//{
//    val gradient = Brush.linearGradient(
//        listOf(Color(0XFFFEE990),Color(0xFFF2D660))
//    )
//
//    val isPressed by interactionSource.collectIsPressedAsState()
//
//    Box(modifier = Modifier
//        .fillMaxWidth()
//        .height(74.dp)
//        .background(gradient))
//    {
//        Log.d("TextRead",state.text)
//
//        Text(
//            text = state.text,
//            modifier = Modifier.align(Alignment.Center)
//        )
//        viewModel.speak(text = state.text)
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize(),
//    )
//    {
//        Row(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(start = 20.dp, bottom = 20.dp),
//            horizontalArrangement = Arrangement.Start,
//            verticalAlignment = Alignment.Bottom
//        )
//        {
//            LargeFloatingActionButton(
//                onClick = {},
//                shape = CircleShape,
//                containerColor = Color(0xFF2B0E48),
//                contentColor = Color(0xFFFEE990)
//            ) {
//                Icon(painter = painterResource(id = R.drawable.home), "Large floating action button"
//                    , modifier = Modifier.height(50.dp))
//            }
//
//            LargeFloatingActionButton(
//                onClick = {
//                },
//                interactionSource = interactionSource,
//                shape = CircleShape,
//                containerColor = Color.White,
//                modifier = Modifier.padding(start = 30.dp)
//            ) {
//                if(isPressed){
//                    Log.d("Voice","Listening")
//                    Icon(painter = painterResource(id = R.drawable.mic), "Large floating action button"
//                        , modifier = Modifier.height(50.dp))
//
//                    viewModel.startListen()
//                }else{
//                    Icon(painter = painterResource(id = R.drawable.mic), "Large floating action button"
//                        , modifier = Modifier.height(50.dp))
//
//                    viewModel.speechRecognizer.stopListening()
//                }
//
//
//            }
//        }
//    }
//}
