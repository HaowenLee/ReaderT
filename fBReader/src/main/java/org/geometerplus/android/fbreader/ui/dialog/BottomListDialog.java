package org.geometerplus.android.fbreader.ui.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.geometerplus.android.fbreader.ui.adapter.BottomListAdapter;
import org.geometerplus.zlibrary.ui.android.R;

import java.util.List;

/**
 * ================================================
 * 作    者：Herve、Li
 * 创建日期：2019/11/1
 * 描    述：底部列表Dialog
 * 修订历史：
 * ================================================
 */
public class BottomListDialog extends BottomSheetDialog {

    private BottomListAdapter<Pair<String, String>> mAdapter;
    private OnCheckListener onCheckListener;

    public BottomListDialog(@NonNull Context context, List<Pair<String, String>> data) {
        super(context);
        setContentView(R.layout.dialog_bottom_list);

        initViews(data);
        initListeners();
    }

    private void initViews(List<Pair<String, String>> data) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        mAdapter = new BottomListAdapter<>(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
        mAdapter.setData(data);
    }

    private void initListeners() {
        mAdapter.setOnItemCheckListener(position -> {
            Pair<String, String> itemData = mAdapter.getItem(position);
            if (onCheckListener != null) {
                onCheckListener.onCheck(itemData.second);
            }
        });
    }

    public void setOnCheckListener(OnCheckListener onCheckListener) {
        this.onCheckListener = onCheckListener;
    }

    public void show(String value) {

        for (Pair<String, String> itemData : mAdapter.getData()) {
            if (TextUtils.equals(itemData.second, value)) {
                mAdapter.setCheckItem(itemData);
                break;
            }
        }
        show();
    }

    public interface OnCheckListener {
        void onCheck(String value);
    }
}