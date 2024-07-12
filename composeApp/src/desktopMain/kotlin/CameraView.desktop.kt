import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.sarxos.webcam.Webcam
import com.github.sarxos.webcam.WebcamPanel
import com.github.sarxos.webcam.WebcamResolution
import com.github.sarxos.webcam.ds.javacv.JavaCvDriver
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import java.util.Locale


@Composable
actual fun CameraView(cameraOpened: Boolean, cameraSelected: CameraSelected, hazeState: HazeState) {

    val os = System.getProperty("os.name").lowercase(Locale.getDefault())
    var error = Throwable(message = "Error retrieving webcam(s)")
    var currentWebcamNumber by mutableStateOf( 0)

    if (os.startsWith("mac")) {
      Webcam.setDriver(JavaCvDriver::class.java)
    }

    val webcams = remember {
        runCatching { Webcam.getWebcams() }.getOrElse {
            error = it
            null
        }
    }

    if (!webcams.isNullOrEmpty()) {

        key(cameraSelected) {
            if (webcams.size > 1) {
                webcams.forEach { it.device.close() }
                currentWebcamNumber = when (cameraSelected) {
                    CameraSelected.RearOrDefaultWebcam -> 0
                    CameraSelected.SelfieOrAdditionalWebcam -> {
                        if (webcams.size > 1) 1 else 0
                    }
                    else -> 0
                }
            }
        }



        val webcam = remember { webcams[currentWebcamNumber] }
        webcam.viewSize = WebcamResolution.VGA.size

        val panel = WebcamPanel(webcam.apply { close() }, false).apply {
                isFPSDisplayed = false
                isDisplayDebugInfo = false
                isImageSizeDisplayed = false
                isMirrored = true
            }

        if (!cameraOpened) {
            panel.stop()
            panel.webcam.close()

            return ClosedCameraView(hazeState = hazeState)
        } else {
            SwingPanel(
                factory = { panel.apply { this.webcam.open(); this.start() } },
                modifier = Modifier.fillMaxSize()
                    .haze(
                        state = hazeState,
                        style = HazeStyle(
                            tint = Color.Black.copy(alpha = .2f),
                            blurRadius = 30.dp,
                            noiseFactor = HazeDefaults.noiseFactor
                        )
                    )
            )
        }



    } else {
        Text(text = "Oops: ${error.message?: "No webcams detected."}")
    }
}