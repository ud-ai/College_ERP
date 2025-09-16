# CollegeLightERP

A modular Android app (Kotlin + Jetpack Compose + Hilt) implementing a lightweight college ERP.

Work in progress. This repo is configured for Firebase Emulators and local development.

## Quick start

1. Open in Android Studio Ladybug or newer.
2. Add `app/google-services.json` from your Firebase project (placeholder project id: `college-erp-dev`).
3. Build: `./gradlew assembleDebug`.
4. Start Firebase emulators (Node 18+ required):
   - Install CLI: `npm i -g firebase-tools`
   - Run: `./emulator-setup.sh` (or `firebase emulators:start --only auth,firestore,storage --project college-erp-dev`)
5. Seed data (optional): `./scripts/seed-dev-data.sh`

## Firebase configuration
- Add `app/google-services.json` for your project. For local dev, emulators are used automatically in debug builds.
- Default dev admin (emulator): email `admin@college.edu`, password `Admin@123`. See docs below to seed and set custom claims.

## Emulator custom claims (RBAC)
Run in Emulator UI or Admin SDK to set claims on a user:
```js
// Using Firebase Admin SDK in a Node REPL connected to emulator
// admin.auth().setCustomUserClaims(uid, { roles: ['ADMIN'] })
```

## Firestore Security Rules
See `firestore.rules`. Tests coming in `security-rules` task.

## Tests
- Unit tests: `./gradlew test`
- Compose UI tests (instrumented): from Android Studio or `./gradlew connectedAndroidTest` with a running emulator

## Cloud Functions
Dev scaffold in `cloud-functions/`. Use the emulator to run locally.

## Modules
- `app` – Android application, DI wiring, navigation
- `data` – Room + Firebase repositories
- `domain` – Models and use cases
- `ui` – Shared Compose UI
