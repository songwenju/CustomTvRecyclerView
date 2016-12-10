package com.songwenju.customtvrecyclerview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private CustomRecyclerView mRecyclerView;
    private CustomAdapter mAdapter;
    private List<Integer> mData;
    private Button mLeftArr;
    private Button mRightArr;
    private int totalWidth;
    private StaggeredGridLayoutManager mLayoutManager;
    public static final int LINE_NUM = 3;  //要显示的行数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        mContext = this;
        mRecyclerView = (CustomRecyclerView) findViewById(R.id.id_recycler_view);
        mLeftArr = (Button) findViewById(R.id.arr_left);
        mRightArr = (Button) findViewById(R.id.arr_right);

        mRightArr.setOnClickListener(this);
        mLeftArr.setOnClickListener(this);
        initData();
        mAdapter = new CustomAdapter(this, mData);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

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
        for (int i = 0; i < 35; i++) {
            mData.add(i);
        }
        if (mData.size() > 0 && !mRecyclerView.isLastItemVisible(LINE_NUM, mData.size())) {
            mRightArr.setVisibility(View.VISIBLE);
        }
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


    private class MyOnItemClickListener implements CustomAdapter.OnItemClickListener {
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
