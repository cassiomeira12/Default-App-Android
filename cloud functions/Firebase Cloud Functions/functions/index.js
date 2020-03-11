// The Cloud Functions for Firebase SDK to create Cloud Functions and setup triggers.
const functions = require('firebase-functions');

// The Firebase Admin SDK to access the Firebase Realtime Database.
const admin = require('firebase-admin');
admin.initializeApp();

// exports.createNotification = functions.https.onRequest(async (req, res) => {
//     const original = req.query.text;
//     const doc = admin.firestore().doc('/notifications/{id}')

//     doc.set({
//         title: original
//     })


//     const snapshot = await admin.firestore
//         .document('/notifications/{id}')
//         .onCreate((snap, context) => {
            
//         })
//     const snapshot = await admin.firestore().ref('/notifications').push({
//         original: original
//     });
//     res.redirect(303, snapshot.to;
// });

exports.createPushNotification = functions.firestore
    .document('notifications/{idNotification}')
    .onCreate(async (snapshot) => {
        
        const token = snapshot.get('token')
        const topic = snapshot.get('topic')

        const imgURL = snapshot.get('avatarURL')
        const title = snapshot.get('title')
        const body = snapshot.get('message')

        if (token !== null) {
            const message = {
                notification: {
                    image: imgURL,
                    title: title,
                    body: body
                },
                token: token
            };
            return admin.messaging().send(message)
        }

        if (topic !== null) {
            const message = {
                notification: {
                    image: imgURL,
                    title: title,
                    body: body
                },
                topic: topic
            };
            return admin.messaging().send(message);
        }
    });

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });
/*
{ "notification": {
    "title": "Notification title",
    "body": "Notification message",
    "sound": "default",
    "color": "#53c4bc",
    "click_action": "MY_BOOK",
    "icon": "ic_launcher"
 },
 "data": {
     "main_picture": "URL_OF_THE_IMAGE"  
 },
 "to" : "USER_FCM_TOKEN"
}
*/
