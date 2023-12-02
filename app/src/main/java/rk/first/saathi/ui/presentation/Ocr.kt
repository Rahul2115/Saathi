package rk.first.saathi.ui.presentation

import android.content.Context
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import rk.first.saathi.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Ocr(navController: NavController) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Scaffold(
        bottomBar = {
            val itemslist = listOf(
                BottomNavItem.LLM,
                BottomNavItem.OCR,
                BottomNavItem.Home,
            )
            HomeFooter(itemslist = itemslist,navController)
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
            Header()
            OcrDisplay(isPressed,navController,interactionSource)
        }
    }
}

@Composable
fun OcrDisplay(isPressed: Boolean,navController: NavController,interactionSource: MutableInteractionSource){
    val context = LocalContext.current
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE or
                CameraController.IMAGE_ANALYSIS)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
    )
    {
        CameraPreview(controller =controller,
            modifier = Modifier.fillMaxSize()
            )

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
                },
                interactionSource = interactionSource,
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