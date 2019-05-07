package org.geometerplus.android.fbreader.util;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class AnimationHelper {

    /**
     * 打开顶部菜单
     *
     * @param targetView 目标
     */
    public static void openTopMenu(View targetView) {
        openMenu(targetView, 0, 0, -1, 0);
    }

    public static void openMenu(View targetView, float fromXValue, float toXValue, float fromYValue, float toYValue) {
        if (targetView.getVisibility() == View.VISIBLE) {
            return;
        }
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                fromXValue, Animation.RELATIVE_TO_SELF, toXValue, Animation.RELATIVE_TO_SELF, fromYValue, Animation.RELATIVE_TO_SELF, toYValue);
        animation.setDuration(200);
        targetView.startAnimation(animation);
        targetView.setVisibility(View.VISIBLE);
    }

    /**
     * 关闭顶部菜单
     *
     * @param targetView 目标
     */
    public static void closeTopMenu(View targetView) {
        closeMenu(targetView, 0, 0, 0, -1);
    }

    public static void closeMenu(final View targetView, float fromXValue, float toXValue, float fromYValue, float toYValue) {
        if (targetView.getVisibility() != View.VISIBLE) {
            return;
        }
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                fromXValue, Animation.RELATIVE_TO_SELF, toXValue, Animation.RELATIVE_TO_SELF, fromYValue, Animation.RELATIVE_TO_SELF, toYValue);
        animation.setDuration(200);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                targetView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        targetView.startAnimation(animation);
    }

    /**
     * 打开底部菜单
     *
     * @param targetView 目标
     */
    public static void openBottomMenu(View targetView) {
        openMenu(targetView, 0, 0, 1, 0);
    }

    /**
     * 关闭底部菜单
     *
     * @param targetView 目标
     */
    public static void closeBottomMenu(View targetView) {
        closeMenu(targetView, 0, 0, 0, 1);
    }
}