package org.geometerplus.android.fbreader.util;

import android.content.Context;

public class SizeUtils {

    public static int dp2px(Context context,float dp){
        return (int) (context.getResources().getDisplayMetrics().density * dp + 0.5);
    }
}
