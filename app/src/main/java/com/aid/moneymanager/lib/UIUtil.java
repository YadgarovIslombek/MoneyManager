/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 19.01.21 15:30
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

// based on
// https://www.thedroidsonroids.com/blog/android/meaningful-motion-with-shared-element-transition-and-circular-reveal-animation/
// https://github.com/DroidsOnRoids/MaterialShowcase

package com.aid.moneymanager.lib;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;

import androidx.annotation.ColorRes;

import com.aid.moneymanager.R;

//--> I FOUND THIS lIBRARY OPEN SOURCE CODE FROM GITHUB AND I IMPLEMENTED MY PROJECT <-- //

public class UIUtil {

    public static void animateRevealHide(final Context ctx, final View view, @ColorRes final int color,
                                         final int finalRadius, final AnimationListener listener) {
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;
        int initialRadius = view.getWidth();

        Animator anim =
                null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, finalRadius);
        }
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setBackgroundColor(ctx.getResources().getColor(color));

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
                listener.onRevealHide();
            }
        });
        anim.setDuration(ctx.getResources().getInteger(R.integer.animation_duration_exit));
        anim.start();
    }
    public static void animateRevealShow(final Context ctx, final View view, final int startRadius,
                                         @ColorRes final int color, int x, int y, final AnimationListener listener) {
        float finalRadius = (float) Math.hypot(view.getWidth(), view.getHeight());

        Animator anim =
                null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(view, x, y, startRadius, finalRadius);
        }
        anim.setDuration(ctx.getResources().getInteger(R.integer.animation_duration));
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setBackgroundColor(ctx.getResources().getColor(color));
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.VISIBLE);
                listener.onRevealShow();
            }
        });
        anim.start();
    }

    private static Animation.AnimationListener getGoneAnimationListener(final OnReturnAnimationFinished listener, final View... views) {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                for(View v : views) {
                    v.setVisibility(View.INVISIBLE);
                }
                if(listener != null) {
                    listener.onAnimationFinished();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
    }

    private static Animation.AnimationListener getShowAnimationListener(final OnReturnAnimationFinished listener, final View... views) {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                for(View v : views) {
                    v.setVisibility(View.VISIBLE);
                }
                if(listener != null) {
                    listener.onAnimationFinished();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
    }

    private static void startAnimations(Animation animation, View... views) {
        for(View v : views) {
            v.startAnimation(animation);
        }
    }

    public interface OnReturnAnimationFinished {
        void onAnimationFinished();
    }
}