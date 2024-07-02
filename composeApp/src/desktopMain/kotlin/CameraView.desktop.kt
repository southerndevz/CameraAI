import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import com.github.sarxos.webcam.Webcam
import com.github.sarxos.webcam.WebcamPanel
import com.github.sarxos.webcam.WebcamResolution
import com.github.sarxos.webcam.ds.javacv.JavaCvDriver
import java.util.Locale

@Composable
actual fun CameraView() {

    val os = System.getProperty("os.name").lowercase(Locale.getDefault())
    var error: Throwable = Throwable(message = "No webcam found")

    if (os.startsWith("mac")) {
      Webcam.setDriver(JavaCvDriver::class.java)
    }

    val webcam = remember {
        runCatching { Webcam.getDefault() }.getOrElse {
            error = it
            null
        }
    }

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
        Text(text = "Oops: ${error.message?: "Unknown error"}")
    }
}