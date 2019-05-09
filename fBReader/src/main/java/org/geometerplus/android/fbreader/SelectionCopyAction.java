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

package org.geometerplus.android.fbreader;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;

import org.geometerplus.zlibrary.core.resources.ZLResource;

import org.geometerplus.fbreader.fbreader.FBReaderApp;
import org.geometerplus.fbreader.fbreader.FBView;
import org.geometerplus.fbreader.util.TextSnippet;

import org.geometerplus.android.util.UIMessageUtil;

/**
 * 复制选中内容
 */
public class SelectionCopyAction extends FBAndroidAction {

    SelectionCopyAction(FBReader baseActivity, FBReaderApp fbReader) {
        super(baseActivity, fbReader);
    }

    @Override
    protected void run(Object... params) {
        // 判断是否有选中内容
        final FBView fbview = Reader.getTextView();
        final TextSnippet snippet = fbview.getSelectedSnippet();
        if (snippet == null) {
            return;
        }
        // 获取选中内容
        final String text = snippet.getText();

        // 复制到剪切板
        final ClipboardManager clipboard = (ClipboardManager) BaseActivity.getApplication().getSystemService(Application.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newPlainText(null, text));
        UIMessageUtil.showMessageText(
                BaseActivity,
                ZLResource.resource("selection").getResource("textInBuffer").getValue().replace("%s", "")
        );

        // 清除选中
        fbview.clearSelection();
    }
}
