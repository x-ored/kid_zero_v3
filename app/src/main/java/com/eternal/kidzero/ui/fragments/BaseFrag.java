package com.eternal.kidzero.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.eternal.kidzero.R;

public class BaseFrag extends Fragment {

    public void executeActionFrag(int actId) {
        NavHostFragment.findNavController(this).navigate(actId);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.startAnimation(AnimationUtils.loadAnimation(this.getContext(), R.anim.frag_anim));
    }
}
