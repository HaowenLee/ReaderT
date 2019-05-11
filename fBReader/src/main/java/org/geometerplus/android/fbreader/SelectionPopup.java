package org.geometerplus.android.fbreader;

import android.view.View;
import android.widget.RelativeLayout;

import org.geometerplus.zlibrary.core.library.ZLibrary;
import org.geometerplus.zlibrary.core.resources.ZLResource;
import org.geometerplus.fbreader.fbreader.ActionCode;
import org.geometerplus.fbreader.fbreader.FBReaderApp;
import org.geometerplus.zlibrary.ui.android.R;

/**
 * 选中动作弹框
 */
class SelectionPopup extends PopupPanel implements View.OnClickListener {

    final static String ID = "SelectionPopup";

    SelectionPopup(FBReaderApp fbReader) {
        super(fbReader);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void createControlPanel(FBReader activity, RelativeLayout root) {
        if (myWindow != null && activity == myWindow.getContext()) {
            return;
        }

        activity.getLayoutInflater().inflate(R.layout.selection_panel, root);
        myWindow = root.findViewById(R.id.selection_panel);

        final ZLResource resource = ZLResource.resource("selectionPopup");
        setupButton(R.id.selection_panel_copy, resource.getResource("copyToClipboard").getValue());
        setupButton(R.id.selection_panel_share, resource.getResource("share").getValue());
        setupButton(R.id.selection_panel_translate, resource.getResource("translate").getValue());
        setupButton(R.id.selection_panel_bookmark, resource.getResource("bookmark").getValue());
    }

    private void setupButton(int buttonId, String description) {
        final View button = myWindow.findViewById(buttonId);
        button.setOnClickListener(this);
        button.setContentDescription(description);
    }

    public void move(int selectionStartY, int selectionEndY) {

        if (myWindow == null) {
            return;
        }

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        // Popup的高度
        int popupHeight = (int) (ZLibrary.Instance().getDisplayDPI() / 160f * 65 + 57);
        myWindow.post(new Runnable() {
            @Override
            public void run() {

            }
        });
        int startY = selectionStartY - popupHeight;
        if (startY > 0) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.topMargin = startY;
            myWindow.setBackgroundResource(R.drawable.reader_window_background_above);
        } else if (selectionEndY + popupHeight < ((View) myWindow.getParent()).getHeight()) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.topMargin = selectionEndY;
            myWindow.setBackgroundResource(R.drawable.reader_window_background_below);
        } else {
            layoutParams.topMargin = 0;
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            myWindow.setBackgroundResource(R.drawable.reader_window_background_above);
        }

        myWindow.setLayoutParams(layoutParams);
    }

    @Override
    protected void update() {
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.selection_panel_bookmark:
                Application.runAction(ActionCode.SELECTION_BOOKMARK);
                break;
            case R.id.selection_panel_translate:
                Application.runAction(ActionCode.SELECTION_TRANSLATE);
                break;
            case R.id.selection_panel_share:
                Application.runAction(ActionCode.SELECTION_SHARE);
                break;
            case R.id.selection_panel_copy:
                Application.runAction(ActionCode.SELECTION_COPY_TO_CLIPBOARD);
                break;
        }
        Application.hideActivePopup();
    }
}