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

package org.geometerplus.zlibrary.core.view;

import org.geometerplus.zlibrary.core.application.ZLApplication;

abstract public class ZLView implements ZLViewEnums {

    public final ZLApplication Application;

    /**
     * 空对象模式
     */
    private ZLPaintContext myViewContext = new DummyPaintContext();

    protected ZLView(ZLApplication application) {
        Application = application;
    }

    protected final void setContext(ZLPaintContext context) {
        myViewContext = context;
    }

    public final ZLPaintContext getContext() {
        return myViewContext;
    }

    private boolean isPreview = false;

    public boolean isPreview() {
        return isPreview;
    }

    public void setPreview(boolean preview) {
        isPreview = preview;
    }

    /**
     * 获取宽度
     *
     * @return 宽度
     */
    public final int getContextWidth() {
        return myViewContext.getWidth();
    }

    /**
     * 获取高度
     *
     * @return 高度
     */
    public final int getContextHeight() {
        return myViewContext.getHeight();
    }

    public interface FooterArea {
        int getHeight();

        void paint(ZLPaintContext context);
    }

    abstract public FooterArea getFooterArea();

    public abstract Animation getAnimationType();

    abstract public void preparePage(ZLPaintContext context, PageIndex pageIndex);

    abstract public void paint(ZLPaintContext context, PageIndex pageIndex);

    abstract public void onScrollingFinished(PageIndex pageIndex);

    public abstract void onFingerPress(int x, int y);

    public abstract void onFingerRelease(int x, int y);

    public abstract void onFingerMove(int x, int y);

    public abstract boolean onFingerLongPress(int x, int y);

    public abstract void onFingerReleaseAfterLongPress(int x, int y);

    public abstract void onFingerMoveAfterLongPress(int x, int y);

    public abstract void onFingerSingleTap(int x, int y);

    public abstract void onFingerDoubleTap(int x, int y);

    public abstract void onFingerEventCancelled();

    /**
     * 是否双击支持
     *
     * @return 是否双击支持
     */
    public boolean isDoubleTapSupported() {
        return false;
    }

    public boolean onTrackballRotated(int diffX, int diffY) {
        return false;
    }

    /**
     * 是否显示滚动条
     *
     * @return 是否显示滚动条
     */
    public abstract boolean isScrollbarShown();

    /**
     * 获取滚动条整体大小
     *
     * @return 滚动条整体大小
     */
    public abstract int getScrollbarFullSize();

    /**
     * 获取滚动条滑块位置
     *
     * @param pageIndex 页面索引
     * @return 滚动条滑块长度
     */
    public abstract int getScrollbarThumbPosition(PageIndex pageIndex);

    /**
     * 获取滚动条滑块长度
     *
     * @param pageIndex 页面索引
     * @return 滚动条滑块长度
     */
    public abstract int getScrollbarThumbLength(PageIndex pageIndex);

    /**
     * 是否能滚动
     *
     * @param index 页面索引
     * @return 是否能滚动
     */
    public abstract boolean canScroll(PageIndex index);

    /**
     * @return 是否可以使用放大镜
     */
    public abstract boolean canMagnifier();
}