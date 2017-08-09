
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

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNFirebasePhoneAuth.sln` in `node_modules/react-native-firebase-phone-auth/windows/RNFirebasePhoneAuth.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Com.Reactlibrary.RNFirebasePhoneAuth;` to the usings at the top of the file
  - Add `new RNFirebasePhoneAuthPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNFirebasePhoneAuth from 'react-native-firebase-phone-auth';

// TODO: What to do with the module?
RNFirebasePhoneAuth;
```
  