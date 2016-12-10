package com.songwenju.customtvrecyclerview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * author songwenju
 * email：songwenju@outlook.com
 * 针对电视的自定义的RecyclerView，该RecyclerView具有以下功能：
 * 1.响应五向键，上下左右会跟着移动，并获得焦点，在获得焦点时会抬高
 * 2.在鼠标hover在条目上时会获得焦点。
 * 3.添加了条目的点击和长按事件
 * 4.添加了是否第一个可见条目和是否是最后一个可见条目的方法
 * 5.在item获得焦点时和失去焦点时，这里有相应的回调方法。
 */
public class CustomRecyclerView extends RecyclerView {

    public CustomRecyclerView(Context context) {
        super(context);
    }

    public CustomRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //在recyclerView的move事件情况下，拦截调，只让它响应五向键和左右箭头移动
        return ev.getAction() == MotionEvent.ACTION_MOVE || super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int dx = this.getChildAt(0).getWidth();
        View focusView = this.getFocusedChild();
        if (focusView != null) {
            //处理左右方向键移动Item到边之后RecyclerView跟着移动
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    LogUtil.i(this, "CustomRecyclerView.KEYCODE_DPAD_RIGHT.");
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        return true;
                    } else {
                        View rightView = FocusFinder.getInstance().findNextFocus(this, focusView, View.FOCUS_RIGHT);
                        LogUtil.i(this, "rightView is null:" + (rightView == null));
                        if (rightView != null) {
                            LogUtil.i(this, "CustomRecyclerView.requestFocusFromTouch.");
                            rightView.requestFocusFromTouch();
                            return true;
                        } else {
                            this.smoothScrollBy(dx, 0);
                            //移动之后获得焦点，是在scroll方法中处理的。
                            return true;
                        }
                    }
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    View leftView = FocusFinder.getInstance().findNextFocus(this, focusView, View.FOCUS_LEFT);
                    LogUtil.i(this, "left is null:" + (leftView == null));
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        return true;
                    } else {
                        if (leftView != null) {
                            leftView.requestFocusFromTouch();
                            return true;
                        } else {
                            this.smoothScrollBy(-dx, 0);
                            return true;
                        }
                    }
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        LogUtil.i(this, "CustomRecyclerView.onScrolled.");
        super.onScrolled(dx, dy);
        //响应五向键，在Scroll时去获得下一个焦点
        final View focusView = this.getFocusedChild();
        if (focusView != null) {
            if (dx > 0) {
                View rightView = FocusFinder.getInstance().findNextFocus(this, focusView, View.FOCUS_RIGHT);
                if (rightView != null) {
                    rightView.requestFocusFromTouch();
                }
            } else {
                View rightView = FocusFinder.getInstance().findNextFocus(this, focusView, View.FOCUS_LEFT);
                if (rightView != null) {
                    rightView.requestFocusFromTouch();
                }
            }
        }

    }

    /**
     * 第一个条目是否可见
     *
     * @return 可见返回true，不可见返回false
     */
    public boolean isFirstItemVisible() {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] firstVisibleItems = null;
            firstVisibleItems = ((StaggeredGridLayoutManager) layoutManager).
                    findFirstCompletelyVisibleItemPositions(firstVisibleItems);
            int position = firstVisibleItems[0];
            return position == 0;
        } else if (layoutManager instanceof LinearLayoutManager) {
            int position = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
            return position == 0;
        }
        return false;
    }


    /**
     * 最后一个条目是否可见
     *
     * @param lineNum    行数
     * @param allItemNum item总数
     * @return 可见返回true，不可见返回false
     */
    public boolean isLastItemVisible(int lineNum, int allItemNum) {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItems = null;
            lastVisibleItems = ((StaggeredGridLayoutManager) layoutManager).findLastCompletelyVisibleItemPositions(lastVisibleItems);
            int position = lastVisibleItems[0];
            LogUtil.i(this, "lastVisiblePosition:" + position);
            for (int i = 0; i < lastVisibleItems.length; i++) {
                LogUtil.i(this, "order:"+i +"----->last position:" + lastVisibleItems[i]);
            }
            boolean isVisible = position >= (allItemNum - lineNum);
            if (isVisible) {
                scrollBy(1, 0);
            }
            return isVisible;
        } else if (layoutManager instanceof LinearLayoutManager) {
            int position = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
            return position == allItemNum - 1;
        }
        return false;
    }

    /**
     * 自定义的RecyclerView Adapter，他实现了hover获得焦点，放大的效果。
     * 实现了点击事件和长按点击事件。
     *
     * @param <T>
     */
    public static abstract class CustomAdapter<T> extends Adapter<ViewHolder> {
        private LayoutInflater mInflater;
        private List<T> mData;

        public interface OnItemClickListener {
            void onItemClick(View view, int position);

            void onItemLongClick(View view, int position);
        }


        private OnItemClickListener mListener;

        public void setOnItemClickListener(OnItemClickListener listener) {
            mListener = listener;
        }


        public CustomAdapter(Context context, List<T> data) {
            mInflater = LayoutInflater.from(context);
            mData = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(onSetItemLayout(), parent, false);
            return onSetViewHolder(view);
        }

        protected abstract ViewHolder onSetViewHolder(View view);

        /**
         * 设置item的layout
         *
         * @return item对应的layout
         */
        protected abstract
        @NonNull
        int onSetItemLayout();

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            onSetItemData(holder, position);
            //item可以获得焦点，需要设置这个属性。
            holder.itemView.setFocusable(true);
            holder.itemView.setOnHoverListener(new View.OnHoverListener() {
                @Override
                public boolean onHover(View v, MotionEvent event) {
                    int what = event.getAction();
                    switch (what) {
                        case MotionEvent.ACTION_HOVER_ENTER:
                            //鼠标进入view，争取到焦点
                            v.requestFocusFromTouch();
                            v.requestFocus();
                            focusStatus(v);
                            break;
                        case MotionEvent.ACTION_HOVER_MOVE:  //鼠标在view上移动
                            break;
                        case MotionEvent.ACTION_HOVER_EXIT:  //鼠标离开view
                            normalStatus(v);
                            break;
                    }
                    return false;
                }
            });

            holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        focusStatus(v);
                    } else {
                        normalStatus(v);
                    }
                }
            });

            if (mListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onItemClick(v, holder.getLayoutPosition());
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mListener.onItemLongClick(v, holder.getLayoutPosition());
                        return true;
                    }
                });
            }
        }

        /**
         * 为Item的内容设置数据
         *
         * @param viewHolder viewHolder
         * @param position   位置
         */
        protected abstract void onSetItemData(ViewHolder viewHolder, int position);

        /**
         * item获得焦点时调用
         *
         * @param itemView view
         */
        private void focusStatus(View itemView) {
            if (itemView == null) {
                return;
            }

            if (Build.VERSION.SDK_INT >= 21) {
                //抬高Z轴
                ViewCompat.animate(itemView).scaleX(1.10f).scaleY(1.10f).translationZ(1).start();
            } else {
                ViewCompat.animate(itemView).scaleX(1.10f).scaleY(1.10f).start();
                ViewGroup parent = (ViewGroup) itemView.getParent();
                parent.requestLayout();
                parent.invalidate();
            }
            onItemFocus(itemView);
        }

        /**
         * 当item获得焦点时处理
         *
         * @param itemView itemView
         */
        protected abstract void onItemFocus(View itemView);


        /**
         * item失去焦点时
         *
         * @param itemView item对应的View
         */
        private void normalStatus(View itemView) {
            if (itemView == null) {
                return;
            }

            if (Build.VERSION.SDK_INT >= 21) {
                ViewCompat.animate(itemView).scaleX(1.0f).scaleY(1.0f).translationZ(0).start();
            } else {
                ViewCompat.animate(itemView).scaleX(1.0f).scaleY(1.0f).start();
                ViewGroup parent = (ViewGroup) itemView.getParent();
                parent.requestLayout();
                parent.invalidate();
            }
            onItemGetNormal(itemView);

        }

        /**
         * 当条目失去焦点时调用
         *
         * @param itemView 条目对应的View
         */
        protected abstract void onItemGetNormal(View itemView);

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }
}
