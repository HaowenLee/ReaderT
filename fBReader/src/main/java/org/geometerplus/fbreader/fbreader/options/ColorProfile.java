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

import java.util.*;

import org.geometerplus.zlibrary.core.util.ZLColor;
import org.geometerplus.zlibrary.core.options.*;
import org.geometerplus.zlibrary.core.view.ZLPaintContext;

public class ColorProfile {

    public static final String THEME_WHITE = "white";
    public static final String THEME_YELLOW = "yellow";
    public static final String THEME_GREEN = "green";
    public static final String THEME_BLACK = "black";

    private static final ArrayList<String> ourNames = new ArrayList<String>();
    private static final HashMap<String, ColorProfile> ourProfiles = new HashMap<String, ColorProfile>();
    public final String Name;
    public final ZLStringOption WallpaperOption;
    public final ZLEnumOption<ZLPaintContext.FillMode> FillModeOption;
    /**
     * 阅读器背景色
     */
    public final ZLColorOption BackgroundOption;
    /**
     * 选中区域背景色
     */
    public final ZLColorOption SelectionBackgroundOption;
    /**
     * 选中区域前景色
     */
    public final ZLColorOption SelectionForegroundOption;
    /**
     * 选中的左右游标颜色
     */
    public final ZLColorOption SelectionCursorOption;
    public final ZLColorOption HighlightingForegroundOption;
    public final ZLColorOption HighlightingBackgroundOption;
    /**
     * 普通文字颜色
     */
    public final ZLColorOption RegularTextOption;
    /**
     * 超链接文字颜色
     */
    public final ZLColorOption HyperlinkTextOption;
    /**
     * 访问过的超链接文字颜色（点击过）
     */
    public final ZLColorOption VisitedHyperlinkTextOption;
    public final ZLColorOption FooterFillOption;
    public final ZLColorOption FooterNGBackgroundOption;
    public final ZLColorOption FooterNGForegroundOption;
    public final ZLColorOption FooterNGForegroundUnreadOption;
    public final ZLColorOption HeaderAndFooterColorOption;

    private ColorProfile(String name, ColorProfile base) {
        this(name);
        WallpaperOption.setValue(base.WallpaperOption.getValue());
        FillModeOption.setValue(base.FillModeOption.getValue());
        BackgroundOption.setValue(base.BackgroundOption.getValue());
        SelectionBackgroundOption.setValue(base.SelectionBackgroundOption.getValue());
        SelectionForegroundOption.setValue(base.SelectionForegroundOption.getValue());
        SelectionCursorOption.setValue(base.SelectionCursorOption.getValue());
        HighlightingForegroundOption.setValue(base.HighlightingForegroundOption.getValue());
        HighlightingBackgroundOption.setValue(base.HighlightingBackgroundOption.getValue());
        RegularTextOption.setValue(base.RegularTextOption.getValue());
        HyperlinkTextOption.setValue(base.HyperlinkTextOption.getValue());
        VisitedHyperlinkTextOption.setValue(base.VisitedHyperlinkTextOption.getValue());
        FooterFillOption.setValue(base.FooterFillOption.getValue());
        FooterNGBackgroundOption.setValue(base.FooterNGBackgroundOption.getValue());
        FooterNGForegroundOption.setValue(base.FooterNGForegroundOption.getValue());
        FooterNGForegroundUnreadOption.setValue(base.FooterNGForegroundUnreadOption.getValue());
        HeaderAndFooterColorOption.setValue(base.HeaderAndFooterColorOption.getValue());
    }

