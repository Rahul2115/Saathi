@file:Suppress("DEPRECATION")

package rk.first.saathi.ui.presentation

import android.util.Size
import androidx.camera.core.AspectRatio
import androidx.camera.core.ImageCapture
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner

@Composable
fun CameraPreview (
    controller:LifecycleCameraController,
    modifier: Modifier = Modifier,
){
    val cameraController = LocalLifecycleOwner.current

    AndroidView(
        factory = {
            PreviewView(it).apply {
                this.controller = controller
                controller.bindToLifecycle(cameraController)
            }
        },
        modifier = modifier
    )
}

