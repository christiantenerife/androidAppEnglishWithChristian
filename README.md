# English with Christian

English with Christian is a multiplatform educational project developed as a final project for the **Higher National Diploma in Multiplatform Application Development (DAM)**.

The system combines an **Android application**, a **WordPress website**, and **interactive H5P activities**, allowing students to practise English at **B2 level** through mobile-friendly exercises.

---

##  Main Features

- User authentication using **Firebase Authentication**
- Dynamic loading of activities from **Firebase Firestore**
- Integration of **H5P interactive activities**
- Activities displayed inside the Android app using **WebView**
- Content management through a **WordPress website**
- Modular architecture allowing easy expansion

---

## Architecture

The system follows a simple cloud-based architecture:

Android App (Kotlin)  
⬇  
Firebase Authentication + Firestore  
⬇  
WordPress + H5P Activities

The activities are created in WordPress and stored as URLs in Firestore.  
The Android application retrieves those URLs dynamically and loads them within the app interface.

This approach allows **new activities to be added without recompiling the app**.

---

## Technologies Used

- **Android Studio**
- **Kotlin**
- **Firebase Authentication**
- **Firebase Firestore**
- **WordPress**
- **H5P Interactive Content**
- **WebView**
- **Git / GitHub**

---
Project Structure: 
EnglishwithChristian/
│
├── app/
│ ├── src/main/java/
│ ├── src/main/res/
│ └── AndroidManifest.xml
│
├── gradle/
├── build.gradle
├── settings.gradle
└── README.md

Installation

1. Clone the repository:
https://github.com/christiantenerife/androidAppEnglishWithChristian
2. Open the project in **Android Studio**

3. Sync Gradle dependencies

4. Run the project on:
   - an Android emulator
   - or a physical Android device

---

##  APK

A compiled APK version of the application is available in the project releases.

You can also build it manually in Android Studio:

Build → Build APK(s)

---

##  Future Improvements

- Progress tracking system
- Gamification elements
- Support for additional English levels (B1, C1)
- Publication on Google Play
- AI-assisted learning tools

---

##  Academic Context

This project was developed as part of the **DAM (Desarrollo de Aplicaciones Multiplataforma)** program.

It demonstrates the integration of mobile development, cloud services, and web technologies in a real educational application.

---

## Author

Christian David Moore

English Teacher and Developer

---

## License
