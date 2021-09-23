package com.eternal.kidzero;

import android.util.Log;

import androidx.annotation.NonNull;

import com.eternal.kidzero.interfaces.IonCodeSent;
import com.eternal.kidzero.interfaces.IonVerificationCompleted;
import com.eternal.kidzero.interfaces.IonVerificationFailed;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;

import java.util.concurrent.TimeUnit;

public final class FbCore {

    private static FbCore instance;
    public String TAG = "core";

    public IonCodeSent ionCodeSent;
    public IonVerificationCompleted ionVerificationCompleted;
    public IonVerificationFailed ionVerificationFailed;

    private FirebaseAuth f_auth;
    public PhoneAuthProvider.OnVerificationStateChangedCallbacks  mCallbacks;

    public FbCore(){

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                ionVerificationCompleted.callback(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                ionVerificationFailed.callback(e);
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,@NonNull PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);
                ionCodeSent.callback(verificationId,token);
            }
        };
    }

    public FirebaseAuth get_fAuth(){

        if (FbCore.getInstance().f_auth == null) {
            FbCore.getInstance().f_auth = FirebaseAuth.getInstance();
        }
        return FbCore.getInstance().f_auth;
    }

    public void send_auth_code(String phoneNumber){

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(get_fAuth())
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(MainActivity.Ref)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    public static FbCore getInstance() {

        if (instance == null) {
            instance = new FbCore();
        }
        return instance;
    }
}
