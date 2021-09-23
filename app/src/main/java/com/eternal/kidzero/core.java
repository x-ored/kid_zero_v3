package com.eternal.kidzero;

import android.app.Activity;
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

public final class core {
    private static core instance;
    public String TAG;
    public String value;
    public IonCodeSent ionCodeSent;
    public IonVerificationCompleted ionVerificationCompleted;
    public IonVerificationFailed ionVerificationFailed;
    private FirebaseAuth f_auth;
    public core(){



        this.TAG = "core";
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
    PhoneAuthProvider.OnVerificationStateChangedCallbacks  mCallbacks;
    public FirebaseAuth getF_auth(){
        if (core.getInstance().f_auth == null) {
            core.getInstance().f_auth = FirebaseAuth.getInstance();
        }
        return core.getInstance().f_auth;
    }
    public void send_auth_code(Activity act,String phoneNumber){

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(core.getInstance().f_auth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(act)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    public static core getInstance() {
        if (instance == null) {
            instance = new core();
        }
        return instance;
    }
}
