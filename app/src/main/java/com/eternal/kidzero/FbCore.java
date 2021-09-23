package com.eternal.kidzero;

import android.os.CountDownTimer;
import android.util.Log;

import androidx.annotation.NonNull;

import com.eternal.kidzero.interfaces.ITimerReplyCodeFinish;
import com.eternal.kidzero.interfaces.ITimerReplyCodeTick;
import com.eternal.kidzero.interfaces.IVerifySuccess;
import com.eternal.kidzero.interfaces.IonCodeSent;
import com.eternal.kidzero.interfaces.IonVerificationCompleted;
import com.eternal.kidzero.interfaces.IonVerificationFailed;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
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
    public IVerifySuccess iVerifySuccess;
    public ITimerReplyCodeTick iTimerReplyCodeTick;
    public ITimerReplyCodeFinish iTimerReplyCodeFinish;
    public String last_phoneNumber;
    CountDownTimer yourCountDownTimer;
    public long timeout = 60l;
    private PhoneAuthProvider.ForceResendingToken token;
    private FirebaseAuth f_auth;
    private FirebaseUser f_user;
    private PhoneAuthCredential credential;

    private String verificationId;
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
                ReciveCodeTimer();
                FbCore.getInstance().verificationId = verificationId;
                FbCore.getInstance().token = token;
                ionCodeSent.callback(verificationId,token);
            }
        };
    }


    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        get_fAuth().signInWithCredential(credential)
                .addOnCompleteListener(MainActivity.Ref, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            set_user(user);
                            iVerifySuccess.callback(user);
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    public void ReciveCodeTimer(){
        if (FbCore.getInstance().yourCountDownTimer == null) {
            FbCore.getInstance().yourCountDownTimer = new CountDownTimer(FbCore.getInstance().timeout, 1000) {
                public void onTick(long millisUntilFinished) {
                    iTimerReplyCodeTick.callback(millisUntilFinished);
                }
                public void onFinish() {
                    FbCore.getInstance().yourCountDownTimer = null;
                    iTimerReplyCodeFinish.callback();
                }

            }.start();
        }
    }

    public FirebaseAuth get_fAuth(){

        if (FbCore.getInstance().f_auth == null) {
            FbCore.getInstance().f_auth = FirebaseAuth.getInstance();
        }
        return FbCore.getInstance().f_auth;
    }

    public FirebaseUser get_user(){

        if (FbCore.getInstance().f_user == null) {
            set_user(get_fAuth().getCurrentUser());
        }
        return FbCore.getInstance().f_user;
    }

    public FirebaseUser set_user(FirebaseUser user){
        return FbCore.getInstance().f_user = user;
    }

    public void send_auth_code(String phoneNumber){
        FbCore.getInstance().last_phoneNumber = phoneNumber;

        PhoneAuthProvider.verifyPhoneNumber(PhoneAuthOptions.newBuilder(get_fAuth())
                .setPhoneNumber(phoneNumber)       // Phone number to verify
                .setTimeout(timeout, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(MainActivity.Ref)                 // Activity (for callback binding)
                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                .build());
    }



    public void verifyPhoneNumberWithCode(String code) {
        FbCore.getInstance().credential = PhoneAuthProvider.getCredential(FbCore.getInstance().verificationId, code);
        signInWithPhoneAuthCredential(FbCore.getInstance().credential);

    }


    public void resendVerificationCode() {
        if (FbCore.getInstance().yourCountDownTimer == null) {

            PhoneAuthProvider.verifyPhoneNumber(PhoneAuthOptions.newBuilder(FbCore.getInstance().get_fAuth())
                    .setPhoneNumber(FbCore.getInstance().last_phoneNumber)       // Phone number to verify
                    .setTimeout(timeout, TimeUnit.SECONDS) // Timeout and unit
                    .setActivity(MainActivity.Ref)                 // Activity (for callback binding)
                    .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                    .setForceResendingToken(FbCore.getInstance().token)     // ForceResendingToken from callbacks
                    .build());
        }
    }
    public static FbCore getInstance() {

        if (instance == null) {
            instance = new FbCore();
        }
        return instance;
    }
}
