# **This is a Kotlin Multiplatform project targeting Android, iOS, Desktop.**

I am attempting to build a KMP app that access the camera for various applications.
Mainly, I'm interested in using it for Computer Vision applications such as Object Detection using the TF (Java) / TF-Lite framework across all platforms.

So far I am able to display a camera feed on:

* Android
* iOS / iPadOS
* Windows (JVM)
* macOS (JVM)

<img src="https://github.com/user-attachments/assets/11f80c8f-392f-4ec9-8b89-1b23844c4270" height="270">
<img src="https://github.com/user-attachments/assets/18be026e-ae6f-40ae-9616-0d3a5491bf78" height="270">

&nbsp;
<img src="https://github.com/user-attachments/assets/154d9401-bd44-42f7-b235-d42acd66506f" height="650">
<img src="https://github.com/user-attachments/assets/b0a18a9c-385c-4e8d-aba5-b78c1177fd85" height="650">

Not tested but should run without a problem on Linux (JVM) since all JVM targets run without issue, therefore it's given that this will run.


**Note:** _Currently the JVM Desktop uses a JavaCVDriver that target all platforms including Mac, Windows, Linux, Android, etc. except for macOS support for other targets are not necessary since they already work with the default driver. In future commits I will remove these dependencies but it is not priority now._ [More Info: Reducing The Number of JavaCV Dependencies](https://github.com/bytedeco/javacpp-presets/wiki/Reducing-the-Number-of-Dependencies)
