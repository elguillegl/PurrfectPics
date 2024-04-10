## Purrfect Pics
This a sample application that integrates good practices for Android apps that make use of remote data.

## Tech Stack
Kotlin
Jetpack Compose
Compose Destinations for navigation
MVVM
Kotlin Coroutines
Repository Pattern to seamlessly switch between remote and local data source.
Hilt for Dependency Injection
Retrofit for API consumption
Coil for image loading

## Highlights
- The cats as a service API was used to retrieve 10 random cat references, this makes the app more dynamic and also sample data in PDF was out of sync with current API specs and data.
- Error handling was added to the whole app, as the service sometimes won't respond, so It includes error placeholders for images and static data retrieved from a local asset to show in the list.
- There's not much data provided by the API, so opted to add extra functionality like zooming and panning.
- Everything on the code that is not specifically related to the domain of the data (API, Screens, )could be reused to adapt to another type of dataset.

## Build 
To build this application, you need
- The latest version of Android Studio.
- Add the following value to the **local.properties** file 
  - CATAAS_API_URL = https://cataas.com/, to set up the remote API value.
- Hit build