package com.eternal.kidzero.ui.fragments.child;

import static com.eternal.kidzero.core.CallbackManager.callCallbak;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eternal.kidzero.FDatabase;
import com.eternal.kidzero.FbCore;
import com.eternal.kidzero.R;
import com.eternal.kidzero.core.CallbackManager;
import com.eternal.kidzero.enums.Role;
import com.eternal.kidzero.models.ChildModel;
import com.eternal.kidzero.models.UserModel;
import com.eternal.kidzero.ui.fragments.BaseFrag;
import com.eternal.kidzero.ui.helpers.TextFormatUtil;
import com.eternal.kidzero.ui.helpers.Network;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

        view.findViewById(R.id.inviteParentDevice).setOnClickListener(v -> {

            if (!TextFormatUtil.inputIsEmpty(InviteParentFrag.this, phoneNumbEditText)) {
                String phoneNum = "+" + countryCodePick.getSelectedCountryCode() + phoneNumbEditText.getText()
                        .toString()
                        .replace("-", "");

                Log.d(TAG, phoneNum);
                FDatabase.getInstance().getCurentUserData(new CallbackManager.Callback((callback, args) -> {
                    if(args[0] instanceof ChildModel) {

                        ((ChildModel) args[0]).getInvite(phoneNum).sendInvite(surcess -> {
                            FDatabase.curentUser().AddConected(phoneNum).save().addOnSuccessListener(aVoid -> {
                                executeActionFrag(R.id.ActGoTo_LoadingFrag);
                            }).addOnFailureListener(e -> {
                                showAlertDialog(e.toString());
                            });

                        },canle -> {
                         showAlertDialog(canle);

                        },onTick -> {
                            // show Error

                        });


                    }
                }));
           //     executeActionFrag(R.id.ActGoTo_ChildMainFrag);
            }
        });
    }
}