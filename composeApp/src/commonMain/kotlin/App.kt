import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cameraai.composeapp.generated.resources.Res
import cameraai.composeapp.generated.resources.camera_change
import cameraai.composeapp.generated.resources.camera_shutter
import cameraai.composeapp.generated.resources.camera_slash
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {



    MaterialTheme {

        var cameraOpen by remember { mutableStateOf(true) }
        var selectedCamera:CameraSelected by remember { mutableStateOf(CameraSelected.RearOrDefaultWebcam) }

        Box(modifier = Modifier.fillMaxSize()) {

            key(cameraOpen, selectedCamera) {
                CameraView(cameraOpen, selectedCamera)
            }
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                CameraControls(
                    cameraOpen,
                    selectedCamera,
                    onCameraOpenChange = { cameraOpen = it },
                    onCameraChange = { if (cameraOpen) selectedCamera = it })
            }
        }
    }
}
@Composable
fun CameraControls(cameraOpen: Boolean, selectedCamera:CameraSelected, onCameraOpenChange: (Boolean) -> Unit, onCameraChange: (CameraSelected) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().background(Color.Black.copy(alpha = 0.45f)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        IconButton(modifier = Modifier.size(30.dp), onClick = { onCameraOpenChange(!cameraOpen) }) {
            Icon(painter = painterResource(Res.drawable.camera_slash), contentDescription = "Stop Camera", tint = if (cameraOpen) Color.White.copy(alpha = 0.3f) else Color.White )
        }

        IconButton(modifier = Modifier.size(90.dp), onClick = { /*TODO*/ }) {
            Icon(painter = painterResource(Res.drawable.camera_shutter), contentDescription = "Capture", tint = Color.White)
        }

        IconButton(modifier = Modifier.size(30.dp), onClick = {
            when (selectedCamera) {
                CameraSelected.RearOrDefaultWebcam -> onCameraChange(CameraSelected.SelfieOrAdditionalWebcam)
                CameraSelected.SelfieOrAdditionalWebcam -> onCameraChange(CameraSelected.RearOrDefaultWebcam)
            }
        }) {
            Icon(painter = painterResource(Res.drawable.camera_change), contentDescription = "Swap Camera", tint = Color.White)
        }
    }
}