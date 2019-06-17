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

package org.geometerplus.fbreader.fbreader.options;

import org.geometerplus.zlibrary.core.options.*;
import org.geometerplus.zlibrary.core.view.ZLView;

/**
 * 翻页选择设置
 */
public class PageTurningOptions {

    /**
     * 手指滑动类型：点击，滑动，点击和滑动
     */
    public enum FingerScrollingType {
        byTap, byFlick, byTapAndFlick
    }

    /**
     * 手指滑动类型支持
     */
    public final ZLEnumOption<FingerScrollingType> FingerScrolling =
            new ZLEnumOption<>("Scrolling", "Finger", FingerScrollingType.byTapAndFlick);
    /**
     * 翻页动画
     */
    public final ZLEnumOption<ZLView.Animation> Animation =
            new ZLEnumOption<>("Scrolling", "Animation", ZLView.Animation.previewShift);
    /**
     * 动画速度
     */
    public final ZLIntegerRangeOption AnimationSpeed =
            new ZLIntegerRangeOption("Scrolling", "AnimationSpeed", 1, 10, 7);
    /**
     * 滑动方向
     */
    public final ZLBooleanOption Horizontal =
            new ZLBooleanOption("Scrolling", "Horizontal", true);
    /**
     * 点击位置图
     */
    public final ZLStringOption TapZoneMap =
            new ZLStringOption("Scrolling", "TapZoneMap", "");
}