package com.eternal.kidzero.ui.fragments.parent;

import static com.eternal.kidzero.core.CallbackManager.addCallbak;
import static com.eternal.kidzero.core.CallbackManager.callCallbak;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eternal.kidzero.FDatabase;
import com.eternal.kidzero.FbCore;
import com.eternal.kidzero.ParentChilds;
import com.eternal.kidzero.R;
import com.eternal.kidzero.adapters.RcChildAdapter;
import com.eternal.kidzero.enums.Role;
import com.eternal.kidzero.models.ChildModel;
import com.eternal.kidzero.models.UserModel;
import com.eternal.kidzero.ui.fragments.BaseFrag;

import java.util.List;

public class ParentMainFrag extends BaseFrag {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_parent_main, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView childRc = view.findViewById(R.id.childRecycleView);
        childRc.setLayoutManager(new LinearLayoutManager(view.getContext()));

        RcChildAdapter adapter = new RcChildAdapter(this);
        childRc.setAdapter(adapter);

        adapter.updateItems();
        addCallbak(ParentChilds.class.getName(),(o) -> adapter.updateItems());


    }
}