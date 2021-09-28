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
import com.eternal.kidzero.ui.helpers.AlertTextForamt;

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

        setSelectEditText(verifyCode_EditText);

        view.findViewById(R.id.verifyCode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!AlertTextForamt.inputIsEmpty(VerifyCodeFrag.this, verifyCode_EditText)) {
                    String code = verifyCode_EditText.getText().toString();
                    FbCore.getInstance().verifyPhoneNumberWithCode(code);
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
        fbCore.ionVerificationFailed = e -> {
            showAlertDialog(getString(R.string.verify_code_incorrect));
            verifyCode_EditText.setText("");
        };

        fbCore.iVerifySuccess = user -> {
          //  executeActionFrag(R.id.LoadingFrag);

           executeActionFrag(R.id.SwitchFrag);
        };
        fbCore.ionVerificationCodeFailed = e -> {
            showAlertDialog(getString(R.string.verify_code_incorrect));
            verifyCode_EditText.setText("");
        };


        fbCore.iTimerReplyCodeTick = millisUntilFinished -> {
            resendCodeTextView.setText(getString(R.string.resend_timeout) + " " + millisUntilFinished / 1000);
        };

        fbCore.iTimerReplyCodeFinish = () -> {
            resendCodeTextView.setText(getString(R.string.resend_code));
        };
    }
}
