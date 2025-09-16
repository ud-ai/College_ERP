const { initializeTestEnvironment, assertFails, assertSucceeds } = require('@firebase/rules-unit-testing');
const fs = require('fs');

(async () => {
  const rules = fs.readFileSync('../../firestore.rules', 'utf8');
  const env = await initializeTestEnvironment({ projectId: 'college-erp-dev', firestore: { rules } });

  const adminCtx = env.authenticatedContext('admin', { roles: ['ADMIN'] });
  const staffCtx = env.authenticatedContext('staff', { roles: ['STAFF'] });
  const studentCtx = env.authenticatedContext('stu1', { roles: ['STUDENT'] });

  await assertSucceeds(adminCtx.firestore().collection('students').doc('S1').get());
  await assertSucceeds(staffCtx.firestore().collection('students').doc('S1').get());
  await assertFails(studentCtx.firestore().collection('students').doc('S2').get());

  await env.cleanup();
})();


