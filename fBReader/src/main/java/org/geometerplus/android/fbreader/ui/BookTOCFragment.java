package org.geometerplus.android.fbreader.ui;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.geometerplus.android.fbreader.BaseFragment;
import org.geometerplus.android.fbreader.FBReader;
import org.geometerplus.android.fbreader.ZLTreeAdapter;
import org.geometerplus.android.util.ViewUtil;
import org.geometerplus.fbreader.bookmodel.TOCTree;
import org.geometerplus.fbreader.fbreader.FBReaderApp;
import org.geometerplus.zlibrary.core.application.ZLApplication;
import org.geometerplus.zlibrary.core.resources.ZLResource;
import org.geometerplus.zlibrary.core.tree.ZLTree;
import org.geometerplus.zlibrary.ui.android.R;

/**
 * 目录索引
 */
public class BookTOCFragment extends BaseFragment {

    /**
     * PROCESS_TREE_ITEM_ID --> 展开目录
     * READ_BOOK_ITEM_ID --> 跳转页面
     */
    private static final int PROCESS_TREE_ITEM_ID = 0;
    private static final int READ_BOOK_ITEM_ID = 1;

    private TOCAdapter myAdapter;
    private ZLTree<?> mySelectedItem;
    private ListView listView;

    @Override
    protected int initLayoutRes() {
        return R.layout.reader_fragment_toc;
    }

    @Override
    protected void initViews() {
        super.initViews();

        // 异常捕获
        Thread.setDefaultUncaughtExceptionHandler(new org.geometerplus.zlibrary.ui.android.library.UncaughtExceptionHandler(mActivity));

        if (getView() != null) {
            listView = getView().findViewById(R.id.listView);
        }

        initTree();
    }

    /**
     * 初始化目录索引树
     */
    public void initTree() {
        final FBReaderApp fbReader = (FBReaderApp) ZLApplication.Instance();
        if (fbReader == null) {
            return;
        }
        // 获取目录索引树
        final TOCTree root = fbReader.Model.TOCTree;
        myAdapter = new TOCAdapter(root);
        // 获取当前的目录索引
        mySelectedItem = fbReader.getCurrentTOCElement();
        myAdapter.selectItem(mySelectedItem);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final int position = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
        final TOCTree tree = (TOCTree) myAdapter.getItem(position);
        switch (item.getItemId()) {
            case PROCESS_TREE_ITEM_ID:
                myAdapter.runTreeItem(tree);
                return true;
            case READ_BOOK_ITEM_ID:
                myAdapter.openBookText(tree);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * 目录索引列表数据适配器
     */
    private final class TOCAdapter extends ZLTreeAdapter {

        TOCAdapter(TOCTree root) {
            super(listView, root);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
            final int position = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
            final TOCTree tree = (TOCTree) getItem(position);
            if (tree.hasChildren()) {
                menu.setHeaderTitle(tree.getText());
                final ZLResource resource = ZLResource.resource("tocView");
                menu.add(0, PROCESS_TREE_ITEM_ID, 0, resource.getResource(isOpen(tree) ? "collapseTree" : "expandTree").getValue());
                menu.add(0, READ_BOOK_ITEM_ID, 0, resource.getResource("readText").getValue());
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final View view = (convertView != null) ? convertView :
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.reader_toc_tree_item, parent, false);
            final TOCTree tree = (TOCTree) getItem(position);
            setIcon(ViewUtil.findImageView(view, R.id.toc_tree_item_icon), tree);
            ViewUtil.findTextView(view, R.id.toc_tree_item_text).setText(tree.getText());
            ViewUtil.findTextView(view, R.id.toc_tree_item_text).setTextColor(tree == mySelectedItem ? getTextColor(true) : getTextColor(false));
            return view;
        }

        private int getTextColor(boolean isChecked) {
            if (isChecked) {
                return getContext().getResources().getColor(R.color.reader_font_checked);
            } else {
                return getContext().getResources().getColor(R.color.reader_font_black);
            }
        }

        void openBookText(TOCTree tree) {
            final TOCTree.Reference reference = tree.getReference();
            if (reference != null) {
                ((FBReader) mActivity).closeSlideMenu();
                final FBReaderApp fbReader = (FBReaderApp) ZLApplication.Instance();
                fbReader.addInvisibleBookmark();
                fbReader.BookTextView.gotoPosition(reference.ParagraphIndex, 0, 0);
                fbReader.showBookTextView();
                fbReader.storePosition();
            }
        }

        @Override
        protected boolean runTreeItem(ZLTree<?> tree) {
            if (super.runTreeItem(tree)) {
                return true;
            }
            openBookText((TOCTree) tree);
            return true;
        }
    }
}