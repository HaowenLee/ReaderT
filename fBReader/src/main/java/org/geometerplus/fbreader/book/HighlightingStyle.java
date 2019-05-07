package org.geometerplus.fbreader.book;

import org.geometerplus.zlibrary.core.util.ZLColor;

/**
 * 标记样式
 */
public class HighlightingStyle {

    public final int Id;
    public final long LastUpdateTimestamp;

    private String myName;
    private ZLColor myBackgroundColor;
    private ZLColor myForegroundColor;

    HighlightingStyle(int id, long timestamp, String name, ZLColor bgColor, ZLColor fgColor) {
        Id = id;
        LastUpdateTimestamp = timestamp;

        myName = name;
        myBackgroundColor = bgColor;
        myForegroundColor = fgColor;
    }

    public String getNameOrNull() {
        return "".equals(myName) ? null : myName;
    }

    void setName(String name) {
        myName = name;
    }

    public ZLColor getBackgroundColor() {
        return myBackgroundColor;
    }

    public void setBackgroundColor(ZLColor bgColor) {
        myBackgroundColor = bgColor;
    }

    public ZLColor getForegroundColor() {
        return myForegroundColor;
    }

    public void setForegroundColor(ZLColor fgColor) {
        myForegroundColor = fgColor;
    }
}
