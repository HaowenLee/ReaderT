package org.geometerplus.android.fbreader.util;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.haowen.huge.DebugLog;

import org.geometerplus.android.fbreader.constant.PreviewConfig;
import org.geometerplus.zlibrary.ui.android.R;
import org.geometerplus.zlibrary.ui.android.view.ZLAndroidWidget;

public class AnimationHelper {

    /**
     * 打开侧边菜单
     *
     * @param targetView     目标
     * @param viewBackground 侧边栏背景
     */
    public static void openSlideMenu(View targetView, View viewBackground, View readerView) {
        openMenu(targetView, -1, 0, 0, 0);
        openSlideAlphaBackground(viewBackground);
        openSlideMenuReader(readerView);
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

    private static void openSlideAlphaBackground(View targetView) {
        if (targetView.getVisibility() == View.VISIBLE) {
            return;
        }
        targetView.setVisibility(View.VISIBLE);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 0.3f);
        alphaAnimation.setDuration(200);
        alphaAnimation.setFillAfter(true);
        targetView.startAnimation(alphaAnimation);
    }

    /**
     * 打开预览
     */
    private static void openSlideMenuReader(View targetView) {
        TranslateAnimation outAnimation = new TranslateAnimation(0,
                ScreenUtils.getWidth(targetView.getContext()) - SizeUtils.dp2px(targetView.getContext(), 40), 0, 0);
        outAnimation.setDuration(200);
        outAnimation.setFillAfter(true);
        targetView.startAnimation(outAnimation);
    }

    /**
     * 关闭侧边菜单
     *
     * @param targetView     目标
     * @param viewBackground 侧边栏背景
     */
    public static void closeSlideMenu(View targetView, View viewBackground, View readerView) {
        closeMenu(targetView, 0, -1, 0, 0);
        closeSlideAlphaBackground(viewBackground);
        closeSlideMenuReader(readerView);
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

    private static void closeSlideAlphaBackground(final View targetView) {
        if (targetView.getVisibility() != View.VISIBLE) {
            return;
        }
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.3f, 0);
        alphaAnimation.setDuration(200);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
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
        targetView.startAnimation(alphaAnimation);
    }

    /**
     * 关闭预览
     */
    private static void closeSlideMenuReader(View targetView) {
        TranslateAnimation outAnimation = new TranslateAnimation(ScreenUtils.getWidth(targetView.getContext())
                - SizeUtils.dp2px(targetView.getContext(), 40), 0, 0, 0);
        outAnimation.setDuration(200);
        targetView.startAnimation(outAnimation);
    }

    /**
     * 打开顶部菜单
     *
     * @param targetView 目标
     */
    public static void openTopMenu(View targetView) {
        openMenu(targetView, 0, 0, -1, 0);
    }

    /**
     * 关闭顶部菜单
     *
     * @param targetView 目标
     */
    public static void closeTopMenu(View targetView) {
        closeMenu(targetView, 0, 0, 0, -1);
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
        closeBottomMenu(targetView, true);
    }

    /**
     * 关闭底部菜单
     *
     * @param targetView 目标
     */
    public static void closeBottomMenu(View targetView, boolean smooth) {
        if (smooth) {
            closeMenu(targetView, 0, 0, 0, 1);
        } else {
            targetView.setVisibility(View.GONE);
        }
    }

    /**
     * 开启预览
     *
     * @param targetView 目标
     */
    public static void openPreview(ZLAndroidWidget targetView) {
        ScaleAnimation animation = new ScaleAnimation(1 / PreviewConfig.SCALE_VALUE, 1, 1 / PreviewConfig.SCALE_VALUE,
                1, Animation.RELATIVE_TO_SELF, PreviewConfig.SCALE_VALUE_PX, Animation.RELATIVE_TO_SELF, PreviewConfig.SCALE_VALUE_PY);
        animation.setDuration(200);
        targetView.startAnimation(animation);
        targetView.setPreview(true);
    }

    /**
     * 关闭预览
     *
     * @param targetView 目标
     */
    public static void closePreview(final ZLAndroidWidget targetView) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1 / PreviewConfig.SCALE_VALUE, 1,
                1 / PreviewConfig.SCALE_VALUE, Animation.RELATIVE_TO_SELF, PreviewConfig.SCALE_VALUE_PX, Animation.RELATIVE_TO_SELF, PreviewConfig.SCALE_VALUE_PY);
        scaleAnimation.setDuration(200 + 100);
        targetView.startAnimation(scaleAnimation);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                targetView.setPreview(false);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}