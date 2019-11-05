package org.geometerplus.android.fbreader.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.geometerplus.android.fbreader.ui.dialog.BottomListDialog;
import org.geometerplus.zlibrary.ui.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作    者：Herve、Li
 * 创建日期：2019/11/1
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class BottomListAdapter<T> extends RecyclerView.Adapter<BottomListAdapter.Holder> {

    private LayoutInflater inflater;
    private List<Pair<String, String>> mData = new ArrayList<>();
    private OnItemCheckListener onItemCheckListener;
    private Pair<String, String> itemData;

    public BottomListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public List<Pair<String, String>> getData() {
        if (mData == null) {
            return new ArrayList<>();
        }
        return mData;
    }

    public void setData(List<Pair<String, String>> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void setCheckItem(Pair<String, String> itemData) {
        this.itemData = itemData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(inflater.inflate(R.layout.item_bottom_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BottomListAdapter.Holder holder, int position) {
        holder.bindData(mData.get(position));
        holder.bindListener();
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public Pair<String, String> getItem(int position) {
        return mData.get(position);
    }

    public void setOnItemCheckListener(OnItemCheckListener listener) {
        this.onItemCheckListener = listener;
    }

    public interface OnItemCheckListener {
        void onItemCheck(int position);
    }

    class Holder extends RecyclerView.ViewHolder {

        Holder(@NonNull View itemView) {
            super(itemView);
        }

        void bindListener() {
            itemView.setOnClickListener(v -> {
                if (onItemCheckListener != null) {
                    onItemCheckListener.onItemCheck(getLayoutPosition());
                }
            });
        }

        void bindData(Pair<String, String> itemData) {
            ((TextView) itemView).setText(itemData.first);
            if (BottomListAdapter.this.itemData == itemData) {
                ((TextView) itemView).setTextColor(Color.parseColor("#FF6B00"));
            } else {
                ((TextView) itemView).setTextColor(Color.parseColor("#333333"));
            }
        }
    }
}
