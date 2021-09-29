package com.eternal.kidzero.ui.fragments.parent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.eternal.kidzero.FbCore;
import com.eternal.kidzero.R;
import com.eternal.kidzero.ui.fragments.BaseFrag;

public class AddQuestFrag extends BaseFrag {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_add_quest, container, false);
    }

    public int Stars = 0;

    private TextView rewardStars;
    private ImageView startImg;

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        appBarInit("New quest", true);

        rewardStars = view.findViewById(R.id.rewardStars);
        startImg = view.findViewById(R.id.startImg);

        view.findViewById(R.id.minusStar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Stars >= 1) {
                    Stars--;
                }
                updateStars();
            }
        });

        view.findViewById(R.id.plusStar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Stars >= 0) {
                    Stars++;
                }
                updateStars();
            }
        });
    }

    private void updateStars() {
        startImg.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake));
        rewardStars.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.number));
        rewardStars.setText(String.valueOf(Stars));
    }
}