import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import cameraai.composeapp.generated.resources.Res
import cameraai.composeapp.generated.resources.camera_change
import cameraai.composeapp.generated.resources.camera_shutter
import cameraai.composeapp.generated.resources.camera_slash
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.systemBarsPadding

@Composable
@Preview
fun App() {



    MaterialTheme {

        var cameraOpen by remember { mutableStateOf(true) }
        var selectedCamera:CameraSelected by remember { mutableStateOf(CameraSelected.RearOrDefaultWebcam) }
        val hazeState = remember { HazeState() }

        Box(modifier = Modifier
            .fillMaxSize()) {

            key(cameraOpen, selectedCamera) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CameraView(cameraOpen, selectedCamera, hazeState)
                }
            }
            Box(modifier = Modifier.align(Alignment.BottomCenter).hazeChild(state = hazeState, shape = RectangleShape)) {
                    CameraControls(
                        cameraOpen,
                        selectedCamera,
                        onCameraOpenChange = { cameraOpen = it },
                        onCameraChange = { if (cameraOpen) selectedCamera = it },
                        hazeState)
            }
        }
    }
}



@Composable
fun CameraControls(cameraOpen: Boolean, selectedCamera:CameraSelected, onCameraOpenChange: (Boolean) -> Unit, onCameraChange: (CameraSelected) -> Unit, hazeState: HazeState) {

    Box(modifier = Modifier) {
        Column(
            modifier = Modifier
                //.background(Color.Black.copy(alpha = 0.5f))
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                IconButton(
                    modifier = Modifier.size(30.dp),
                    onClick = { onCameraOpenChange(!cameraOpen) }) {
                    Icon(
                        painter = painterResource(Res.drawable.camera_slash),
                        contentDescription = "Stop Camera",
                        tint = if (cameraOpen) Color.White.copy(alpha = 0.3f) else Color.White
                    )
                }

                IconButton(modifier = Modifier.size(90.dp),
                    onClick = { /*TODO*/ }) {
                    Icon(
                        modifier = Modifier,
                        painter = painterResource(Res.drawable.camera_shutter),
                        contentDescription = "Capture",
                        tint = Color.White
                    )
                }

                IconButton(modifier = Modifier.size(30.dp), onClick = {
                    when (selectedCamera) {
                        CameraSelected.RearOrDefaultWebcam -> onCameraChange(CameraSelected.SelfieOrAdditionalWebcam)
                        CameraSelected.SelfieOrAdditionalWebcam -> onCameraChange(CameraSelected.RearOrDefaultWebcam)
                    }
                }) {
                    Icon(
                        painter = painterResource(Res.drawable.camera_change),
                        contentDescription = "Swap Camera",
                        tint = Color.White
                    )
                }
            }
            Spacer(
                modifier = Modifier.padding(
                    horizontal = 0.dp,
                    vertical = systemBarsPadding.navigationBarSpacerPVs.calculateBottomPadding()
                )
            )
        }
    }
}