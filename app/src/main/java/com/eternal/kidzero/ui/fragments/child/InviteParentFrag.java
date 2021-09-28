package com.eternal.kidzero.ui.fragments.child;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.eternal.kidzero.FbCore;
import com.eternal.kidzero.R;
import com.eternal.kidzero.ui.fragments.BaseFrag;
import com.eternal.kidzero.ui.helpers.AlertTextForamt;

public class InviteParentFrag extends BaseFrag {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_invite_parent, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText phoneNum_EditText = view.findViewById(R.id.phoneNum_EditText);
        setSelectEditText(phoneNum_EditText);
        view.findViewById(R.id.inviteParentDevice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!AlertTextForamt.inputIsEmpty(InviteParentFrag.this, phoneNum_EditText)) {
                    executeActionFrag(R.id.ActGoTo_ChildMainFrag);
                }
            }
        });
    }
}