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
import com.eternal.kidzero.adapters.RcChildAdapter;
import com.eternal.kidzero.models.ChildModel;
import com.eternal.kidzero.ui.fragments.BaseFrag;

public class ChildListFrag extends BaseFrag {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_child_list, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView childRc = view.findViewById(R.id.childRecycleView);
        childRc.setLayoutManager(new LinearLayoutManager(view.getContext()));

        RcChildAdapter adapter = new RcChildAdapter(this);
        childRc.setAdapter(adapter);

        for (int i = 0; i < 15; i++) {
            adapter.addItem(new ChildModel("My item " + i));
        }
    }
}
