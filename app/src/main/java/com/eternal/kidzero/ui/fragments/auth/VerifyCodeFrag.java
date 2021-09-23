package com.eternal.kidzero.ui.fragments.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.eternal.kidzero.FbCore;
import com.eternal.kidzero.R;
import com.eternal.kidzero.ui.fragments.BaseFrag;

import org.w3c.dom.Text;

public class VerifyCodeFrag extends BaseFrag {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_verify_code, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText verifyCode_EditText = view.findViewById(R.id.verifyCode_EditText);
        TextView resendCodeTextView = view.findViewById(R.id.resendCode_TextView);

        FbCore fbCore = FbCore.getInstance();

        view.findViewById(R.id.verifyCode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = verifyCode_EditText.getText().toString();

                if (!code.isEmpty()) {

                 FbCore.getInstance().verifyPhoneNumberWithCode(code);
                }
                else {
                    showAlertDialog(getString(R.string.empty_verify_code));
                }
            }
        });

        view.findViewById(R.id.resendCode_TextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               FbCore.getInstance().resendVerificationCode();
            }
        });

        fbCore.ionVerificationCompleted = credential -> {
            FbCore.getInstance().signInWithPhoneAuthCredential(credential);
        };

        fbCore.iVerifySuccess = user -> {

        };

        fbCore.iTimerReplyCodeTick = millisUntilFinished -> {
            resendCodeTextView.setText("Wait "+millisUntilFinished);
        };

        fbCore.iTimerReplyCodeFinish = ()->{
            resendCodeTextView.setText(getString(R.string.resend_code));
        };
    }
}
