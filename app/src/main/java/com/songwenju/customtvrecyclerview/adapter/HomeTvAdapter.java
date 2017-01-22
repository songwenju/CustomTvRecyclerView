package com.songwenju.customtvrecyclerview.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.songwenju.customtvrecyclerview.widget.CustomRecyclerView;
import com.songwenju.customtvrecyclerview.util.LogUtil;
import com.songwenju.customtvrecyclerview.R;

import java.util.List;

public class HomeTvAdapter extends CustomRecyclerView.CustomAdapter<Integer> {

    public HomeTvAdapter(Context context, List<Integer> data) {
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
        holder.tv.setText("17TV:" + position);
    }


    @Override
    protected void onItemFocus(View itemView, int position) {
        TextView tvFocus = (TextView) itemView.findViewById(R.id.tv_focus);
        ImageView focusBg = (ImageView) itemView.findViewById(R.id.focus_bg);

        tvFocus.setVisibility(View.VISIBLE);
        focusBg.setVisibility(View.VISIBLE);

        if (Build.VERSION.SDK_INT >= 21) {
            //抬高Z轴
            ViewCompat.animate(itemView).scaleX(1.10f).scaleY(1.10f).translationZ(1).start();
        } else {
            ViewCompat.animate(itemView).scaleX(1.10f).scaleY(1.10f).start();
            ViewGroup parent = (ViewGroup) itemView.getParent();
            parent.requestLayout();
            parent.invalidate();
        }
    }

    @Override
    protected void onItemGetNormal(View itemView, int position) {

        TextView tvFocus = (TextView) itemView.findViewById(R.id.tv_focus);
        ImageView focusBg = (ImageView) itemView.findViewById(R.id.focus_bg);

        tvFocus.setVisibility(View.VISIBLE);
        focusBg.setVisibility(View.INVISIBLE);

        if (Build.VERSION.SDK_INT >= 21) {
            ViewCompat.animate(itemView).scaleX(1.0f).scaleY(1.0f).translationZ(0).start();
        } else {
            LogUtil.i(this, "HomeTvAdapter.normalStatus.scale build version < 21");
            ViewCompat.animate(itemView).scaleX(1.0f).scaleY(1.0f).start();
            ViewGroup parent = (ViewGroup) itemView.getParent();
            parent.requestLayout();
            parent.invalidate();
        }
    }

    @Override
    protected int getCount() {
        return mData.size();
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
