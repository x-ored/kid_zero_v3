package com.eternal.kidzero.ui.fragments.auth;

import static com.eternal.kidzero.core.CallbackManager.addCallbak;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.eternal.kidzero.FbCore;
import com.eternal.kidzero.R;
import com.eternal.kidzero.core.CallbackManager;
import com.eternal.kidzero.ui.fragments.BaseFrag;
import com.eternal.kidzero.ui.helpers.TextFormatUtil;
import com.google.firebase.auth.PhoneAuthCredential;

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

        setSelectEditText(verifyCode_EditText);

        view.findViewById(R.id.verifyCode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextFormatUtil.inputIsEmpty(VerifyCodeFrag.this, verifyCode_EditText)) {
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



    }


    @Override
    public void onStart() {
        super.onStart();
        addCallbak("ionVerificationCompleted",(callback, objects) ->  FbCore.getInstance().signInWithPhoneAuthCredential((PhoneAuthCredential)objects[0]));
        addCallbak("ionVerificationFailed",(callback, objects) -> {
            showAlertDialog(getString(R.string.verify_code_incorrect));
            ((EditText)getFragView().findViewById(R.id.verifyCode_EditText)).setText("");
        });

        addCallbak("iVerifySuccess",(callback, objects) -> {
            executeActionFrag(R.id.SwitchFrag);
            callback.dispose();
        });
        addCallbak("ionVerificationCodeFailed",(callback, objects) -> {
            showAlertDialog(getString(R.string.verify_code_incorrect));
            ((EditText)getFragView().findViewById(R.id.verifyCode_EditText)).setText("");
        });
        addCallbak("iTimerReplyCodeTick",(callback, objects) -> ((TextView)getFragView().findViewById(R.id.resendCode_TextView)).setText(getString(R.string.resend_timeout) + " " + (long)objects[0] / 1000));
        addCallbak("iTimerReplyCodeFinish",(callback, objects) -> ((TextView)getFragView().findViewById(R.id.resendCode_TextView)).setText(getString(R.string.resend_code)));

    }

    @Override
    public void onStop() {
        super.onStop();
        CallbackManager.removeCallbacks();
    }
}
