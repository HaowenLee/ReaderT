package org.geometerplus.android.fbreader.ui;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.geometerplus.android.fbreader.BaseFragment;
import org.geometerplus.android.fbreader.FBReader;
import org.geometerplus.android.fbreader.api.FBReaderIntents;
import org.geometerplus.android.fbreader.bookmark.EditBookmarkActivity;
import org.geometerplus.android.fbreader.libraryService.BookCollectionShadow;
import org.geometerplus.android.util.OrientationUtil;
import org.geometerplus.android.util.UIMessageUtil;
import org.geometerplus.android.util.ViewUtil;
import org.geometerplus.fbreader.book.Book;
import org.geometerplus.fbreader.book.BookEvent;
import org.geometerplus.fbreader.book.Bookmark;
import org.geometerplus.fbreader.book.BookmarkQuery;
import org.geometerplus.fbreader.book.HighlightingStyle;
import org.geometerplus.fbreader.book.IBookCollection;
import org.geometerplus.fbreader.bookmodel.TOCTree;
import org.geometerplus.fbreader.fbreader.FBReaderApp;
import org.geometerplus.zlibrary.core.application.ZLApplication;
import org.geometerplus.zlibrary.core.resources.ZLResource;
import org.geometerplus.zlibrary.ui.android.R;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BookMarkFragment extends BaseFragment implements IBookCollection.Listener<Book> {

    private static final int OPEN_ITEM_ID = 0;
    private static final int EDIT_ITEM_ID = 1;
    private static final int DELETE_ITEM_ID = 2;
    private final Map<Integer, HighlightingStyle> myStyles =
            Collections.synchronizedMap(new HashMap<>());
    private final BookCollectionShadow myCollection = new BookCollectionShadow();
    private final Comparator<Bookmark> myComparator = new Bookmark.ByTimeComparator();
    private final ZLResource myResource = ZLResource.resource("bookmarksView");
    private final Object myBookmarksLock = new Object();
    private volatile Book myBook;
    private volatile BookmarksAdapter myThisBookAdapter;

    private TOCTree root;

    @Override
    protected void initData() {
        super.initData();

        mActivity.setDefaultKeyMode(Activity.DEFAULT_KEYS_SEARCH_LOCAL);

        final SearchManager manager = (SearchManager) mActivity.getSystemService(Activity.SEARCH_SERVICE);
        manager.setOnCancelListener(null);

        myBook = ((FBReader) mActivity).getMyFBReaderApp().getCurrentBook();
        if (myBook == null) {
            ((FBReader) mActivity).closeSlideMenu();
        }

        myCollection.bindToService(mActivity, () -> {
            myThisBookAdapter = new BookmarksAdapter(getView().findViewById(R.id.listView));
            myCollection.addListener(BookMarkFragment.this);

            updateStyles();
            loadBookmarks();
        });

        final FBReaderApp fbReader = (FBReaderApp) ZLApplication.Instance();
        // 获取目录索引树
        root = fbReader.Model.TOCTree;
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.reader_fragment_book_mark_list;
    }

    private void updateStyles() {
        synchronized (myStyles) {
            myStyles.clear();
            for (HighlightingStyle style : myCollection.highlightingStyles()) {
                myStyles.put(style.Id, style);
            }
        }
    }

    private void loadBookmarks() {
        new Thread(new Runnable() {
            public void run() {
                synchronized (myBookmarksLock) {
                    for (BookmarkQuery query = new BookmarkQuery(myBook, Bookmark.Type.BookMark.ordinal(), 50); ; query = query.next()) {
                        final List<Bookmark> thisBookBookmarks = myCollection.bookmarks(query);
                        if (thisBookBookmarks.isEmpty()) {
                            break;
                        }
                        // 根据段落索引获取章节标题，并赋值
                        for (int i = 0; i < thisBookBookmarks.size(); i++) {
                            Bookmark bookmark = thisBookBookmarks.get(i);
                            int paragraphIndex = bookmark.getParagraphIndex();
                            String tocText = getTocText(paragraphIndex);
                            bookmark.setTocText(tocText);
                        }
                        myThisBookAdapter.addAll(thisBookBookmarks);
                    }
                }
            }
        }).start();
    }

    /**
     * 根据段落索引获取章节标题
     *
     * @param paragraphIndex 段落索引
     * @return 章节标题
     */
    private String getTocText(int paragraphIndex) {
        if (root == null) {
            return "";
        }
        TOCTree treeToSelect = null;
        for (TOCTree tree : root) {
            final TOCTree.Reference reference = tree.getReference();
            if (reference == null) {
                continue;
            }
            if (reference.ParagraphIndex > paragraphIndex) {
                break;
            }
            treeToSelect = tree;
        }
        return treeToSelect == null ? "" : treeToSelect.getText();
    }

    @Override
    public void onDestroyView() {
        myCollection.unbind();
        super.onDestroyView();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() != OPEN_ITEM_ID && item.getItemId() != EDIT_ITEM_ID && item.getItemId() != DELETE_ITEM_ID) {
            return false;
        }
        final int position = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
        final Bookmark bookmark = myThisBookAdapter.getItem(position);
        switch (item.getItemId()) {
            case OPEN_ITEM_ID:
                gotoBookmark(bookmark);
                return true;
            case EDIT_ITEM_ID:
                final Intent intent = new Intent(mActivity, EditBookmarkActivity.class);
                FBReaderIntents.putBookmarkExtra(intent, bookmark);
                OrientationUtil.startActivity(mActivity, intent);
                return true;
            case DELETE_ITEM_ID:
                myCollection.deleteBookmark(bookmark);
                return true;
        }
        return false;
    }

    private void gotoBookmark(Bookmark bookmark) {
        bookmark.markAsAccessed();
        myCollection.saveBookmark(bookmark);
        final Book book = myCollection.getBookById(bookmark.BookId);
        if (book != null) {
            FBReader.openBookActivity(mActivity, book, bookmark);
        } else {
            UIMessageUtil.showErrorMessage(mActivity, "cannotOpenBook");
        }
    }

    public void onBookEvent(BookEvent event, Book book) {
        if (event == BookEvent.BookMarkUpdated) {
            updateBookmarks(book);
        }
    }

    /**
     * 更新书签
     *
     * @param book 书籍对象
     */
    private void updateBookmarks(final Book book) {
        new Thread(() -> {
            synchronized (myBookmarksLock) {
                // 是否是当前的书
                final boolean flagThisBookTab = book.getId() == myBook.getId();

                // 暂存列表里旧数据
                final Map<String, Bookmark> oldBookmarks = new HashMap<>();
                if (flagThisBookTab) {
                    for (Bookmark b : myThisBookAdapter.bookmarks()) {
                        oldBookmarks.put(b.Uid, b);
                    }
                }

                // 查询数据库的书签数据
                for (BookmarkQuery query = new BookmarkQuery(book, Bookmark.Type.BookMark.ordinal(), 50); ; query = query.next()) {
                    final List<Bookmark> loaded = myCollection.bookmarks(query);
                    if (loaded.isEmpty()) {
                        break;
                    }
                    for (Bookmark b : loaded) {
                        // 暂存数据移除远程数据里的标签
                        final Bookmark old = oldBookmarks.remove(b.Uid);
                        if (flagThisBookTab) {
                            // 更新列表书签数据
                            myThisBookAdapter.replace(old, b);
                        }
                    }
                }

                if (flagThisBookTab) {
                    // 移除列表里，远程数据已移除数据
                    myThisBookAdapter.removeAll(oldBookmarks.values());
                }
            }
        }).start();
    }

    // method from IBookCollection.Listener
    public void onBuildEvent(IBookCollection.Status status) {
    }

    private final class BookmarksAdapter extends BaseAdapter implements AdapterView.OnItemClickListener, View.OnCreateContextMenuListener {
        private final List<Bookmark> myBookmarksList =
                Collections.synchronizedList(new LinkedList<>());

        BookmarksAdapter(ListView listView) {
            listView.setAdapter(this);
            listView.setOnItemClickListener(this);
            listView.setOnCreateContextMenuListener(this);
        }

        public List<Bookmark> bookmarks() {
            return Collections.unmodifiableList(myBookmarksList);
        }

        public void addAll(final List<Bookmark> bookmarks) {
            mActivity.runOnUiThread(() -> {
                synchronized (myBookmarksList) {
                    for (Bookmark b : bookmarks) {
                        final int position = Collections.binarySearch(myBookmarksList, b, myComparator);
                        if (position < 0) {
                            myBookmarksList.add(-position - 1, b);
                        }
                    }
                }
                notifyDataSetChanged();
            });
        }

        /**
         * 更新数据
         *
         * @param old 旧数据
         * @param b   新数据
         */
        public void replace(final Bookmark old, final Bookmark b) {
            // 如果有旧数据，并且相同 --> 返回
            if (old != null && areEqualsForView(old, b)) {
                return;
            }
            mActivity.runOnUiThread(() -> {
                synchronized (myBookmarksList) {
                    // 有旧数据则移除
                    if (old != null) {
                        myBookmarksList.remove(old);
                    }
                    // 查找新数据在列表里的位置，如果不存在则添加该数据到列表集合
                    final int position = Collections.binarySearch(myBookmarksList, b, myComparator);
                    if (position < 0) {
                        // 查询章节标题，并设置
                        String tocText = getTocText(b.ParagraphIndex);
                        b.setTocText(tocText);
                        myBookmarksList.add(-position - 1, b);
                    }
                }
                // 通知UI更新
                notifyDataSetChanged();
            });
        }

        /**
         * 比对书签是否相同
         *
         * @param b0 书签0
         * @param b1 书签1
         * @return 书签是否相同
         */
        private boolean areEqualsForView(Bookmark b0, Bookmark b1) {
            return
                    b0.getStyleId() == b1.getStyleId() &&
                            b0.getText().equals(b1.getText()) &&
                            b0.getTimestamp(Bookmark.DateType.Latest).equals(b1.getTimestamp(Bookmark.DateType.Latest));
        }

        public void removeAll(final Collection<Bookmark> bookmarks) {
            if (bookmarks.isEmpty()) {
                return;
            }
            mActivity.runOnUiThread(() -> {
                myBookmarksList.removeAll(bookmarks);
                notifyDataSetChanged();
            });
        }

        public void clear() {
            mActivity.runOnUiThread(() -> {
                myBookmarksList.clear();
                notifyDataSetChanged();
            });
        }

        public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
            final int position = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
            if (getItem(position) != null) {
                menu.add(0, OPEN_ITEM_ID, 0, myResource.getResource("openBook").getValue());
                menu.add(0, EDIT_ITEM_ID, 0, myResource.getResource("editBookmark").getValue());
                menu.add(0, DELETE_ITEM_ID, 0, myResource.getResource("deleteBookmark").getValue());
            }
        }

        @Override
        public final boolean areAllItemsEnabled() {
            return true;
        }

        @Override
        public final boolean isEnabled(int position) {
            return true;
        }

        public final void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final Bookmark bookmark = getItem(position);
            if (bookmark != null) {
                ((FBReader) mActivity).closeSlideMenu();
                gotoBookmark(bookmark);
            }
        }

        @Override
        public final int getCount() {
            return myBookmarksList.size();
        }

        @Override
        public final Bookmark getItem(int position) {
            return position >= 0 ? myBookmarksList.get(position) : null;
        }

        @Override
        public final long getItemId(int position) {
            final Bookmark item = getItem(position);
            return item != null ? item.getId() : -1;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final View view = (convertView != null) ? convertView :
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.reader_item_list_book_mark, parent, false);
            final View layoutTitle = ViewUtil.findView(view, R.id.llTitle);
            final TextView tvTitle = ViewUtil.findTextView(view, R.id.tvTitle);
            final TextView tvContent = ViewUtil.findTextView(view, R.id.tvContent);
            final View viewDivider = ViewUtil.findView(view, R.id.viewDivider);

            final Bookmark bookmark = getItem(position);

            // 章节标题和内容的判读逻辑（如果和上一条的章节标题一样，就认为是同一章节内容）
            if (bookmark != null) {
                if (position > 0) {
                    Bookmark item = getItem(position - 1);
                    if (item != null && TextUtils.equals(item.getTocText(), bookmark.getTocText())) {
                        layoutTitle.setVisibility(View.GONE);
                        viewDivider.setVisibility(View.GONE);
                    } else {
                        layoutTitle.setVisibility(View.VISIBLE);
                        viewDivider.setVisibility(View.VISIBLE);
                    }
                } else {
                    layoutTitle.setVisibility(View.VISIBLE);
                    viewDivider.setVisibility(View.VISIBLE);
                }
                tvTitle.setText(bookmark.getTocText());
                tvContent.setText(bookmark.getText());
            }
            return view;
        }
    }
}