package com.eternal.kidzero.ui.fragments.parent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eternal.kidzero.R;
import com.eternal.kidzero.adapters.RcQuestAdapter;
import com.eternal.kidzero.models.QuestModel;
import com.eternal.kidzero.ui.fragments.BaseFrag;

public class ParentQuestListFrag extends BaseFrag {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_parent_quest_list, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        appBarInit("Quest's", true);

        RecyclerView questRc = view.findViewById(R.id.questRecyclerView);
        questRc.setLayoutManager(new LinearLayoutManager(view.getContext()));

        RcQuestAdapter adapter = new RcQuestAdapter();
        questRc.setAdapter(adapter);
        adapter.addItem(new QuestModel("this is test quest one", 15));
        adapter.addItem(new QuestModel("this is test quest two, bind firebase please :)", 29));

        view.findViewById(R.id.openNewQuestFrag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeActionFrag(R.id.ActGoTo_AddQuestFrag);
            }
        });
    }
}