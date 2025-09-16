#!/usr/bin/env bash
set -euo pipefail

echo "Starting Firebase Emulators (Auth, Firestore, Storage)"
export FIREBASE_EMULATOR_HUB=127.0.0.1:4400
export FIRESTORE_EMULATOR_HOST=127.0.0.1:8080
export FIREBASE_AUTH_EMULATOR_HOST=127.0.0.1:9099
export FIREBASE_STORAGE_EMULATOR_HOST=127.0.0.1:9199

if ! command -v firebase >/dev/null 2>&1; then
  echo "Firebase CLI not found. Install: npm i -g firebase-tools"
  exit 1
fi

firebase emulators:start --only auth,firestore,storage --project college-erp-dev


