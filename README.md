# suspend coroutine callback in Location Callback. /nString Provider.
# interceptor to make retrofit read from file as cache.
# MVI pattern
<img src="https://miro.medium.com/v2/resize:fit:1400/1*WKCrQxvpb0_fAr3kSk6Jxw.jpeg" width="500">


## coroutine callback
This is a sample app that demonstrate how to use coroutine Callback in Android application in clean architecture.



## StringProvider Mechanism
This sample app also  demonstrate how to use string provider to make domain use string resource but the value from presentation



## Interceptor to make retrofit read from file 
This app also  demonstrate how to read fro file using retrofit using interceptor



## Package Architecture 
Because it is an architectural project, UI has been kept simple.

### Flow 
This app uses [_**MVI (Model View Intent)**_](https://proandroiddev.com/mvi-architecture-with-kotlin-flows-and-channels-d36820b2028d) architecture.
 

### Package Modules

- **App Package**

  `:app` module is an [com.android.application](https://developer.android.com/studio/projects/android-library), which is needed to create the app bundle. It contains dependency graph and UI related classes. It presents data to screen and handle user interactions.

- **Data Package**

  `:data` module contains implementation of repository and local - remote repository interface adapt
  
- **Domain Package**

  `:domain` module contains use cases and repository interface adapt
	  
 - **Presentation Module**
 
	  `:presentation` module contains ui 

Each module has its own test.

### Tech Stack
- [Kotlin](https://kotlinlang.org)
- [Jetpack](https://developer.android.com/jetpack)
	* [Android KTX](https://developer.android.com/kotlin/ktx)
    * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle)
    * [Data Binding](https://developer.android.com/topic/libraries/data-binding)
    * [View Binding](https://developer.android.com/topic/libraries/view-binding)
    *  [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
    * [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started)
    * [Room](https://developer.android.com/training/data-storage/room)
- [Coroutines - Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html)
  - [State Flow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)
  -   [Shared Flow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)
  -  [Channels](https://kotlinlang.org/docs/channels.html#channel-basics)
- [Dagger Hilt](https://dagger.dev/hilt/)
- [Retrofit](https://square.github.io/retrofit/)
- [OkHttp](https://github.com/square/okhttp)
- [KotlinX](https://github.com/Kotlin/kotlinx.serialization)
- [KotlinX Serialization Converter](https://github.com/JakeWharton/retrofit2-kotlinx-serialization-converter)
- [LeakCanary](https://square.github.io/leakcanary/)
- [Testing](https://developer.android.com/training/testing/fundamentals)
    *  [MockK](https://mockk.io/)
    * [Junit4](https://junit.org/junit4/)
    * [Truth](https://github.com/google/truth)
    * [Turbine](https://github.com/cashapp/turbine)
    * [Fragment Testing](https://developer.android.com/guide/fragments/test)
    * [Navigation Testing](https://developer.android.com/guide/navigation/navigation-testing)
    * [Coroutine Test](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test)
    * [Barista](https://github.com/AdevintaSpain/Barista)
    * [Dagger Hilt Testing](https://developer.android.com/training/dependency-injection/hilt-testing)

### Contributions