    private ColorProfile(String name) {
        Name = name;
        switch (name) {
            case THEME_YELLOW:
                WallpaperOption =
                        new ZLStringOption("Colors", name + ":Wallpaper", "");
                FillModeOption =
                        new ZLEnumOption<>("Colors", name + ":FillMode", ZLPaintContext.FillMode.tile);
                BackgroundOption =
                        createOption(name, "Background", 228, 223, 204);
                SelectionBackgroundOption =
                        createOption(name, "SelectionBackground", 224, 207, 167);
                SelectionForegroundOption =
                        createNullOption(name, "SelectionForeground");
                SelectionCursorOption =
                        createOption(name, "SelectionCursorOption", 173, 124, 45);
                HighlightingBackgroundOption =
                        createOption(name, "Highlighting", 255, 192, 128);
                HighlightingForegroundOption =
                        createNullOption(name, "HighlightingForeground");
                RegularTextOption =
                        createOption(name, "Text", 62, 42, 23);
                HyperlinkTextOption =
                        createOption(name, "Hyperlink", 60, 139, 255);
                VisitedHyperlinkTextOption =
                        createOption(name, "VisitedHyperlink", 200, 139, 255);
                FooterFillOption =
                        createOption(name, "FooterFillOption", 170, 170, 170);
                FooterNGBackgroundOption =
                        createOption(name, "FooterNGBackgroundOption", 248, 248, 248);
                FooterNGForegroundOption =
                        createOption(name, "FooterNGForegroundOption", 154, 154, 154);
                FooterNGForegroundUnreadOption =
                        createOption(name, "FooterNGForegroundUnreadOption", 248, 248, 248);
                HeaderAndFooterColorOption =
                        createOption(name, "HeaderAndFooterColorOption", 145, 130, 115);
                break;
            case THEME_GREEN:
                WallpaperOption =
                        new ZLStringOption("Colors", name + ":Wallpaper", "");
                FillModeOption =
                        new ZLEnumOption<>("Colors", name + ":FillMode", ZLPaintContext.FillMode.tile);
                BackgroundOption =
                        createOption(name, "Background", 166, 201, 171);
                SelectionBackgroundOption =
                        createOption(name, "SelectionBackground", 133, 182, 116);
                SelectionForegroundOption =
                        createNullOption(name, "SelectionForeground");
                SelectionCursorOption =
                        createOption(name, "SelectionCursorOption", 70, 144, 34);
                HighlightingBackgroundOption =
                        createOption(name, "Highlighting", 255, 192, 128);
                HighlightingForegroundOption =
                        createNullOption(name, "HighlightingForeground");
                RegularTextOption =
                        createOption(name, "Text", 62, 42, 23);
                HyperlinkTextOption =
                        createOption(name, "Hyperlink", 60, 139, 255);
                VisitedHyperlinkTextOption =
                        createOption(name, "VisitedHyperlink", 200, 139, 255);
                FooterFillOption =
                        createOption(name, "FooterFillOption", 170, 170, 170);
                FooterNGBackgroundOption =
                        createOption(name, "FooterNGBackgroundOption", 248, 248, 248);
                FooterNGForegroundOption =
                        createOption(name, "FooterNGForegroundOption", 154, 154, 154);
                FooterNGForegroundUnreadOption =
                        createOption(name, "FooterNGForegroundUnreadOption", 248, 248, 248);
                HeaderAndFooterColorOption =
                        createOption(name, "HeaderAndFooterColorOption", 145, 130, 115);
                break;
            case THEME_BLACK:
                WallpaperOption =
                        new ZLStringOption("Colors", name + ":Wallpaper", "");
                FillModeOption =
                        new ZLEnumOption<>("Colors", name + ":FillMode", ZLPaintContext.FillMode.tile);
                BackgroundOption =
                        createOption(name, "Background", 52, 56, 60);
                SelectionBackgroundOption =
                        createOption(name, "SelectionBackground", 11, 22, 36);
                SelectionForegroundOption =
                        createNullOption(name, "SelectionForeground");
                SelectionCursorOption =
                        createOption(name, "SelectionCursorOption", 0, 131, 216);
                HighlightingBackgroundOption =
                        createOption(name, "Highlighting", 96, 96, 128);
                HighlightingForegroundOption =
                        createNullOption(name, "HighlightingForeground");
                RegularTextOption =
                        createOption(name, "Text", 193, 213, 232);
                HyperlinkTextOption =
                        createOption(name, "Hyperlink", 60, 142, 224);
                VisitedHyperlinkTextOption =
                        createOption(name, "VisitedHyperlink", 200, 139, 255);
                FooterFillOption =
                        createOption(name, "FooterFillOption", 85, 85, 85);
                FooterNGBackgroundOption =
                        createOption(name, "FooterNGBackgroundOption", 68, 68, 68);
                FooterNGForegroundOption =
                        createOption(name, "FooterNGForegroundOption", 187, 187, 187);
                FooterNGForegroundUnreadOption =
                        createOption(name, "FooterNGForegroundUnreadOption", 119, 119, 119);
                HeaderAndFooterColorOption =
                        createOption(name, "HeaderAndFooterColorOption", 126, 137, 148);
                break;
            default:
                WallpaperOption =
                        new ZLStringOption("Colors", name + ":Wallpaper", "");
                FillModeOption =
                        new ZLEnumOption<>("Colors", name + ":FillMode", ZLPaintContext.FillMode.tile);
                BackgroundOption =
                        createOption(name, "Background", 0xffffffff);
                SelectionBackgroundOption =
                        createOption(name, "SelectionBackground", 0xffd9edf9);
                SelectionForegroundOption =
                        createNullOption(name, "SelectionForeground");
                SelectionCursorOption =
                        createOption(name, "SelectionCursorOption", 0xff0083d8);
                HighlightingBackgroundOption =
                        createOption(name, "Highlighting", 217, 237, 249);
                HighlightingForegroundOption =
                        createNullOption(name, "HighlightingForeground");
                RegularTextOption =
                        createOption(name, "Text", 0xff3e2a17);
                HyperlinkTextOption =
                        createOption(name, "Hyperlink", 60, 139, 255);
                VisitedHyperlinkTextOption =
                        createOption(name, "VisitedHyperlink", 200, 139, 255);
                FooterFillOption =
                        createOption(name, "FooterFillOption", 170, 170, 170);
                FooterNGBackgroundOption =
                        createOption(name, "FooterNGBackgroundOption", 248, 248, 248);
                FooterNGForegroundOption =
                        createOption(name, "FooterNGForegroundOption", 154, 154, 154);
                FooterNGForegroundUnreadOption =
                        createOption(name, "FooterNGForegroundUnreadOption", 248, 248, 248);
                HeaderAndFooterColorOption =
                        createOption(name, "HeaderAndFooterColorOption", 154, 154, 154);
                break;
        }
    }

