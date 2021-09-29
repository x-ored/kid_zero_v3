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
import com.eternal.kidzero.models.QuestModel;
import com.eternal.kidzero.models.UserModel;

import java.util.ArrayList;

public class RcQuestAdapter extends RecyclerView.Adapter<RcQuestAdapter.viewHolder> {

    private ArrayList<QuestModel> modelsArr = new ArrayList<>();
    private final String TAG = "RcQuestAdapter";

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView questText_EditText;
        TextView questReward_EditText;

        View view;

        public viewHolder(View itemView) {
            super(itemView);

            this.questText_EditText = itemView.findViewById(R.id.questText_EditText);
            this.questReward_EditText = itemView.findViewById(R.id.questReward_EditText);
            this.view = itemView;
        }
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_quest_item, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.questText_EditText.setText(modelsArr.get(position).questText);
        holder.questReward_EditText.setText(String.valueOf(modelsArr.get(position).reward));
    }

    @Override
    public int getItemCount() {
        return modelsArr.size();
    }

    public void addItem(QuestModel questModel) {
        modelsArr.add(questModel);
        notifyDataSetChanged();
    }
}
