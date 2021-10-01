package com.eternal.kidzero.ui.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.eternal.kidzero.R;
import com.eternal.kidzero.ui.Anim;

public class DialogFrag extends DialogFragment {

    public String messageToShow;

    public DialogFrag(String messageToShow) {
        this.messageToShow = messageToShow;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_wnd, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ((TextView)view.findViewById(R.id.alertMessage_TextView)).setText(messageToShow);

        view.findViewById(R.id.Alert_ImageView).startAnimation(new Anim().create(R.anim.shake));
        view.findViewById(R.id.CancelDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });
    }
}
