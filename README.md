# **This is a Kotlin Multiplatform project targeting Android, iOS, Desktop.**

I am attempting to build a KMP app that access the camera for various features.
I'm interested in using it for Computer Vision applications such as Object Detection using the TFLite framework with KMP.

So far I am able to display a camera feed on:

* Android 11
* Windows 11 (JVM)
* macOS Sonoma (JVM)

The following platforms are not fully tested but should run without a problem:

* Linux (JVM) - _all JVM targets run without issue, therefore it's given that this will run. I don't have a Linux computer to test on._
* iOS - _it builds and installs onto the iOS Simulator but I don't have the latest MacBook with a good GPU to render the iPhone so I don't see what happens)_

**Note:** Currently the JVM Desktop uses a JavaCVDriver that target all platforms including Mac, Windows, Linux, Android, etc. except for macOS support for other targets are not necessary since they already work with the default driver. In future commits I will remove these dependencies but it is not priority now. [More Info: Reducing The Number of JavaCV Dependencies](https://github.com/bytedeco/javacpp-presets/wiki/Reducing-the-Number-of-Dependencies)

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
