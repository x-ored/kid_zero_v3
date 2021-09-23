package com.eternal.kidzero.ui.fragments.auth;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.eternal.kidzero.R;
import com.eternal.kidzero.FbCore;
import com.eternal.kidzero.ui.fragments.BaseFrag;

import br.com.sapereaude.maskedEditText.MaskedEditText;

public class AuthFrag extends BaseFrag {

    public static String TAG = "AuthFrag";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_auth, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaskedEditText phoneNumbEditText = view.findViewById(R.id.phoneNum_MaskedEditText);

        view.findViewById(R.id.LoginWithPhone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNum = phoneNumbEditText.getText()
                        .toString()
                        .replace("(", "")
                        .replace(")", "")
                        .replace("-", "");

                Log.d(TAG, phoneNum);

                if (!phoneNum.isEmpty()) {
                    FbCore.getInstance().send_auth_code(phoneNum);
                }
                else {
                    showAlertDialog(getString(R.string.empty_phone));
                }
            }
        });

        FbCore sing = FbCore.getInstance();

        sing.ionCodeSent = (verificationId, token) -> {
            executeActionFrag(R.id.ActGoTo_VerifyCodeFrag);
        };

        sing.ionVerificationFailed = e -> {
            showAlertDialog(e.toString());
        };
    }
}