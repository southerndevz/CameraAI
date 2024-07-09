import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView

@Composable
actual fun CameraView (cameraOpened:Boolean, cameraSelected:CameraSelected) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember { PreviewView(context) }

    if(!cameraOpened) {
        cameraProviderFuture.get().unbindAll()
        return ClosedCameraView()
    }

    key(cameraSelected) {
        cameraProviderFuture.get().unbindAll()
    }

    AndroidView(factory = { previewView },
        modifier = Modifier.fillMaxSize(),
        update = { _ ->
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(previewView.surfaceProvider)
            val cameraSelector = CameraSelector.Builder().requireLensFacing(
                when (cameraSelected) {
                    CameraSelected.RearOrDefaultWebcam -> CameraSelector.LENS_FACING_BACK
                    CameraSelected.SelfieOrAdditionalWebcam -> CameraSelector.LENS_FACING_FRONT
                    else -> CameraSelector.LENS_FACING_BACK
                }).build()
            cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview)
        })
}
