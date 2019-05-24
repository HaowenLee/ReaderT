/*
 * Copyright (C) 2007-2015 FBReader.ORG Limited <contact@fbreader.org>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 */

package org.geometerplus.zlibrary.ui.android.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.haowen.huge.DebugLog;

import org.geometerplus.android.fbreader.constant.PreviewConfig;
import org.geometerplus.zlibrary.core.view.ZLView;
import org.geometerplus.zlibrary.core.view.ZLViewEnums;
import org.geometerplus.zlibrary.ui.android.view.animation.BitmapManager;


/**
 * Bitmap管理（绘制后的图）的实现
 */
final class BitmapManagerImpl implements BitmapManager {

    /**
     * 缓存Bitmap大小
     */
    private final int SIZE = 4;
    private final Bitmap[] myBitmaps = new Bitmap[SIZE];
    private final ZLView.PageIndex[] myIndexes = new ZLView.PageIndex[SIZE];

    private int myWidth;
    private int myHeight;

    private final ZLAndroidWidget myWidget;

    BitmapManagerImpl(ZLAndroidWidget widget) {
        myWidget = widget;
    }

    /**
     * 设置绘制Bitmap的宽高（即阅读器内容区域）
     *
     * @param w 宽
     * @param h 高
     */
    void setSize(int w, int h) {
        if (myWidth != w || myHeight != h) {
            myWidth = w;
            myHeight = h;
            for (int i = 0; i < SIZE; ++i) {
                myBitmaps[i] = null;
                myIndexes[i] = null;
            }
            System.gc();
            System.gc();
            System.gc();
        }
    }

    /**
     * 获取阅读器内容Bitmap
     *
     * @param index 页索引
     * @return 阅读器内容Bitmap
     */
    public Bitmap getBitmap(ZLView.PageIndex index) {
        for (int i = 0; i < SIZE; ++i) {
            if (index == myIndexes[i]) {
                return myBitmaps[i];
            }
        }
        final int iIndex = getInternalIndex(index);
        myIndexes[iIndex] = index;

        // 如果该位置的Bitmap为null就创建一个
        if (myBitmaps[iIndex] == null) {
            try {
                myBitmaps[iIndex] = Bitmap.createBitmap(myWidth, myHeight, Bitmap.Config.RGB_565);
            } catch (OutOfMemoryError e) {
                // 内存溢出后，调用gc，再创建
                System.gc();
                System.gc();
                myBitmaps[iIndex] = Bitmap.createBitmap(myWidth, myHeight, Bitmap.Config.RGB_565);
            }
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < SIZE; ++i) {
            builder.append(" | ").append(myIndexes[i]);
        }
        System.out.println(builder.toString());

        // 绘制出Bitmap
        myWidget.drawOnBitmap(myBitmaps[iIndex], index);
        return myBitmaps[iIndex];
    }

    /**
     * 绘制页面（主要应用与动画） {@link org.geometerplus.zlibrary.ui.android.view.animation.AnimationProvider}
     *
     * @param canvas 画布
     * @param x      x坐标
     * @param y      y坐标
     * @param index  页面索引
     * @param paint  画笔
     */
    public void drawBitmap(Canvas canvas, int x, int y, ZLView.PageIndex index, Paint paint) {
        canvas.drawBitmap(getBitmap(index), x, y, paint);
    }

    /**
     * 绘制预览页面（主要应用与动画） {@link org.geometerplus.zlibrary.ui.android.view.animation.AnimationProvider}
     *
     * @param canvas 画布
     * @param x      x坐标
     * @param y      y坐标
     * @param index  页面索引
     * @param paint  画笔
     */
    @Override
    public void drawPreviewBitmap(Canvas canvas, int x, int y, ZLViewEnums.PageIndex index, Paint paint) {
        Bitmap previousBitmap = getBitmap(ZLView.PageIndex.previous);
        int width = previousBitmap.getWidth();
        canvas.drawBitmap(previousBitmap, x / PreviewConfig.SCALE_VALUE - width - width * PreviewConfig.SCALE_MARGIN_VALUE, y / PreviewConfig.SCALE_VALUE, paint);
        canvas.drawBitmap(getBitmap(ZLView.PageIndex.current), x / PreviewConfig.SCALE_VALUE, y / PreviewConfig.SCALE_VALUE, paint);
        canvas.drawBitmap(getBitmap(ZLView.PageIndex.next), x / PreviewConfig.SCALE_VALUE + width + width * PreviewConfig.SCALE_MARGIN_VALUE, y / PreviewConfig.SCALE_VALUE, paint);
    }

    /**
     * 获取一个内部索引位置，用于存储Bitmap（原则是：先寻找空的，再寻找非当前使用的）
     *
     * @return 索引位置
     */
    private int getInternalIndex(ZLViewEnums.PageIndex index) {
        // 寻找没有存储内容的位置
        for (int i = 0; i < SIZE; ++i) {
            if (myIndexes[i] == null) {
                return i;
            }
        }
        // 如果没有，找一个不是当前的位置
        for (int i = 0; i < SIZE; ++i) {
            if (myIndexes[i] != ZLView.PageIndex.current && myIndexes[i] != ZLView.PageIndex.previous && myIndexes[i] != ZLView.PageIndex.next) {
                return i;
            }
        }
        throw new RuntimeException("That's impossible");
    }

    /**
     * 重置索引缓存
     * TODO: 需要精确rest（避免不必要的缓存失效）
     */
    void reset() {
        for (int i = 0; i < SIZE; ++i) {
            myIndexes[i] = null;
        }
    }

    /**
     * 位移操作（所有索引位移至下一状态）
     *
     * @param forward 是否向前
     */
    void shift(boolean forward) {
        for (int i = 0; i < SIZE; ++i) {
            if (myIndexes[i] == null) {
                continue;
            }
            myIndexes[i] = forward ? myIndexes[i].getPrevious() : myIndexes[i].getNext();
        }
    }
}