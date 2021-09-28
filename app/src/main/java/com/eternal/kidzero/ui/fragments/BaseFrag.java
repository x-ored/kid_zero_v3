package com.eternal.kidzero.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.eternal.kidzero.R;
import com.eternal.kidzero.ui.fragments.parent.ParentMainFrag;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseFrag extends Fragment {

    public View fragView;
    public int customAnimAssetId = 0;

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (customAnimAssetId == 0) {
            view.startAnimation(AnimationUtils.loadAnimation(this.getContext(), R.anim.frag_anim));
        }
        else {
            view.startAnimation(AnimationUtils.loadAnimation(this.getContext(), customAnimAssetId));
        }

        this.fragView = view;
    }

    public void useAnim(int customAnimAssetId) {
        this.customAnimAssetId = customAnimAssetId;
    }

    public boolean slideDown(){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                fragView.getHeight()); // toYDelta
        animate.setDuration(200);

        fragView.startAnimation(animate);

        return animate.hasEnded();
    }

    public void showAlertDialog(String msg) {
        new DialogFrag(msg).show(getFragmentManager(), "Alert!");
    }

    public void setSelectEditText(EditText editText) {
        if (editText.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void appBarInit(String Text) {
        ((TextView)fragView.findViewById(R.id.appBarTextView)).setText(Text);
    }

    public void appBarInit(String Text, Boolean backStack) {

        if (backStack) {
            ImageView appBarIconImageView = fragView.findViewById(R.id.appBarIconImageView);
            appBarIconImageView.setVisibility(View.VISIBLE);
            appBarIconImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().popBackStack();
                }
            });
        }
        ((TextView)fragView.findViewById(R.id.appBarTextView)).setText(Text);
    }

    public void executeActionFrag(int actId) {
        NavHostFragment.findNavController(this).navigate(actId);
    }

    public void executeActionFrag(int actId, Bundle args) {
        NavHostFragment.findNavController(this).navigate(actId, args);
    }

    public void parentNavigate(Fragment baseFrag) {
        getFragmentManager().beginTransaction()
                .replace(R.id.parental_nav_host_fragment, baseFrag)
                .addToBackStack(null)
                .commit();
    }
}
