# Poker Tracker

This is a Kotlin Multiplatform project, that was made with the [Decompose](https://github.com/arkivanov/decompose) template.

## Summary

Simple Compose Multiplatform App for tracking poker expenses.

Add Venues, Plan events at them, and then associate Expenses.

This project was inspired by the [sample project of Decompose](https://github.com/arkivanov/Decompose/tree/master/sample)
and the [Compose Multiplatform Template](https://github.com/JetBrains/compose-multiplatform-template).

## Motivation

This app was built purely for testing different altstore publication methods without copying someone else's work, so don't @ me about issues!

That said, in future updates I will flesh things out a bit, and make a real UI before I release to Google Play Store and App Store.

-----

## Decompose Project Structure

The project consists of multiple modules:

### `shared`

This is a Kotlin module that contains the shared logic of all platforms. This also includes the
Decompose implementation. 

### `compose-ui`

This is a Kotlin module that contains the UI written with Compose Multiplatform that is shared
across all platforms.

It depends on the `shared` module as it uses the component interfaces from Decompose.

### `app-android`

This is a Kotlin module that contains and builds the Android mobile application.

It makes use of the shared code from the modules `shared` and `compose-ui`.

### `app-ios-compose`

This is an Xcode project that builds an iOS mobile application with Compose UI.

It makes use of the shared code from the modules `shared` and `compose-ui`.

-----

## Dependencies

- [Kermit](https://github.com/touchlab/Kermit) for logging
- [jordond/MaterialKolor](https://github.com/jordond/MaterialKolor) + Material3 for UI
- [kizitonwose/Calendar](https://github.com/kizitonwose/Calendar) for planner view
- [sqldelight/sqldelight](https://github.com/sqldelight/sqldelight) for observable database
- [arkivanov/Decompose](https://github.com/arkivanov/Decompose) for navigation
- [arkivanov/essenty](https://github.com/arkivanov/Essenty) for lifecycle
- [FortAwesome/Font-Awesome](https://github.com/FortAwesome/Font-Awesome) for icons
- [InsertKoinIO/koin](https://github.com/InsertKoinIO/koin) for di

## Attributions

### Font Awesome

This project uses [Font Awesome Free](https://fontawesome.com) by Fonticons, Inc.

- Icons: [CC BY 4.0 License](https://creativecommons.org/licenses/by/4.0/)
- Fonts: [SIL OFL 1.1](https://scripts.sil.org/OFL)
- Code: [MIT License](https://opensource.org/licenses/MIT)

Copyright 2024 Fonticons, Inc.
