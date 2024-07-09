import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cameraai.composeapp.generated.resources.Res
import cameraai.composeapp.generated.resources.camera_slash
import org.jetbrains.compose.resources.painterResource


@Composable
expect fun CameraView(cameraOpened:Boolean = true, cameraSelected:CameraSelected = CameraSelected.RearOrDefaultWebcam)

@Composable
fun ClosedCameraView() {
    Box(Modifier.fillMaxSize().background(Color.Black)) {
        Icon(
            modifier = Modifier.align(Alignment.Center).size(40.dp),
            painter = painterResource(Res.drawable.camera_slash),
            contentDescription = "Camera closed",
            tint = Color.White.copy(alpha = 0.5f)
        )
    }
}

sealed class CameraSelected {
    data object RearOrDefaultWebcam : CameraSelected()
    data object SelfieOrAdditionalWebcam : CameraSelected()
}
