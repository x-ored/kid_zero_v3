package com.eternal.kidzero.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eternal.kidzero.App;
import com.eternal.kidzero.R;
import com.eternal.kidzero.models.AppModel;
import com.eternal.kidzero.models.QuestModel;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class RcAppsAdapter extends RecyclerView.Adapter<RcAppsAdapter.viewHolder> {

    private ArrayList<AppModel> modelsArr = new ArrayList<>();
    private final String TAG = "RcAppsAdapter";
    public boolean isParent = false;

    public RcAppsAdapter(boolean isParent) {
        this.isParent = isParent;
    }

    public RcAppsAdapter() { }

    public class viewHolder extends RecyclerView.ViewHolder {

        CheckBox appCheckBox;
        ShapeableImageView appImage;
        TextView appName;
        View view;

        public viewHolder(View itemView) {
            super(itemView);
            this.view = itemView;

            this.appCheckBox = view.findViewById(R.id.appCheckBox);
            this.appImage = view.findViewById(R.id.appImage);
            this.appName = view.findViewById(R.id.appName);
        }
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_app_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        AppModel appModel = modelsArr.get(position);
        holder.appImage.setImageDrawable(appModel.drawable);
        holder.appName.setText(appModel.name);
        if (isParent) {
            holder.appCheckBox.setVisibility(View.VISIBLE);
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "->" + appModel.packageName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelsArr.size();
    }

    public void setArray(ArrayList<AppModel> appModels) {
        modelsArr = appModels;
        notifyDataSetChanged();
    }
}
