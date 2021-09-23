package com.eternal.kidzero.ui.fragments.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.eternal.kidzero.R;
import com.eternal.kidzero.ui.fragments.BaseFrag;

import br.com.sapereaude.maskedEditText.MaskedEditText;

public class AuthFrag extends BaseFrag {

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
                String phoneNum = phoneNumbEditText.getText().toString();

                if (!phoneNum.isEmpty()) {
                    executeActionFrag(R.id.ActGoTo_VerifyCodeFrag);
                }
                else {
                    showAlertDialog(getString(R.string.empty_phone));
                }
            }
        });
    }
}