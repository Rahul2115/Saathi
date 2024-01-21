# SAATHI
<img src="https://github.com/Rahul2115/Saathi/blob/master/images/logo.jpeg" alt="Logo" width="300" height="300">

## An AI powered aid for the visually impaired community
[![My Skills](https://skillicons.dev/icons?i=kotlin,androidstudio,firebase,gcp,tensorflow,figma&perline=&theme=)](https://skillicons.dev)

Saathi is an AI powered, fully voice activated android application designed to assist the visually impaired community. It leverages the power cutting-edge technologies like Large Language Models (LLMs), Natural Language Processing, Text-to-speech and Text-to-speech models , Computer Vision models and much more. Saathi aligns with the united nations sustainable development goal of reducing inequalities in the society.

## Features

- Fully voice-activated commands using Text-to-speech, Speech-to-text models and NLP.
- Large Language Model module to help in educational question answering and learning using Gemini API.
- Scene description using Gemini Vision API.
- Sign-board recognition and Institution board text recognition module.
- Pothole detection and alerting module for seamless outdoor navigation.
- Specially designed voice activated authentication system for personalization

  
All these features to be combined in one single android app and all features to be activated and de-activated using voice.

## Tech
Saathi uses the following technologies:

- [Kotlin](https://kotlinlang.org) - Programming Language for Android
- [Android Studio](https://developer.android.com/studio?gclid=Cj0KCQiAnrOtBhDIARIsAFsSe51MxgNrDQ5ajwxGi3g24wLt3r2TpVdCGJW-JKBjzhmmnNaanvxLVdIaAn2sEALw_wcB&gclsrc=aw.ds)- Official Integrated Development Environment (IDE) for Android app development
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Android UI developent Toolkit
- [ML-Kit](https://developers.google.com/ml-kit) -Machine Learning Package for android development 
- [TensorFlow](https://www.tensorflow.org) - Deep Learning Framework (using it for custom object detection)
- [Gemini API](https://ai.google.dev)- Cloud APIs for LLM and Vision 
- [Firebase](https://firebase.google.com) - Back-end and Database
- [Figma](https://www.figma.com/) - Ui Design and prototyping

## Installation
### Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/Rahul2115/Saathi.git
    ```

2. Open the project in Android Studio.

3. Ensure that you have the necessary dependencies installed:
   - [Android Studio](https://developer.android.com/studio)
   - [JDK (Java Development Kit)](https://www.oracle.com/java/technologies/javase-downloads.html)

4. **Gemini API Setup:**
   - Obtain API credentials from the [Gemini API](https://gemini.com/api).
   - Create a `secrets.properties` file in the project root and add your Gemini API key and secret:

     ```properties
     GEMINI_API_KEY=your_api_key
     GEMINI_API_SECRET=your_api_secret
     ```

     Make sure to add `secrets.properties` to your `.gitignore` file to keep your API credentials secure.

5. Build and run the app on an emulator or physical device.


## Flow Diagrams
<details>
<summary>Authentication Module</summary>
<img src="https://github.com/Rahul2115/Saathi/blob/master/images/AUTH.png" alt="Logo">

</details>

<details>
  <summary>LLM Module</summary>
  <img src="https://github.com/Rahul2115/Saathi/blob/master/images/LLM_Learning.png" alt="Logo">
</details>

<details>
  <summary>Institutional-Board Text Recognition Module</summary>
  <img src="https://github.com/Rahul2115/Saathi/blob/master/images/OCR.png" alt="Logo">
</details>

<details>
  <summary>Obstacle Detection Module</summary>
  <img src="https://github.com/Rahul2115/Saathi/blob/master/images/Obstacle%20Detection.png" alt="Logo">
</details>

<details>
<summary>Scenario Description</summary>
  <img src="https://github.com/Rahul2115/Saathi/blob/master/images/Image_Description.png" alt="image">
</details>





