package org.geometerplus.zlibrary.core.view;

import org.geometerplus.zlibrary.core.library.ZLibrary;
import org.geometerplus.zlibrary.core.util.ZLColor;

/**
 * 选中光标
 */
public abstract class SelectionCursor {

    public static void draw(ZLPaintContext context, Which which, int x, int y, ZLColor color, ZLColor cursorColor) {
        context.setFillColor(color);
        final int dpi = ZLibrary.Instance().getDisplayDPI();
        final int unit = dpi / 120;
        final int xCenter = which == Which.Left ? x - unit - 1 : x + unit + 1;
        context.fillRectangle(xCenter - unit, y + dpi / 8, xCenter + unit, y - dpi / 8);
        context.setFillColor(cursorColor);
        if (which == Which.Left) {
            context.fillCircle(xCenter, y - dpi / 8, unit * 6);
        } else {
            context.fillCircle(xCenter, y + dpi / 8, unit * 6);
        }
    }

    public enum Which {
        Left,
        Right
    }
}
