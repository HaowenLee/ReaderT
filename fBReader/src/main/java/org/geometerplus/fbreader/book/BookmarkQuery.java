package org.geometerplus.fbreader.book;

/**
 * 标记查询
 */
public final class BookmarkQuery {

    public final AbstractBook Book;
    /**
     * 是否可见
     */
    public final boolean Visible;
    /**
     * 类型
     */
    public final int MarkType;
    /**
     * 字符数限制
     */
    public final int Limit;
    public final int Page;

    public BookmarkQuery(int limit) {
        this(null, -1, limit);
    }

    public BookmarkQuery(AbstractBook book, int markType, int limit) {
        this(book, markType, true, limit);
    }

    public BookmarkQuery(AbstractBook book, int markType, boolean visible, int limit) {
        this(book, visible, markType, limit, 0);
    }

    BookmarkQuery(AbstractBook book, boolean visible, int markType, int limit, int page) {
        Book = book;
        Visible = visible;
        MarkType = markType;
        Limit = limit;
        Page = page;
    }

    public BookmarkQuery next() {
        return new BookmarkQuery(Book, Visible, MarkType, Limit, Page + 1);
    }
}
