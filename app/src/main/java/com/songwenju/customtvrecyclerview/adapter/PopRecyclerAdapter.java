package com.songwenju.customtvrecyclerview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.songwenju.customtvrecyclerview.widget.CustomRecyclerView;
import com.songwenju.customtvrecyclerview.R;
import com.songwenju.customtvrecyclerview.util.SpUtil;

import java.util.List;

/**
 * songwenju on 16-12-23 : 10 : 13.
 * 邮箱：songwenju@outlook.com
 */

public class PopRecyclerAdapter extends CustomRecyclerView.CustomAdapter<String> {

    public PopRecyclerAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder onSetViewHolder(View view) {
        return new PopViewHolder(view);
    }

    @NonNull
    @Override
    protected int onSetItemLayout() {
        return R.layout.list_menu_popwindow_item;
    }

    @Override
    protected void onSetItemData(RecyclerView.ViewHolder viewHolder, int position) {
        PopViewHolder holder = (PopViewHolder) viewHolder;
        holder.tvItem.setText(mData.get(position));
        if (position == SpUtil.getInt(Constants.CATEGORY_POP_SELECT_POSITION, -1)) {
            holder.tvItem.setTextColor(mContext.getResources().getColor(R.color.orange));
        } else {
            holder.tvItem.setTextColor(mContext.getResources().getColor(R.color.common_white));
        }
    }

    @Override
    protected void onItemFocus(View itemView, int position) {
        RelativeLayout layout = (RelativeLayout) itemView.findViewById(R.id.item_contain);
        layout.setBackgroundResource(R.drawable.pop_item_focus_drawable);

        TextView itemText = (TextView) itemView.findViewById(R.id.tv_item);
        itemText.setTextColor(mContext.getResources().getColor(R.color.common_white));
    }

    @Override
    protected void onItemGetNormal(View itemView, int position) {
        RelativeLayout layout = (RelativeLayout) itemView.findViewById(R.id.item_contain);
        layout.setBackgroundResource(R.drawable.pop_item_unfocus_drawable);
        if (position == SpUtil.getInt(Constants.CATEGORY_POP_SELECT_POSITION, -1)) {
            TextView itemText = (TextView) itemView.findViewById(R.id.tv_item);
            itemText.setTextColor(mContext.getResources().getColor(R.color.orange));
        }
    }

    @Override
    protected int getCount() {
        return mData.size();
    }

    private class PopViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        public PopViewHolder(View itemView) {
            super(itemView);
            tvItem = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }
}
