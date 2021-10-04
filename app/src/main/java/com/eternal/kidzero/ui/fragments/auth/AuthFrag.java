package com.eternal.kidzero.ui.fragments.auth;

import static com.eternal.kidzero.core.CallbackManager.addCallbak;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.eternal.kidzero.R;
import com.eternal.kidzero.FbCore;
import com.eternal.kidzero.core.CallbackManager;
import com.eternal.kidzero.ui.fragments.BaseFrag;
import com.eternal.kidzero.ui.helpers.Network;
import com.google.firebase.FirebaseException;
import com.hbb20.CountryCodePicker;

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

        FbCore fbCore = FbCore.getInstance();

        CountryCodePicker countryCodePick = view.findViewById(R.id.countryCodePick);
        countryCodePick.setCountryForNameCode(Network.getCountryCode());

        MaskedEditText phoneNumbEditText = view.findViewById(R.id.phoneNum_MaskedEditText);
        setSelectEditText(phoneNumbEditText);

        view.findViewById(R.id.LoginWithPhone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNum = "+" + countryCodePick.getSelectedCountryCode() + phoneNumbEditText.getText()
                        .toString()
                        .replace("-", "");

                Log.d(TAG, phoneNum);

                fbCore.sendAuthCode(phoneNum);
            }
        });



    }

    @Override
    public void onStart() {
        super.onStart();
        addCallbak("ionCodeSent",(callback, objects) -> {
            executeActionFrag(R.id.ActGoTo_VerifyCodeFrag);
            callback.dispose();
        });
        addCallbak("ionVerificationFailed",(callback, objects) -> {
            showAlertDialog(((FirebaseException)objects[0]).toString());
            (( MaskedEditText )getFragView().findViewById(R.id.phoneNum_MaskedEditText)).setText("");
        });


    }

    @Override
    public void onStop() {
        super.onStop();
        CallbackManager.removeCallbacks();
    }
}