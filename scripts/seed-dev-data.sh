#!/usr/bin/env bash
set -euo pipefail

PROJECT=college-erp-dev

if ! command -v firebase >/dev/null 2>&1; then
  echo "Install Firebase CLI: npm i -g firebase-tools"; exit 1; fi

echo "Seeding 10 students, 3 hostels, and sample payments into emulators..."

firebase firestore:delete --project $PROJECT --all-collections -y || true

for i in {1..10}; do
  id="S$i"
  firebase firestore:documents:set --project $PROJECT students/$id \
    studentId=$id fullName="Student $i" program="BSc" admissionDate=$(date +%s%3N) status=PENDING || true
done

for h in {1..3}; do
  hid="H$h"
  for r in {1..5}; do
    rid="R$h$r"
    firebase firestore:documents:set --project $PROJECT hostels/$hid/rooms/$rid \
      hostelId=$hid roomId=$rid roomNo="$rid" capacity=2 || true
  done
done

for i in {1..5}; do
  pid="P$i"
  sid="S$i"
  firebase firestore:documents:set --project $PROJECT payments/$pid \
    paymentId=$pid studentId=$sid amount=1000 date=$(date +%s%3N) method=CASH processedBy=admin receiptUrl=null || true
done

echo "Seed complete."


