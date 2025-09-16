const { initializeTestEnvironment } = require('@firebase/rules-unit-testing');
const fs = require('fs');

(async () => {
  const env = await initializeTestEnvironment({
    firestore: {
      rules: fs.readFileSync('../../firestore.rules', 'utf8'),
    },
    projectId: 'college-erp-dev',
  });
  const admin = env.unauthenticatedContext().firestore();
  // Minimal smoke: rules load without syntax errors
  console.log('Rules loaded.');
  await env.cleanup();
})();


