package org.geometerplus.zlibrary.core.view;

import org.geometerplus.zlibrary.core.library.ZLibrary;
import org.geometerplus.zlibrary.core.util.ZLColor;

/**
 * 选中光标
 */
public abstract class SelectionCursor {

    /**
     * 绘制选中
     *
     * @param context     ZLPaintContext
     * @param which       左右
     * @param x           x
     * @param y           y
     * @param cursorColor 光标颜色
     */
    public static void draw(ZLPaintContext context, Which which, int x, int y, ZLColor cursorColor) {
        context.setFillColor(cursorColor);
        final int dpi = ZLibrary.Instance().getDisplayDPI();
        final int unit = dpi / 120;
        final int xCenter = which == Which.Left ? x - unit - 1 : x + unit + 1;
        context.fillRectangle(xCenter - unit, y + dpi / 8, xCenter + unit, y - dpi / 8);
        if (which == Which.Left) {
            context.fillCircle(xCenter, y - dpi / 8, unit * 6);
        } else {
            context.fillCircle(xCenter, y + dpi / 8, unit * 6);
        }
    }

    /**
     * 左右光标
     */
    public enum Which {
        Left,
        Right
    }
}