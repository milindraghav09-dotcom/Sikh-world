# Sikh World — Android starter project

Premium black-and-gold Android app starter built with Kotlin + Jetpack Compose.

## Included
- Home screen and bottom navigation
- Sikh wallpaper and 3D artwork collection preview screens
- WhatsApp status section with a share-message action
- Live Kirtan screen that opens the official SGPC live page:
  https://new.sgpc.net/live-kirtan-sri-harmandir-sahib/

## Important content status
This is a working UI starter, not a finished media catalogue. The tiles are previews/placeholders. Before publishing, add images and vertical videos that you own or have permission to distribute. Do not download, re-upload, or re-stream SGPC broadcasts; the app opens SGPC's official page instead.

## Build an APK
1. Install Android Studio (current stable) on Windows.
2. Extract this ZIP.
3. Android Studio → Open → select the `SikhWorldAndroid` folder.
4. Allow Gradle sync and install the Android SDK requested by Android Studio.
5. Connect an Android phone with USB debugging enabled, or create an emulator.
6. Press Run to test.
7. To create an APK: Build → Build Bundle(s) / APK(s) → Build APK(s).
8. The debug APK will be under `app/build/outputs/apk/debug/`.

For a release APK, configure a signing key in Android Studio (Build → Generate Signed Bundle / APK). Keep the keystore private.

## Requirements
- Android Studio with Android SDK Platform 35
- Internet access to download Gradle and Android dependencies during first sync
- JDK 17 or the JDK bundled with Android Studio

## Next development work
- Add real licensed wallpaper image assets under `app/src/main/res/drawable/`.
- Replace preview tiles with an image grid and implement save/set-wallpaper actions.
- Add licensed 9:16 status videos and implement video preview/share.
- Add a privacy policy and app icon before Play Store release.
