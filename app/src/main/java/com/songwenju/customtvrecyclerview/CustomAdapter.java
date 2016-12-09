package com.songwenju.customtvrecyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends CustomRecyclerView.CustomAdapter<Integer> {

    public CustomAdapter(Context context, List<Integer> data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder onSetViewHolder(View view) {
        return new GalleryViewHolder(view);
    }

    @NonNull
    @Override
    protected int onSetItemLayout() {
        return R.layout.item;
    }

    @Override
    protected void onSetItemData(RecyclerView.ViewHolder viewHolder, int position) {
        GalleryViewHolder holder = (GalleryViewHolder) viewHolder;
        holder.tv.setText("17TV:"+position);
    }


    @Override
    protected void onItemFocus(View itemView) {
        TextView tvFocus = (TextView) itemView.findViewById(R.id.tv_focus);
        ImageView focusBg = (ImageView) itemView.findViewById(R.id.focus_bg);

        tvFocus.setVisibility(View.VISIBLE);
        focusBg.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onItemGetNormal(View itemView) {
        TextView tvFocus = (TextView) itemView.findViewById(R.id.tv_focus);
        ImageView focusBg = (ImageView) itemView.findViewById(R.id.focus_bg);

        tvFocus.setVisibility(View.VISIBLE);
        focusBg.setVisibility(View.INVISIBLE);
    }

    private class GalleryViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView iv;

        GalleryViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_focus);
            iv = (ImageView) itemView.findViewById(R.id.im);
        }
    }
}
