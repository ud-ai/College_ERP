const admin = require('firebase-admin');

// Initialize Firebase Admin SDK for emulator
admin.initializeApp({
  projectId: 'college-erp-dev'
});

// Connect to Auth emulator
process.env.FIREBASE_AUTH_EMULATOR_HOST = '127.0.0.1:9098';

async function createAdminUser() {
  try {
    // Create admin user
    const userRecord = await admin.auth().createUser({
      uid: 'admin-uid',
      email: 'admin@college.edu',
      password: 'Admin@123',
      displayName: 'Admin User'
    });

    console.log('Admin user created:', userRecord.uid);

    // Set custom claims for admin role
    await admin.auth().setCustomUserClaims(userRecord.uid, { 
      roles: ['ADMIN'] 
    });

    console.log('Admin role assigned to user');
    process.exit(0);
  } catch (error) {
    console.error('Error creating admin user:', error);
    process.exit(1);
  }
}

createAdminUser();