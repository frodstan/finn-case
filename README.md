# Finn ads case

## Testing
**Make sure to build the app in release mode when testing it.** 

Jetpack Compose runs a lot slower in debug mode.

## Solution
### Tech stack
- Jetpack Compose (For rendering the UI)
- Retrofit (For networking)
- Moshi (For deserialization of network response)
- Coil (For image loading)
- Koin (For dependency injection)
- Room (For storing our favourite ads)

### Project structure
The project is in a multi-module setup. The assignment says to keep it simple, but I believe the setup is a good compromise between not being too complex and scalability.
It is structured in a way that modules can only depend on other modules lower in the hierarchy (Platform -> Feature -> Library). 
This keeps the dependency graph flat, speeds up build time because we do not have circular dependencies and helps us with decoupling.
I have used Kotlin DSL for the Gradle scripts, which I am sure can be done in a more clean way by grouping dependencies or something. I have little experience using it, but I thought this project would be a good chance to get some experience with it.

In the UI-layer I have gone for a MVVM setup where ViewModel is responsible for mapping data from the repositories to the UI models.
As for the offline functionality, I have chosen to create a simple disk cache in the Data-layer with datastore. I just cache the last successful response, and use it in case the network request fails.
The favourites are stored in a Room database using the `favourite` property in the item response. I thought this way made sense the way the response was structured. 
Another option I considered was to store the whole item object in the Room database, and at the same time use it as an offline cache. 
But I moved away from this idea because it is easier keeping the items up to date by caching the latest successful response.

## If I had more time
If I had more time to complete the project, there are a few more things to do
- Add tests. There are no tests in the project now, neither Unit or Instrumented tests.
- Create better abstractions wrapping around 3rd party libraries. I'm thinking mostly about Coil in this case, so it would be easier to swap them out later if needed.
- Better error handling. The error handling in the app is very minimal. It should not crash, but if there is an error fetching data it would just display error. We should show more information about what went wrong, and provide the user with a way around it if possible (refresh for example)
- More testing and better implementation for accessibility services like screen readers.
- Enable R8


I had fun with the project, and I hope you're satisfied with the solution I have come up with.
