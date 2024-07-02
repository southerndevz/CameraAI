import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.github.sarxos.webcam.Webcam
import com.github.sarxos.webcam.WebcamPanel
import com.github.sarxos.webcam.WebcamResolution

@Composable
actual fun CameraView() {
    var error:Throwable = Throwable(message = "No webcam found")
    val webcam = remember { runCatching { Webcam.getDefault()}.getOrElse {
        error = it
        null
    } }


    if (webcam != null) {
        webcam.viewSize = WebcamResolution.VGA.size

        DisposableEffect(webcam) {
            webcam.open()
            onDispose {
                webcam.close()
            }

        }

        val panel = remember {
            WebcamPanel(webcam).apply {
                isFPSDisplayed = false
                isDisplayDebugInfo = false
                isImageSizeDisplayed = false
                isMirrored = true
            }
        }

        DisposableEffect(panel) {
            onDispose {
                panel.webcam.close()
            }
        }

            SwingPanel(
                factory = { panel },
                modifier = Modifier.fillMaxSize()
            )

    } else {
        Text(text = error.cause?.message?:"No webcams found.")
    }
}