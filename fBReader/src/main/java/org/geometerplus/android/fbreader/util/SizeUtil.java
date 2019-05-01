package org.geometerplus.android.fbreader.util;

import android.content.Context;

public class SizeUtil {

    public static int dp2px(Context context,float dp){
        return (int) (context.getResources().getDisplayMetrics().density * dp + 0.5);
    }
}
