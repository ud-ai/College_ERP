# CollegeLightERP

A modular Android app (Kotlin + Jetpack Compose + Hilt) implementing a lightweight college ERP.

Work in progress. See tasks in repository issues and commits.

## Quick start

1. Open in Android Studio Ladybug or newer.
2. Add `app/google-services.json` from your Firebase project (placeholder project id: `college-erp-dev`).
3. Build: `./gradlew assembleDebug`.

## Modules
- `app` – Android application, DI wiring, navigation
- `data` – Room + Firebase repositories
- `domain` – Models and use cases
- `ui` – Shared Compose UI
