import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import com.github.sarxos.webcam.Webcam
import com.github.sarxos.webcam.WebcamPanel
import com.github.sarxos.webcam.WebcamResolution
import com.github.sarxos.webcam.ds.javacv.JavaCvDriver
import java.util.Locale


@Composable
actual fun CameraView(cameraOpened:Boolean, cameraSelected:CameraSelected) {

    val os = System.getProperty("os.name").lowercase(Locale.getDefault())
    var error = Throwable(message = "Error retrieving webcam(s)")
    var currentWebcamNumber = remember { 0 }

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
            webcams.forEach { it.device.close() }
            currentWebcamNumber = when (cameraSelected) {
                CameraSelected.RearOrDefaultWebcam -> 0
                CameraSelected.SelfieOrAdditionalWebcam -> {
                    if (webcams.size > 1) 1 else 0
                }
                else -> 0
            }
        }



        val webcam = remember { webcams[currentWebcamNumber] }
        webcam.viewSize = WebcamResolution.VGA.size

        val panel = remember {
            WebcamPanel(webcam, false).apply {
                isFPSDisplayed = false
                isDisplayDebugInfo = false
                isImageSizeDisplayed = false
                isMirrored = true
            }
        }

        DisposableEffect(panel) {
            onDispose {
                panel.let {
                    if (it.webcam.isOpen) it.webcam.close()
                    webcams.forEach { webcam -> webcam.device.close() }
                    if (it.isStarted || it.isStarting || it.isShowing) it.stop()
                }
            }
        }

        if (!cameraOpened) {
            panel.let {
                if (it.webcam.isOpen) it.webcam.close()
                webcams.forEach { webcam -> webcam.device.close() }
                if (it.isStarted || it.isStarting || it.isShowing) it.stop()
            }
            return ClosedCameraView()
        }

        SwingPanel(
            factory = { panel.apply { this.start() } },
            modifier = Modifier.fillMaxSize()
        )

    } else {
        Text(text = "Oops: ${error.message?: "No webcams detected."}")
    }
}