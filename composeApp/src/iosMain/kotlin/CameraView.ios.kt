import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVCaptureDeviceInput
import platform.AVFoundation.AVCaptureDevicePositionBack
import platform.AVFoundation.AVCaptureDevicePositionFront
import platform.AVFoundation.AVCaptureSession
import platform.AVFoundation.AVCaptureSessionPresetPhoto
import platform.AVFoundation.AVCaptureStillImageOutput
import platform.AVFoundation.AVCaptureVideoPreviewLayer
import platform.AVFoundation.AVLayerVideoGravityResizeAspectFill
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.AVVideoCodecJPEG
import platform.AVFoundation.AVVideoCodecKey
import platform.AVFoundation.position
import platform.CoreGraphics.CGRect
import platform.QuartzCore.CATransaction
import platform.QuartzCore.kCATransactionDisableActions
import platform.UIKit.UIView


@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun CameraView(cameraOpened: Boolean, cameraSelected: CameraSelected, hazeState: HazeState) {

    if(!cameraOpened) return ClosedCameraView(hazeState = hazeState)

    val device = AVCaptureDevice.devicesWithMediaType(AVMediaTypeVideo).firstOrNull(){ device ->
        (device as AVCaptureDevice).position == when (cameraSelected) {
            CameraSelected.RearOrDefaultWebcam -> AVCaptureDevicePositionBack
            CameraSelected.SelfieOrAdditionalWebcam -> AVCaptureDevicePositionFront
            else -> AVCaptureDevicePositionBack
        }
    }!! as AVCaptureDevice

    val input = AVCaptureDeviceInput.deviceInputWithDevice(device, error = null) as AVCaptureDeviceInput

    val output = AVCaptureStillImageOutput()
    output.outputSettings = mapOf(AVVideoCodecKey to AVVideoCodecJPEG)


    val session  = AVCaptureSession()
    session.sessionPreset = AVCaptureSessionPresetPhoto

    session.addInput(input)
    session.addOutput(output)

    val cameraPreviewLayer = remember { AVCaptureVideoPreviewLayer(session = session) }

    UIKitView(
        modifier = Modifier.fillMaxSize()
            .haze(
                state = hazeState,
                style = HazeStyle(
                    tint = Color.Black.copy(alpha = .2f),
                    blurRadius = 30.dp,
                    noiseFactor = HazeDefaults.noiseFactor
                )
            )
        ,
        background = Color.Black,
        factory = {
            val container = UIView()
            container.layer.addSublayer(cameraPreviewLayer)
            cameraPreviewLayer.videoGravity = AVLayerVideoGravityResizeAspectFill
            session.startRunning()
            return@UIKitView container
        },
        onResize = { container:UIView, rect:CValue<CGRect> ->
            CATransaction.begin()
            CATransaction.setValue(true, kCATransactionDisableActions)
            container.setFrame(rect)
            cameraPreviewLayer.setFrame(rect)
            CATransaction.commit()
        })


}