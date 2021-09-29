package com.eternal.kidzero.ui.fragments.child;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eternal.kidzero.FbCore;
import com.eternal.kidzero.ParentChilds;
import com.eternal.kidzero.R;
import com.eternal.kidzero.adapters.RcChildAdapter;
import com.eternal.kidzero.adapters.RcQuestAdapter;
import com.eternal.kidzero.core.CallbackManager;
import com.eternal.kidzero.models.QuestModel;
import com.eternal.kidzero.ui.fragments.BaseFrag;
import com.eternal.kidzero.ui.helpers.Network;
import com.hbb20.CountryCodePicker;

import br.com.sapereaude.maskedEditText.MaskedEditText;

public class QuestListFrag extends BaseFrag {

    public static String TAG = "QuestListFrag";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_quest_list, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView questRc = view.findViewById(R.id.questRecyclerView);
        questRc.setLayoutManager(new LinearLayoutManager(view.getContext()));

        RcQuestAdapter adapter = new RcQuestAdapter();
        questRc.setAdapter(adapter);
        adapter.addItem(new QuestModel("this is test quest one", 15));
        adapter.addItem(new QuestModel("this is test quest two, bind firebase please :)", 29));
    }
}