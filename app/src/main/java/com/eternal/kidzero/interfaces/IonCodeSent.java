package com.eternal.kidzero.interfaces;

import androidx.annotation.NonNull;

import com.google.firebase.auth.PhoneAuthProvider;

public interface IonCodeSent {
 void callback(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token);
}
