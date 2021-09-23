package com.eternal.kidzero.interfaces;

import com.google.firebase.auth.PhoneAuthCredential;

public interface IonVerificationCompleted {

    void callback(PhoneAuthCredential credential);
}
