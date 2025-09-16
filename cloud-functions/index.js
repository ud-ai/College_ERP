const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

// Sends a log (simulating email) when a new payment is created and ensures receiptUrl exists
exports.onPaymentCreated = functions.firestore
  .document('payments/{paymentId}')
  .onCreate(async (snap, context) => {
    const payment = snap.data();
    console.log(`Payment created: ${context.params.paymentId}`, payment);
    if (!payment.receiptUrl) {
      await snap.ref.update({ processed: true });
    }
    return null;
  });