    private static ZLColorOption createOption(String profileName, String optionName, int r, int g, int b) {
        return new ZLColorOption("Colors", profileName + ':' + optionName, new ZLColor(r, g, b));
    }

    private static ZLColorOption createNullOption(String profileName, String optionName) {
        return new ZLColorOption("Colors", profileName + ':' + optionName, null);
    }

    private static ZLColorOption createOption(String profileName, String optionName, int color) {
        return new ZLColorOption("Colors", profileName + ':' + optionName, new ZLColor(color));
    }

    public static List<String> names() {
        if (ourNames.isEmpty()) {
            final int size = new ZLIntegerOption("Colors", "NumberOfSchemes", 0).getValue();
            if (size == 0) {
                ourNames.add(THEME_WHITE);
                ourNames.add(THEME_YELLOW);
                ourNames.add(THEME_GREEN);
                ourNames.add(THEME_BLACK);
            } else {
                for (int i = 0; i < size; ++i) {
                    ourNames.add(new ZLStringOption("Colors", "Scheme" + i, "").getValue());
                }
            }
        }
        return Collections.unmodifiableList(ourNames);
    }

    public static ColorProfile get(String name) {
        ColorProfile profile = ourProfiles.get(name);
        if (profile == null) {
            profile = new ColorProfile(name);
            ourProfiles.put(name, profile);
        }
        return profile;
    }
}