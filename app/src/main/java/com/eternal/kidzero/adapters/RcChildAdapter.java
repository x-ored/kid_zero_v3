package com.eternal.kidzero.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.eternal.kidzero.FDatabase;
import com.eternal.kidzero.R;
import com.eternal.kidzero.models.ChildModel;
import com.eternal.kidzero.models.UserModel;
import com.eternal.kidzero.ui.fragments.BaseFrag;

import java.util.ArrayList;

public class RcChildAdapter extends RecyclerView.Adapter<RcChildAdapter.viewHolder> {

    private ArrayList<UserModel> modelsArr = new ArrayList<>();
    private final String TAG = "RcChildAdapter";
    private BaseFrag baseFrag;

    public RcChildAdapter(BaseFrag baseFrag) {
        this.baseFrag = baseFrag;
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView childName;
        View view;

        public viewHolder(View itemView) {
            super(itemView);

            this.childName = itemView.findViewById(R.id.childName);
            this.view = itemView;
        }
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_child_item, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        int pos = position;

        holder.childName.setText(modelsArr.get(position).getName());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, String.valueOf(pos));

                baseFrag.executeActionFrag(R.id.ActGoTo_ChildProfileFrag);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelsArr.size();
    }

    public void addItem(ChildModel childModel) {
        modelsArr.add(childModel);
        notifyDataSetChanged();
    }

    public void updateItems() {
        modelsArr.clear();
        modelsArr.addAll(FDatabase.getChildManager().getChilds());
        notifyDataSetChanged();
    }


}
