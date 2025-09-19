@echo off
echo Starting Firebase emulators...
start /B firebase emulators:start --only auth,firestore,storage --project college-erp-dev

echo Waiting for emulators to start...
timeout /t 10 /nobreak > nul

echo Creating admin user...
node create-admin-user.js

echo Setup complete. Admin user: admin@college.edu / Admin@123