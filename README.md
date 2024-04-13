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
- The cats as a service API was used to retrieve 10 random cat references using endpoint **v1/images/search?has_breeds=1&order=RAND**
- Specific cat data retrieved using endpoint **v1/images/{id}**.
  - Breeds Details added using collapsible panel
- Re-usability
  - UI Components
    - Cat Image
    - Screen Loading message
    - Error message
  - Resource state handling
  - Error handling
    - More fine grained handling possible by using error enums.

## Build 
To build this application, you need
- The latest version of Android Studio.
- Add the following value to the **local.properties** file 
  - CATAAS_API_URL = https://api.thecatapi.com/, to set up the remote API value.
- Hit build