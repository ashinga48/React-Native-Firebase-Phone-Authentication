
package com.reactlibrary;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

public class RNFirebasePhoneAuthModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private FirebaseAuth mAuth;
  private String mVerificationId = null;

  public RNFirebasePhoneAuthModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNFirebasePhoneAuth";
  }



  private void sendEvent(String eventName, @Nullable WritableMap params) {
    this.reactContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit(eventName, params);
  }

  @ReactMethod
  public void initFirebase(
          String ProjectId,
          String AppId,
          String APIKey,
          String DatabaseURL,
          Callback successCallback,
          Callback errorCallback
  ) {
    try {
      Log.d("TONUSER","Init FB ");

      /*
      FirebaseOptions options = new FirebaseOptions.Builder()
              .setProjectId(ProjectId)
              .setApplicationId(AppId) // Required for Analytics.
              .setApiKey(APIKey) // Required for Auth.
              .setDatabaseUrl(DatabaseURL) // Required for RTDB.
              .build();
      FirebaseApp.initializeApp(reactContext.getApplicationContext(), options);
      */

      FirebaseApp.initializeApp(reactContext.getApplicationContext());
      Log.d("TONUSER","Init FB 2");
//      Log.d("RESUME", FirebaseApp.getInstance() == null ? "Not initiated" : "Yes initiated");

      //FirebaseApp.initializeApp(reactContext.getApplicationContext());
//      mAuth = FirebaseAuth.getInstance();
//      FirebaseUser currentUser = mAuth.getCurrentUser();
//      WritableMap params = Arguments.createMap();
//      params.putString("user", currentUser != null ? currentUser.getUid() : null);
//      successCallback.invoke(params);
    } catch (Exception e) {


      Log.d("TONUSER","ERROR "+e.getMessage());
      errorCallback.invoke(e.getMessage());
    }
  }

  @ReactMethod
  public void getCurrentUser(
          Callback successCallback,
          Callback errorCallback
  ) {
    try {


      String something = "";

      PackageInfo info;
      try {

        info = reactContext.getApplicationContext().getPackageManager().getPackageInfo(
                "com.touchon.user", PackageManager.GET_SIGNATURES);
        for (Signature signature : info.signatures) {
//          MessageDigest md;
//          md = MessageDigest.getInstance("SHA");
//          md.update(signature.toByteArray());
//          something = new String(Base64.encode(md.digest(), 0));
//          Log.e("Hash key", something);
//          System.out.println("Hash key" + something);

          MessageDigest md = MessageDigest.getInstance("SHA");
          md.update(signature.toByteArray());
          Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
          something = Base64.encodeToString(md.digest(), Base64.DEFAULT);
        }

      } catch (PackageManager.NameNotFoundException e1) {
        Log.e("name not found", e1.toString());
        something = e1.toString();
      } catch (NoSuchAlgorithmException e2) {
        Log.e("no such an algorithm", e2.toString());
        something = e2.toString();
      } catch (Exception e3) {
        Log.e("exception", e3.toString());
        something = e3.toString();
      }


      //FirebaseApp.initializeApp(reactContext.getApplicationContext());
      mAuth = FirebaseAuth.getInstance();
      FirebaseUser currentUser = mAuth.getCurrentUser();
      WritableMap params = Arguments.createMap();
      params.putString("user", "ATA"+something);
      successCallback.invoke(params);
    } catch (Exception e) {
      errorCallback.invoke(e.getMessage());
    }
  }


  @ReactMethod
  public void sendOTP(String phoneNumber,
                      final Callback successCallback,
                      final Callback errorCallback){

    Log.d("TONUSER","NUM "+phoneNumber);
    final String TAG = "OTP";
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

      @Override
      public void onVerificationCompleted(PhoneAuthCredential credential) {
        // This callback will be invoked in two situations:
        // 1 - Instant verification. In some cases the phone number can be instantly
        //     verified without needing to send or enter a verification code.
        // 2 - Auto-retrieval. On some devices Google Play services can automatically
        //     detect the incoming verification SMS and perform verificaiton without
        //     user action.
        Log.d(TAG, "onVerificationCompleted:" + credential);

        WritableMap params = Arguments.createMap();
        params.putString("CODE", "VERIFIED");
        params.putString("OTPNumber", credential.getSmsCode());
        if(mVerificationId != null)
          params.putString("verificationId", mVerificationId);

//                successCallback.invoke(params);
        sendEvent("OTPStatus", params);
        //signInWithPhoneAuthCredential(credential);
      }

      @Override
      public void onVerificationFailed(FirebaseException e) {
        // This callback is invoked in an invalid request for verification is made,
        // for instance if the the phone number format is not valid.
        Log.w(TAG, "onVerificationFailed", e);

        WritableMap params = Arguments.createMap();
        params.putString("CODE", "ERROR");
        params.putString("message", e.toString());
        sendEvent("OTPStatus", params);
//                errorCallback.invoke(params);

        if (e instanceof FirebaseAuthInvalidCredentialsException) {
          // Invalid request
          // ...
        } else if (e instanceof FirebaseTooManyRequestsException) {
          // The SMS quota for the project has been exceeded
          // ...
        }

        // Show a message and update the UI
        // ...
      }

      @Override
      public void onCodeSent(String verificationId,
                             PhoneAuthProvider.ForceResendingToken token) {
        // The SMS verification code has been sent to the provided phone number, we
        // now need to ask the user to enter the code and then construct a credential
        // by combining the code with a verification ID.
        Log.d(TAG, "onCodeSent:" + verificationId);

        // Save verification ID and resending token so we can use them later
        mVerificationId = verificationId;
//                mResendToken = token;


        WritableMap params = Arguments.createMap();
        params.putString("CODE", "SENT");
        params.putString("verificationId", ""+verificationId);
        sendEvent("OTPStatus", params);

//                successCallback.invoke(params);
        // ...
      }
    };

    PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,
            60,
            TimeUnit.SECONDS,
            reactContext.getCurrentActivity(),
            mCallbacks);

  }

  @ReactMethod
  public void test(String url) {
    Log.d("TESTEVENT", "TEST EVENT CALLED");
    WritableMap params = Arguments.createMap();
    params.putString("data", "HEHE"+url);
    sendEvent("test", params);
  }

  @ReactMethod
  public void test2(
          Callback successCallback,
          Callback errorCallback
  ) {
    try {

      successCallback.invoke("Something with new");
    } catch (Exception e) {
      errorCallback.invoke(e.getMessage());
    }
  }

}
