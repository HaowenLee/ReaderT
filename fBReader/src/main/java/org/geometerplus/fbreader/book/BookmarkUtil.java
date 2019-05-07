package org.geometerplus.fbreader.book;

import org.geometerplus.zlibrary.core.resources.ZLResource;
import org.geometerplus.zlibrary.text.view.*;

public abstract class BookmarkUtil {

    public static String getStyleName(HighlightingStyle style) {
        final String name = style.getNameOrNull();
        return (name != null && name.length() > 0) ? name : defaultName(style);
    }

    private static String defaultName(HighlightingStyle style) {
        return ZLResource.resource("style").getValue().replace("%s", String.valueOf(style.Id));
    }

    public static void setStyleName(HighlightingStyle style, String name) {
        style.setName(defaultName(style).equals(name) ? null : name);
    }

    public static void findEnd(Bookmark bookmark, ZLTextView view) {
        if (bookmark.getEnd() != null) {
            return;
        }
        ZLTextWordCursor cursor = view.getStartCursor();
        if (cursor.isNull()) {
            cursor = view.getEndCursor();
        }
        if (cursor.isNull()) {
            return;
        }
        cursor = new ZLTextWordCursor(cursor);
        cursor.moveTo(bookmark);

        ZLTextWord word = null;
        mainLoop:
        for (int count = bookmark.getLength(); count > 0; cursor.nextWord()) {
            while (cursor.isEndOfParagraph()) {
                if (!cursor.nextParagraph()) {
                    break mainLoop;
                }
            }
            final ZLTextElement element = cursor.getElement();
            if (element instanceof ZLTextWord) {
                if (word != null) {
                    --count;
                }
                word = (ZLTextWord) element;
                count -= word.Length;
            }
        }
        if (word != null) {
            bookmark.setEnd(cursor.getParagraphIndex(), cursor.getElementIndex(), word.Length);
        }
    }
}
