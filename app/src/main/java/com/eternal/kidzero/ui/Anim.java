package com.eternal.kidzero.ui;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.eternal.kidzero.App;
import com.eternal.kidzero.R;
import com.eternal.kidzero.interfaces.functions.Functions;

public class Anim {

    Functions.Action<Animation> onAnimationStart;
    Functions.Action<Animation> onAnimationEnd;
    Functions.Action<Animation> onAnimationRepeat;

    View view;
    Animation animation;

    public Anim setOnAnimationStart(Functions.Action<Animation> onAnimationStart) {
        this.onAnimationStart = onAnimationStart;
        return this;
    }

    public Anim setOnAnimationEnd(Functions.Action<Animation> onAnimationEnd) {
        this.onAnimationEnd = onAnimationEnd;
        return this;
    }

    public Anim setOnAnimationRepeat(Functions.Action<Animation> onAnimationRepeat) {
        this.onAnimationRepeat = onAnimationRepeat;
        return this;
    }

    public Animation create(int animId) {
        return AnimationUtils.loadAnimation(App.getAppContext(), animId);
    }

    public Anim() { }

    public Anim(int animId, View view) {

        this.view = view;

        animation = AnimationUtils.loadAnimation(App.getAppContext(), animId);
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                if (onAnimationStart != null)
                    onAnimationStart.apply(animation);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (onAnimationEnd != null)
                    onAnimationEnd.apply(animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                if (onAnimationRepeat != null)
                    onAnimationRepeat.apply(animation);
            }
        });
    }

    public void play() {
        this.view.startAnimation(animation);
    }
}
