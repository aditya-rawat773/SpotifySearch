
# SpotifySearch

## Project Overview
The app allows users to search for albums, artists, playlists, and tracks from the Spotify search API. It displays the search results in different sections, and users can view detailed information about each item by clicking on it. Additionally, the app supports offline functionality using Room Database, saving the last searched result.

## Architecture
The project follows the MVVM (Model-View-ViewModel) architecture pattern, incorporating the following key components:
- **Model**: Represents the data layer, responsible for data management and interactions with external APIs. Room Database is utilized for local storage.
- **View**: Comprises fragments responsible for presenting user interface components and handling user interactions.
- **ViewModel**: Acts as a mediator between the View and the Model, managing UI-related data and facilitating communication with the data layer. 
- Hilt is used for dependency injection, ensuring efficient and scalable management of dependencies. Coroutines are for asynchronous programming, enhancing responsiveness and performance by facilitating asynchronous calls.

## Dependency Injection
Hilt, a powerful dependency injection library for Android, is employed for dependency injection in this project. It simplifies the setup and management of dependencies, promoting modularity and testability within the application.

## Offline Support
The app leverages Room Database for offline functionality, enabling the storage of the last searched result locally. Users can retrieve and access this data even when offline, ensuring a seamless user experience.

## ViewModel Testing
In this project, ViewModel tests are implemented to verify the behavior and interactions of ViewModel components. These tests validate the correctness of ViewModel logic, state management and interaction with other components such as repositories.

## APK
A working APK of the project can be downloaded from [here](https://drive.google.com/file/d/1eh-YyjAf79tTUunrA9YoJMHBcaPJFXIr/view?usp=share_link).
