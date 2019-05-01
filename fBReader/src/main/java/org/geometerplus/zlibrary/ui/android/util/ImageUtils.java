package org.geometerplus.zlibrary.ui.android.util;

import android.graphics.Bitmap;

public class ImageUtils {

    public static Bitmap scale(final Bitmap src,
                               final float scale,
                               final boolean recycle) {
        Bitmap ret = Bitmap.createScaledBitmap(src, (int) (src.getWidth() * scale), (int) (src.getHeight() * scale), true);
        if (recycle && !src.isRecycled() && ret != src) src.recycle();
        return ret;
    }
}
