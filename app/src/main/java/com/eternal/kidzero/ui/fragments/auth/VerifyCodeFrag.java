package com.eternal.kidzero.ui.fragments.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.eternal.kidzero.R;
import com.eternal.kidzero.ui.fragments.BaseFrag;

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

        view.findViewById(R.id.verifyCode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = verifyCode_EditText.getText().toString();

                if (!code.isEmpty()) {

                    // Sent verify code by verification token
                }
                else {
                    showAlertDialog(getString(R.string.empty_verify_code));
                }
            }
        });

        view.findViewById(R.id.resendCode_TextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Resend code by verification token
            }
        });
    }
}
