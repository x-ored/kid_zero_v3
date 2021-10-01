package com.eternal.kidzero.ui.fragments.child;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eternal.kidzero.R;
import com.eternal.kidzero.adapters.RcQuestAdapter;
import com.eternal.kidzero.ui.fragments.BaseFrag;

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

    }
}