package com.eternal.kidzero.interfaces;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

public interface IonVerificationCodeFailed {
    void callback(FirebaseAuthInvalidCredentialsException e);
}
