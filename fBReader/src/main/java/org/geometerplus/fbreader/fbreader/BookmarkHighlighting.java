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

package org.geometerplus.fbreader.fbreader;

import org.geometerplus.zlibrary.core.util.ZLColor;

import org.geometerplus.zlibrary.text.view.*;

import org.geometerplus.fbreader.book.*;

public final class BookmarkHighlighting extends ZLTextSimpleHighlighting {

    public final Bookmark Bookmark;
    /**
     * 使用默认颜色配置
     */
    public final boolean USE_DEFAULT = true;
    final IBookCollection Collection;

    BookmarkHighlighting(ZLTextView view, IBookCollection collection, Bookmark bookmark) {
        super(view, startPosition(bookmark), endPosition(bookmark));
        Collection = collection;
        Bookmark = bookmark;
    }

    private static ZLTextPosition startPosition(Bookmark bookmark) {
        return new ZLTextFixedPosition(bookmark.getParagraphIndex(), bookmark.getElementIndex(), 0);
    }

    private static ZLTextPosition endPosition(Bookmark bookmark) {
        final ZLTextPosition end = bookmark.getEnd();
        if (end != null) {
            return end;
        }
        // TODO: compute end and save bookmark
        return bookmark;
    }

    @Override
    public ZLColor getForegroundColor() {
        if (USE_DEFAULT) {
            return View.getHighlightingForegroundColor();
        }
        final HighlightingStyle bmStyle = Collection.getHighlightingStyle(Bookmark.getStyleId());
        return bmStyle != null ? bmStyle.getForegroundColor() : null;
    }

    @Override
    public ZLColor getBackgroundColor() {
        if (USE_DEFAULT) {
            return View.getHighlightingBackgroundColor();
        }
        final HighlightingStyle bmStyle = Collection.getHighlightingStyle(Bookmark.getStyleId());
        return bmStyle != null ? bmStyle.getBackgroundColor() : null;
    }

    @Override
    public ZLColor getOutlineColor() {
        return null;
    }
}