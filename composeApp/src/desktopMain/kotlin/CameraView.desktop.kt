import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
        webcam.viewSize = WebcamResolution.FHD.size
        webcam.open()
        val density = LocalDensity.current.density

        val panel = remember {
            WebcamPanel(webcam).apply {
                isFPSDisplayed = true
                isDisplayDebugInfo = true
                isImageSizeDisplayed = true
                isMirrored = true
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            SwingPanel(
                factory = { panel },
                modifier = Modifier.size(width = (1080 * density).dp, height = (720 * density).dp)
            )
        }
    } else {
        Text(text = error.cause?.message?:"No webcams found.")
    }

}