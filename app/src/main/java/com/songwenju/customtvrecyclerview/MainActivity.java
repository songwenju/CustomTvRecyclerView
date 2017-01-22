package com.songwenju.customtvrecyclerview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.songwenju.customtvrecyclerview.adapter.Constants;
import com.songwenju.customtvrecyclerview.adapter.HomeTvAdapter;
import com.songwenju.customtvrecyclerview.adapter.PopRecyclerAdapter;
import com.songwenju.customtvrecyclerview.util.LogUtil;
import com.songwenju.customtvrecyclerview.util.SpUtil;
import com.songwenju.customtvrecyclerview.widget.CustomRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private CustomRecyclerView mRecyclerView;
    private HomeTvAdapter mAdapter;
    private TextView mCategoryBtn;
    private List<Integer> mData;
    private Button mLeftArr;
    private Button mRightArr;
    private int totalWidth;
    private StaggeredGridLayoutManager mLayoutManager;
    public static final int LINE_NUM = 3;  //要显示的行数
    private PopupWindow mPopupWindow;
    private CustomRecyclerView mPopRecyclerView;
    private ImageButton mUpArr;
    private ImageButton mDownArr;

    private List<String> mChannelList;
    private PopRecyclerAdapter mPopRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();

    }

    private void setListener() {
        mCategoryBtn.setOnClickListener(this);
        //popWindow
        mUpArr.setOnClickListener(this);
        mDownArr.setOnClickListener(this);
        mPopRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mPopRecyclerAdapter = new PopRecyclerAdapter(mContext, mChannelList);
        mPopRecyclerView.setAdapter(mPopRecyclerAdapter);
        mPopRecyclerView.setLayoutAnimation(PopLayoutAnimation.orderAnimation());
        mPopRecyclerAdapter.setOnItemClickListener(new OnPopRecyclerItemClickListener());
        mPopRecyclerView.setOnScrollListener(new OnPopRecyclerViewScrollListener());
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        mContext = this;
        mRecyclerView = (CustomRecyclerView) findViewById(R.id.id_recycler_view);
        mLeftArr = (Button) findViewById(R.id.arr_left);
        mRightArr = (Button) findViewById(R.id.arr_right);
        mCategoryBtn = (TextView) findViewById(R.id.category_btn);

        mRightArr.setOnClickListener(this);
        mLeftArr.setOnClickListener(this);
        initData();
        mAdapter = new HomeTvAdapter(this, mData);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());



        //init popWindow
        View popupView = getLayoutInflater().inflate(R.layout.list_menu_popwindow, null, false);
        mPopRecyclerView = (CustomRecyclerView) popupView.findViewById(R.id.recycler_view);
        mUpArr = (ImageButton) popupView.findViewById(R.id.up_arrow);
        mDownArr = (ImageButton) popupView.findViewById(R.id.down_arrow);
        mPopupWindow = new PopupWindow(popupView, 404, 1920);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.update();


        //设置布局管理器
        mLayoutManager = new StaggeredGridLayoutManager(LINE_NUM, StaggeredGridLayoutManager.HORIZONTAL);
        mLayoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MyOnItemClickListener());
        mRecyclerView.setOnScrollListener(new MyOnScrollListener()

        );
    }

    private void initData() {
        totalWidth = getResources().getDimensionPixelSize(R.dimen.total_width);
        mData = new ArrayList<Integer>();
        for (int i = 0; i < 350; i++) {
            mData.add(i);
        }
        mChannelList = new ArrayList<>();

        mChannelList.add("少儿频道");
        mChannelList.add("音乐频道");
        mChannelList.add("电视剧频道");
        mChannelList.add("电影频道");
        mChannelList.add("新闻频道");
        mChannelList.add("综艺频道");
        mChannelList.add("体育频道");
        mChannelList.add("科教频道");
        mChannelList.add("国际频道");

        mCategoryBtn.setText(mChannelList.get(3));
        SpUtil.putInt(Constants.CATEGORY_POP_SELECT_POSITION, 3);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.arr_left:
                LogUtil.i(this, "MainActivity.onClick.leftArr");
                if (mRecyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
                    LogUtil.i(this, "MainActivity.onClick.smoothScrollBy-----" + -totalWidth);
                    mRecyclerView.smoothScrollBy(-totalWidth, 0);
                }
                break;
            case R.id.arr_right:
                LogUtil.i(this, "MainActivity.onClick.rightArr");
                if (mRecyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
                    LogUtil.i(this, "MainActivity.onClick.smoothScrollBy-----" + totalWidth);
                    mRecyclerView.smoothScrollBy(totalWidth, 0);
                }
            case R.id.up_arrow:
                LogUtil.i("swj", "MainActivity.onClick.upArrow");
                if (mPopRecyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
                    mPopRecyclerView.smoothScrollBy(0, -800);
                }
                break;
            case R.id.down_arrow:
                LogUtil.i("swj", "MainActivity.onClick.downArrow");
                if (mPopRecyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
                    mPopRecyclerView.smoothScrollBy(0, 800);
                }
                break;
            case R.id.category_btn:
                LogUtil.i("swj", "CategoryActivity.onClick.category_btn");
                // 以下拉的方式显示，并且可以设置显示的位置
                mPopRecyclerView.startLayoutAnimation();
                mPopupWindow.showAsDropDown(mCategoryBtn, 0, -150);

                break;
        }

    }

    /**
     * 设置左侧箭头的状态
     */
    private void setLeftArrStatus() {
        if (mRecyclerView.isFirstItemVisible()) {
            LogUtil.i(this, "fist can visit");
            mLeftArr.setVisibility(View.INVISIBLE);
        } else {
            mLeftArr.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置右侧箭头的状态
     */
    private void setRightArrStatus() {
        if (mRecyclerView.isLastItemVisible(LINE_NUM, mData.size())) {
            LogUtil.i(this, "last can visit");
            mRightArr.setVisibility(View.INVISIBLE);
        } else {
            mRightArr.setVisibility(View.VISIBLE);
        }
    }

    private class OnPopRecyclerItemClickListener implements CustomRecyclerView.CustomAdapter.OnItemClickListener {
        @Override
        public void onItemClick(View view, int position) {
            LogUtil.i("swj", "OnPopRecyclerItemClickListener.onItemClick.");
            SpUtil.putInt(Constants.CATEGORY_POP_SELECT_POSITION, position);
            mPopRecyclerAdapter.notifyDataSetChanged();
            mCategoryBtn.setText(mChannelList.get(position));
            //TODO 具体怎么滚动根据业务逻辑来
            mRecyclerView.smoothScrollToPosition(position * 12);
            mPopupWindow.dismiss();

        }

        @Override
        public void onItemLongClick(View view, int position) {

        }
    }

    //popWindow
    private class OnPopRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            //在滚动的时候处理箭头的状态
            setUpArrStatus();
            setDownArrStatus();
        }
    }

    /**
     * 设置上箭头的状态
     */
    private void setUpArrStatus() {
        if (mPopRecyclerView.isFirstItemVisible()) {
            mUpArr.setVisibility(View.INVISIBLE);
        } else {
            mUpArr.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 设置右侧箭头的状态
     */
    private void setDownArrStatus() {
        if (mPopRecyclerView.isLastItemVisible(1, mChannelList.size())) {
            LogUtil.i(this, "last can visit");
            mDownArr.setVisibility(View.INVISIBLE);
        } else {
            mDownArr.setVisibility(View.VISIBLE);
        }
    }


    private class MyOnItemClickListener implements HomeTvAdapter.OnItemClickListener {
        @Override
        public void onItemClick(View view, int position) {
            Toast.makeText(mContext, "click:" + position, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onItemLongClick(View view, int position) {
        }
    }


    private class MyOnScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            //在滚动的时候处理箭头的状态
            setLeftArrStatus();
            setRightArrStatus();
        }
    }
}
