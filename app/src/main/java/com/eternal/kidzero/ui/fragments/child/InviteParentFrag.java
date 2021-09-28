package com.eternal.kidzero.ui.fragments.child;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.eternal.kidzero.FbCore;
import com.eternal.kidzero.R;
import com.eternal.kidzero.ui.fragments.BaseFrag;
import com.eternal.kidzero.ui.helpers.AlertTextForamt;
import com.eternal.kidzero.ui.helpers.Network;
import com.hbb20.CountryCodePicker;

import br.com.sapereaude.maskedEditText.MaskedEditText;

public class InviteParentFrag extends BaseFrag {

    public final String TAG = "TAG";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_invite_parent, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CountryCodePicker countryCodePick = view.findViewById(R.id.countryCodePick);
        countryCodePick.setCountryForNameCode(Network.getCountryCode());

        MaskedEditText phoneNumbEditText = view.findViewById(R.id.phoneNum_MaskedEditText);
        setSelectEditText(phoneNumbEditText);

        view.findViewById(R.id.inviteParentDevice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!AlertTextForamt.inputIsEmpty(InviteParentFrag.this, phoneNumbEditText)) {
                    String phoneNum = "+" + countryCodePick.getSelectedCountryCode() + phoneNumbEditText.getText()
                            .toString()
                            .replace("-", "");

                    Log.d(TAG, phoneNum);

                    executeActionFrag(R.id.ActGoTo_ChildMainFrag);
                }
            }
        });
    }
}