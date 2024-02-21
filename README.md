# SAATHI
<img src="https://github.com/Rahul2115/Saathi/blob/master/images/logo.jpeg" alt="Logo" width="300" height="300">

## An AI powered aid for the visually impaired community
[![My Skills](https://skillicons.dev/icons?i=kotlin,androidstudio,firebase,gcp,tensorflow,figma&perline=&theme=)](https://skillicons.dev)

Saathi is an AI powered, fully voice activated android application designed to assist the visually impaired community in their daily activities. Our vision is to combine all the features required for daily and repetitive tasks of a blind individual in a single application.

Saathi leverages the power of cutting-edge technologies like Mulit - Modal Large Language Models (LLMs), Natural Language Processing, Text-to-speech and Text-to-speech models , Computer Vision and Image Processing models and much more. Saathi aligns with the united nations sustainable development goal of reducing inequalities in the society.

Our app is designed and developed by taking continous feedbacks from the visually impaired students from a local special school for differently abled children. 

## Features

Supports Fully voice-activated as well as regular button based app navigation. The UI is minimalistic and easy to use for the visually impaired individuals.

#### Saathi has 6 different modules :

1. **Authentication Module** : Provides a fully-voice activated login and signup experience for the blind individual, which has been developed using **firebase** number based authentication.

2. **LOOK Module** : Utilizes the **Gemini Pro Vision** API which describes the image that is clicked by the user. This module can be used for Outdoor navigation, signboard description, informational or direction board description, learning and much more. 

3. **READ Module** : Can read any page from a newspaper, book, document, etc. It utilizes Text-Recognition V2 API from the ML-Kit Library.

4. **LEARN Module** : Here we utilize the **Gemini Pro** API to help the blind individuals learn and enhance their knowledge by Voice-based Question Answering. This module can be effiently utilized to enhance knowledge in academic subjects, general knowledge and genral curiosity.

5. **FIND Module** : Unlike the look and read module, the FIND module continously scans for common keywords in the surrounding as text format eg : School Bus, Toilet, etc. Thus helping the blind individual to navigate effectively.

6. **Obstacle Detection Module** : We wish to build a custom obstacle detection and alerting module which can detect potholes, cars, etc  to help the blind inviduals in outdoor navigation. We are planning to combine this with Google Maps for effective travelling.


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

## Developer Machine Setup Guide

### Prerequisites

Before proceeding with the setup, ensure that the following software is installed on your development machine:

1. **Android Studio**: Download and install the latest version of [Android Studio](https://developer.android.com/studio).

2. **Git**: Install Git for version control. You can download it from the [official Git website](https://git-scm.com/).

3. **Gemini API Setup:**
   - Obtain API credentials from the [Gemini API](https://gemini.com/api).
   - Create a `secrets.properties` file in the project root and add your Gemini API key and secret:

     ```properties
     GEMINI_API_KEY=your_api_key
     GEMINI_API_SECRET=your_api_secret
     ```

     Make sure to add `secrets.properties` to your `.gitignore` file to keep your API credentials secure.
### Setup Instructions

Follow these steps to set up your development environment:

1. **Clone the Repository**: Open a terminal or command prompt and clone the GitHub repository containing the project code using the following command:

    ```bash
    git clone https://github.com/Rahul2115/Saathi.git
    ```

2. **Open Project in Android Studio**: Launch Android Studio and select "Open an existing Android Studio project." Navigate to the directory where you cloned the repository and select the `build.gradle` file located in the root directory of the project.

3. **Install Dependencies**: Android Studio may prompt you to install missing dependencies and SDK components. Follow the prompts to install them.

4. **Configure Firebase Backend**:
    - If you haven't already, create a Firebase project in the [Firebase Console](https://console.firebase.google.com/).
    - Add your Android app to the Firebase project and download the `google-services.json` file.
    - Place the `google-services.json` file in the `app` directory of your Android project.

5. **Run the App**: After configuring Firebase, you can run the app locally on an emulator or a connected device. Click the green play button in Android Studio to build and run the app.



## Flow Diagrams
Note: there have been changes in the system design after some feedbacks and iterations. We are working to include the updated diagrams as soon as possible!

<details>
<summary>Authentication Module</summary>
<img src="https://github.com/Rahul2115/Saathi/blob/master/images/AUTH.png" alt="Logo">

</details>

<details>
  <summary>LEARN Module</summary>
  <img src="https://github.com/Rahul2115/Saathi/blob/master/images/LLM_Learning.png" alt="Logo">
</details>

<details>
  <summary> READ Module</summary>
  <img src="https://github.com/Rahul2115/Saathi/blob/master/images/OCR.png" alt="Logo">
</details>


<details>
<summary>LOOK Module</summary>
  <img src="https://github.com/Rahul2115/Saathi/blob/master/images/Image_Description.png" alt="image">
</details>

<details>
  <summary>Obstacle Detection Module</summary>
  <img src="https://github.com/Rahul2115/Saathi/blob/master/images/Obstacle%20Detection.png" alt="Logo">
</details>






