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
    public String last_phoneNumber;

    public IonCodeSent ionCodeSent;
    public IonVerificationCompleted ionVerificationCompleted;
    public IonVerificationFailed ionVerificationFailed;
    public IVerifySuccess iVerifySuccess;
    public ITimerReplyCodeTick iTimerReplyCodeTick;
    public ITimerReplyCodeFinish iTimerReplyCodeFinish;

    public long timeout = 60l;

    CountDownTimer yourCountDownTimer;

    private PhoneAuthProvider.ForceResendingToken token;
    private FirebaseAuth fbAuth;
    private FirebaseUser fbUser;
    private PhoneAuthCredential credential;

    private String verificationId;
    public PhoneAuthProvider.OnVerificationStateChangedCallbacks  mCallbacks;

    public FbCore() {

        yourCountDownTimer = null;
        iTimerReplyCodeTick = millisUntilFinished -> {};
        iTimerReplyCodeFinish = ()->{};
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

                FbCore.getInstance().verificationId = verificationId;
                FbCore.getInstance().token = token;
                receiveCodeTimer();
                ionCodeSent.callback(verificationId,token);

            }
        };
    }

    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        getAuth().signInWithCredential(credential)
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
                        }
                        else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    public void receiveCodeTimer(){

        if (yourCountDownTimer == null) {
            yourCountDownTimer = new CountDownTimer(timeout*1000, 1000) {

                public void onTick(long millisUntilFinished) {  try {
                    iTimerReplyCodeTick.callback(millisUntilFinished);
                }catch (Exception e){}

                }
                public void onFinish() {
                    yourCountDownTimer = null;
                    try {
                        iTimerReplyCodeFinish.callback();
                    }catch (Exception e){}

                }
            }.start();
        }
    }

    public FirebaseAuth getAuth(){

        if (FbCore.getInstance().fbAuth == null) {
            FbCore.getInstance().fbAuth = FirebaseAuth.getInstance();
        }
        return FbCore.getInstance().fbAuth;
    }

    public FirebaseUser set_user(FirebaseUser user){
        return FbCore.getInstance().fbUser = user;
    }

    public FirebaseUser get_user() {

        if (fbUser == null) {
            set_user(getAuth().getCurrentUser());
        }
        return fbUser;
    }

    public void sendAuthCode(String phoneNumber){
        last_phoneNumber = phoneNumber;

        PhoneAuthProvider.verifyPhoneNumber(PhoneAuthOptions.newBuilder(getAuth())
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
        if (yourCountDownTimer==null) {

            PhoneAuthProvider.verifyPhoneNumber(PhoneAuthOptions.newBuilder(getAuth())
                    .setPhoneNumber(last_phoneNumber)       // Phone number to verify
                    .setTimeout(timeout, TimeUnit.SECONDS) // Timeout and unit
                    .setActivity(MainActivity.Ref)                 // Activity (for callback binding)
                    .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                    .setForceResendingToken(token)     // ForceResendingToken from callbacks
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
