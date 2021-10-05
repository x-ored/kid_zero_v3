package com.eternal.kidzero.adapters;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eternal.kidzero.R;
import com.eternal.kidzero.models.AppModel;
import com.eternal.kidzero.ui.fragments.BaseFrag;
import com.eternal.kidzero.ui.helpers.Network;
import com.eternal.kidzero.ui.helpers.TextFormatUtil;
import com.google.android.material.imageview.ShapeableImageView;

import org.json.JSONObject;

import java.util.ArrayList;

public class RcAppsAdapter extends RecyclerView.Adapter<RcAppsAdapter.viewHolder> {

    private ArrayList<AppModel> modelsArr = new ArrayList<>();
    private final String TAG = "RcAppsAdapter";
    public boolean isParent;
    public BaseFrag baseFrag;

    public RcAppsAdapter(BaseFrag baseFrag, boolean isParent) {
        this.isParent = isParent;
        this.baseFrag = baseFrag;
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        LinearLayout costContainer;
        LinearLayout timerContainer;

        CheckBox appCheckBox;
        ShapeableImageView appImage;

        TextView appName;
        TextView cost_TextView;
        TextView appTimeBlock_TextView;

        View view;

        public viewHolder(View itemView) {
            super(itemView);
            this.view = itemView;

            this.appCheckBox = view.findViewById(R.id.appCheckBox);
            this.appImage = view.findViewById(R.id.appImage);
            this.appName = view.findViewById(R.id.appName);

            this.cost_TextView = view.findViewById(R.id.cost_TextView);
            this.appTimeBlock_TextView = view.findViewById(R.id.appTimeBlock_TextView);

            this.costContainer = view.findViewById(R.id.costContainer);
            this.timerContainer = view.findViewById(R.id.timerContainer);
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
        if (appModel.drawable != null) {
            holder.appImage.setImageDrawable(appModel.drawable);
        }
        holder.appName.setText(appModel.name);
        if (isParent) {
            holder.appCheckBox.setVisibility(View.VISIBLE);
            holder.appCheckBox.setChecked(appModel.isBlocked);
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("appModel", appModel);
                baseFrag.executeActionFrag(R.id.ActGoTo_AppLimitFrag, bundle);
            }
        });

        if (appModel.cost > 0) {
            holder.costContainer.setVisibility(View.VISIBLE);
            holder.cost_TextView.setText(String.valueOf(appModel.cost));
        }
        if (appModel.timeToBlock > 0) {
            holder.timerContainer.setVisibility(View.VISIBLE);
            holder.appTimeBlock_TextView.setText(
                    TextFormatUtil.msToStr(appModel.timeToBlock)
            );
        }
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
