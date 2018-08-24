package com.kwabenaberko.cointrack.utils;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kwabenaberko.cointrack.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Kwabena Berko on 8/23/2018.
 */

public class Helpers {
    public static String round(double number) {
        NumberFormat numberFormat = new DecimalFormat("##.##");
        return (numberFormat.format(number));
    }

    public static void playListItemTransitionAnimation(Context context, int colorFrom, final LinearLayout listItemLayout){
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(),
                ContextCompat.getColor(context, colorFrom),
                ContextCompat.getColor(context, android.R.color.white));
        colorAnimation.setDuration(5000); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                listItemLayout.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();
    }

    public static void playTextTransitionAnimation(Context context, int colorFrom, final TextView textView){
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(),
                ContextCompat.getColor(context, colorFrom),
                ContextCompat.getColor(context, R.color.text));
        colorAnimation.setDuration(20000);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                textView.setTextColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();
    }
}
