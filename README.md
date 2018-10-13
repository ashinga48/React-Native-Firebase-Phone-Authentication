

# react-native-firebase-phone-auth

## Getting started

`$ npm install react-native-firebase-phone-auth --save`

### Mostly automatic installation

`$ react-native link react-native-firebase-phone-auth`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-firebase-phone-auth` and add `RNFirebasePhoneAuth.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNFirebasePhoneAuth.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNFirebasePhoneAuthPackage;` to the imports at the top of the file
  - Add `new RNFirebasePhoneAuthPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-firebase-phone-auth'
  	project(':react-native-firebase-phone-auth').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-firebase-phone-auth/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-firebase-phone-auth')
  	```

## Usage

1. Import DeviceEventEmitter in react-native
2. Import RNFirebasePhoneAuth from 'react-native-firebase-phone-auth'

    ```javascript
    import {
        //...
        DeviceEventEmitter,
    } from 'react-native';

    import RNFirebasePhoneAuth from 'react-native-firebase-phone-auth';
    ```
3. (Optional) initialize Firebase
    ```
        /*
            All info appId, projectId, appKey, databaseURL
            can be found in google-services.json file which you can download
            from firebase console in the website
        */
        RNFirebasePhoneauth.initFirebase(
            appId,
            projectId,
            appKey,
            databaseURL,
            (resp)=>{ console.log(resp) },
            (error)=>{
                //alert(error); console.log(error);
                },
        );
    ```
4. Add event listener for OTPStatus in componentDidMount
    ```
        componentDidMount() {
             this.initFirebase(); // if needed
             this.listenToOTP();
         }

         listenToOTP(){

             DeviceEventEmitter.addListener('OTPStatus', (data) => {

                 switch(data.CODE){
                    case "SENT":
                            //SMS Sent
                        break;
                    case "VERIFIED":
                             //SMS Received & read by google play services
                              let authData = {
                                  code : data.OTPNumber,
                                  verificationId : data.verificationId
                              }
                              firebase.auth().signInWithPhoneAuth(
                                 authData,
                                 (success)=>{
                                     //on Successful Login
                                 },
                                 (error)=>{
                                     //on Error
                                 });
                         break;
                    case "ERROR":
                            //error sending SMS
                        break;
                 }

                 if(data.CODE == "ERROR")
                     {
                     }

             });


         }
    ```

5. Lastly Send OTP to a phoneNumber
    ```
        let phoneNumber = "+61412341234";
        RNFirebasePhoneauth.sendOTP(phoneNumber);
    ```

    **That's it!!!**

// TODO: Android & iOS Firebase link
RNFirebasePhoneAuth;


## Common Issues on Build / Integration

1 : Failed to resolve: com.google.firebase:firebase-core:X.X.X

    follow this link, it worked for me
    https://stackoverflow.com/questions/37310188/failed-to-resolve-com-google-firebasefirebase-core9-0-0

2 : Did you setup Firebase for Android & iOS properly??

    https://support.google.com/firebase/answer/7000104?hl=en

    OR

    - ANDROID issue #1 - Add firebase configuration file to the root of the project in android
        https://support.google.com/firebase/answer/7015592

    - ANDROID issue #2 - build.gradle config did run perfectly with this configuration

        ```
        compileSdkVersion 25
            buildToolsVersion "25.0.2"

            defaultConfig {
                applicationId "com.ivu"
                minSdkVersion 16
                targetSdkVersion 26


    2. iOS
