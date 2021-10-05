package com.eternal.kidzero.adapters;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eternal.kidzero.App;
import com.eternal.kidzero.FDatabase;
import com.eternal.kidzero.R;
import com.eternal.kidzero.enums.InviteStatus;
import com.eternal.kidzero.models.ChildModel;
import com.eternal.kidzero.models.UserModel;
import com.eternal.kidzero.ui.Anim;
import com.eternal.kidzero.ui.fragments.BaseFrag;

import java.util.ArrayList;
import java.util.Collection;

public class RcChildAdapter extends RecyclerView.Adapter<RcChildAdapter.viewHolder> {

    private ArrayList<UserModel> modelsArr = new ArrayList<>();
    private final String TAG = "RcChildAdapter";
    private BaseFrag baseFrag;
    private Boolean withInvite = false;

    public TextView rcIsEmpty_TextView;

    public RcChildAdapter(BaseFrag baseFrag) {
        this.baseFrag = baseFrag;
    }
    public RcChildAdapter(BaseFrag baseFrag, Boolean withInvite) {
        this.baseFrag = baseFrag;
        this.withInvite = withInvite;
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        LinearLayout acceptContainer;
        RelativeLayout cancelInvite;
        RelativeLayout acceptInvite;
        TextView childName;
        View view;

        public viewHolder(View itemView) {
            super(itemView);

            this.childName = itemView.findViewById(R.id.childName);
            this.view = itemView;

            if (withInvite) {
                acceptContainer = itemView.findViewById(R.id.acceptContainer);
                acceptInvite = itemView.findViewById(R.id.acceptInvite);
                cancelInvite = itemView.findViewById(R.id.cancelInvite);
                acceptContainer.setVisibility(View.VISIBLE);
            }
        }
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_child_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        getItemCount();
        int pos = position;

        UserModel userModel = modelsArr.get(position);

        holder.childName.setText(userModel.getName());

        if (withInvite) {
            if(!userModel.getStatus().equals(InviteStatus.wait))
                holder.view.setVisibility(View.GONE);

            holder.acceptInvite.setOnClickListener(v -> new Anim(R.anim.slide_left, holder.view)
                    .setOnAnimationEnd(animation -> {
                        FDatabase.curentUser().AddConected(userModel.getUid()).save().addOnSuccessListener(aVoid -> {
                            userModel.setStatus(InviteStatus.ok).save();
                        }).addOnFailureListener(e -> {
                            // show Error
                        });

                    })
                    .play());
            holder.cancelInvite.setOnClickListener(v -> new Anim(R.anim.slide_left, holder.view)
                    .setOnAnimationEnd(animation -> userModel.remove())
                    .play());
        }
        else {
            holder.view.setOnClickListener(v -> {
                Bundle b = new Bundle();
                b.putSerializable("userModel", userModel);
                baseFrag.executeActionFrag(R.id.ActGoTo_ChildProfileFrag, b);
            });
        }
    }

    @Override
    public int getItemCount() {
        checkCount();
        return modelsArr.size();
    }

    public void removeItem(int position) {
        modelsArr.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(ChildModel childModel) {
        modelsArr.add(childModel);
        notifyDataSetChanged();
    }

    public void updateItems() {
        modelsArr.clear();
        modelsArr.addAll(FDatabase.getParentChildManager().getChilds());
        notifyDataSetChanged();
    }
    public void updateItems(Collection<UserModel> xxx) {
        modelsArr.clear();
        modelsArr.addAll(xxx);
        checkCount();
        notifyDataSetChanged();

    }

    public void checkCount() {
        if (modelsArr.size() < 1 && rcIsEmpty_TextView != null) {
            rcIsEmpty_TextView.setVisibility(View.VISIBLE);
            rcIsEmpty_TextView.startAnimation(new Anim().create(R.anim.number));
        }else if (modelsArr.size() >0 && rcIsEmpty_TextView != null){
            rcIsEmpty_TextView.setVisibility(View.GONE);
            rcIsEmpty_TextView.startAnimation(new Anim().create(R.anim.number));
        }
    }
}
