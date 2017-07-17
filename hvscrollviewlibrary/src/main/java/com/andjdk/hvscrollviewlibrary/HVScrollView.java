/**
 * Copyright (c) 2016, andjdk@163.com All Rights Reserved.
 * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG            #
 * #                                                   #
 */
package com.andjdk.hvscrollviewlibrary;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Collection;

import static com.andjdk.hvscrollviewlibrary.DisplayUtil.dip2px;

/**
 * Created by andjdk on 2015/11/3.
 */
public class HVScrollView extends RelativeLayout {
    /**
     * 列表头的高和宽
     */
    private LinearLayout mLayoutTitleMovable;
    private float mStartX = 0;
    private int mMoveOffsetX = 0;
    private int mFixX = 0;

    private String[] mFixLeftListColumnsText;
    private int[] mFixLeftListColumnsWidth;

    private String[] mMovableListColumnsText = new String[]{};
    private int[] mMovableListColumnsWidth = null;

    private ListView mStockListView;
    private Object mAdapter;

    private Collection<View> mMovableViewList;

    private Context context;
    private int mMovableTotalWidth = 0;

    private int mMoveViewWidth = 70;
    private int mFixViewWidth = 80;
    private int mItemViewHeight = 50;

    private LinearLayout footerLayout;

    private boolean isLoading;  //正在加载
    private int mLastVisibleItem;
    private int mTotalItemCount;
    private int mVisibleItemCount;
    private int mFirstVisibleItem;


    public HVScrollView(Context context) {
        this(context, null);
    }

    public HVScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HVScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    private void initView() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(buildHeadLayout());
        linearLayout.addView(buildMoveableListView());

        this.addView(linearLayout, new LayoutParams(LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private View buildHeadLayout() {
        LinearLayout headLayout = new LinearLayout(getContext());
        headLayout.setGravity(Gravity.CENTER);
        LinearLayout fixHeadLayout = new LinearLayout(getContext());
        addListHeaderTextView(mFixLeftListColumnsText[0], mFixLeftListColumnsWidth[0], fixHeadLayout);
        fixHeadLayout.setGravity(Gravity.CENTER);
        headLayout.addView(fixHeadLayout, 0, new ViewGroup.LayoutParams(dip2px(context, mFixViewWidth), dip2px(context, mItemViewHeight)));

        mLayoutTitleMovable = new LinearLayout(getContext());
        for (int i = 0; i < mMovableListColumnsText.length; i++) {
            TextView textView = addListHeaderTextView(mMovableListColumnsText[i], mMovableListColumnsWidth[i], mLayoutTitleMovable);
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onHeaderClickedListener != null) {
                        onHeaderClickedListener.onHeadViewClick(((TextView) v).getText().toString());
                    }
                }
            });
        }
        headLayout.addView(mLayoutTitleMovable);
        return headLayout;
    }


    private View buildMoveableListView() {
        RelativeLayout linearLayout = new RelativeLayout(getContext());
        mStockListView = new ListView(getContext());
        if (null != mAdapter) {
            if (mAdapter instanceof CommonAdapter) {
                mStockListView.setAdapter((CommonAdapter) mAdapter);
                mMovableViewList = ((CommonAdapter) mAdapter).getMovableViewList();
            }
        }

        footerLayout = new LinearLayout(getContext());
        footerLayout.setGravity(Gravity.CENTER);
        ProgressBar progressBar = new ProgressBar(getContext());
        footerLayout.addView(progressBar);
        footerLayout.setVisibility(GONE);
        mStockListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //当滑动到底部时
                if (mTotalItemCount == mLastVisibleItem && scrollState == SCROLL_STATE_IDLE) {
                    if (!isLoading) {
                        isLoading = true;
                        if (null != onLoadMoreListener) {
                            onLoadMoreListener.onLoadingMore();
                            footerLayout.setVisibility(View.VISIBLE);//显示底部布局
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mFirstVisibleItem = firstVisibleItem;
                mTotalItemCount = totalItemCount;
                mLastVisibleItem = firstVisibleItem + visibleItemCount;
                mVisibleItemCount = visibleItemCount;
            }
        });
        mStockListView.setOnItemClickListener(mOnItemClickListener);
        mStockListView.setOnItemLongClickListener(mOnItemLongClickListener);
        linearLayout.addView(footerLayout, LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        linearLayout.addView(mStockListView, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        return linearLayout;
    }

    public void onLoadingComplete() {
        if (null != footerLayout) {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    footerLayout.setVisibility(GONE);
                    isLoading = false;
                    mStockListView.setSelection(mLastVisibleItem - mVisibleItemCount + 1);
                }
            });
        }
    }

    public void setAdapter(Object adapter) {
        this.mAdapter = adapter;
        initView();
    }


    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (null != onItemClickedListener) {
                onItemClickedListener.onItemClick(parent, view, position, id);
            }
        }
    };

    private AdapterView.OnItemLongClickListener mOnItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            if (null != onItemLongClickedListener) {
                onItemLongClickedListener.onItemLongClick(parent, view, position, id);
            }
            return false;
        }
    };

    private OnItemClickedListener onItemClickedListener;
    private OnItemLongClickedListener onItemLongClickedListener;

    /**
     * 列表item单机事件
     */
    public void setOnItemClick(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    /**
     * 列表item长按事件
     */
    public void setOnItemLongClick(OnItemLongClickedListener onItemLongClickedListener) {
        this.onItemLongClickedListener = onItemLongClickedListener;
    }

    private TextView addListHeaderTextView(String headerName, int AWidth, LinearLayout fixHeadLayout) {
        TextView textView = new TextView(getContext());
        textView.setText(headerName);
        textView.setGravity(Gravity.CENTER);
        fixHeadLayout.addView(textView, AWidth, dip2px(context, 50));
        return textView;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = (int) Math.abs(ev.getX() - mStartX);
                if (offsetX > 30) {
                    return true;
                } else {
                    return false;
                }
            case MotionEvent.ACTION_UP:
                actionUP();
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private void actionUP() {
        if (mFixX < 0) {
            mFixX = 0;
            mLayoutTitleMovable.scrollTo(0, 0);
            if (null != mMovableViewList) {

                updateAdapterViewHorizontalOffset(0);
                for (View view : mMovableViewList) {
                    view.scrollTo(0, 0);
                }

            } else {
                if (mLayoutTitleMovable.getWidth() + Math.abs(mFixX) > MovableTotalWidth()) {
                    int offsetX = MovableTotalWidth() - mLayoutTitleMovable.getWidth();
                    mLayoutTitleMovable.scrollTo(offsetX, 0);

                    updateAdapterViewHorizontalOffset(offsetX);
                    if (null != mMovableViewList) {
                        for (View view : mMovableViewList) {
                            view.scrollTo(offsetX, 0);
                        }
                    }
                }
            }
        }
    }

    private void updateAdapterViewHorizontalOffset(int offset) {
        if (mAdapter != null)
            ((CommonAdapter) mAdapter).setHorizontalOffset(offset);
    }


    private int MovableTotalWidth() {
        if (0 == mMovableTotalWidth) {
            for (int i = 0; i < mMovableListColumnsWidth.length; i++) {
                mMovableTotalWidth = mMovableTotalWidth + mMovableListColumnsWidth[i];
            }
        }
        return mMovableTotalWidth;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = event.getX();
                return true;
            case MotionEvent.ACTION_MOVE:
                int offsetX = (int) Math.abs(event.getX() - mStartX);
                if (offsetX > 30) {
                    mMoveOffsetX = (int) (mStartX - event.getX() + mFixX);
                    if (0 > mMoveOffsetX) {
                        mMoveOffsetX = 0;
                    } else {
                        if ((mLayoutTitleMovable.getWidth() + mMoveOffsetX) > MovableTotalWidth()) {
                            mMoveOffsetX = MovableTotalWidth() - mLayoutTitleMovable.getWidth();
                        }
                    }
                    mLayoutTitleMovable.scrollTo(mMoveOffsetX, 0);
                    if (null != mMovableViewList) {
                        updateAdapterViewHorizontalOffset(mMoveOffsetX);

                        for (View view : mMovableViewList) {
                            view.scrollTo(mMoveOffsetX, 0);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mFixX = mMoveOffsetX; // mFixX + (int) ((int) ev.getX() - mStartX)
                actionUP();
                break;

        }

        return super.onTouchEvent(event);
    }

    /**
     * 必须先初始化顶部标题栏
     *
     * @param headerListData
     */
    public void setHeaderListData(String[] headerListData) {
        if (headerListData == null) {
            return;
        }
        this.mMovableListColumnsText = headerListData;
        mMovableListColumnsWidth = new int[headerListData.length];
        for (int i = 0; i < headerListData.length; i++) {
            mMovableListColumnsWidth[i] = dip2px(context, mMoveViewWidth);
        }
        mFixLeftListColumnsWidth = new int[]{dip2px(context, mFixViewWidth)};
        mFixLeftListColumnsText = new String[]{"名称"};
    }


    private OnHeaderClickedListener onHeaderClickedListener = null;

    public OnHeaderClickedListener getOnHeaderClickedListener() {
        return onHeaderClickedListener;
    }

    public void setOnHeaderClickedListener(OnHeaderClickedListener onHeaderClickedListener) {
        this.onHeaderClickedListener = onHeaderClickedListener;
    }

    /**
     * 列头点击事件
     */
    public interface OnHeaderClickedListener {
        public void onHeadViewClick(String string);

    }

    private OnLoadMoreListener onLoadMoreListener;

    public OnLoadMoreListener getOnLoadMoreListener() {
        return onLoadMoreListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadingMore();
    }

    /**
     * listview item单击事件
     */
    public interface OnItemClickedListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id);

    }

    /**
     * listview item单击事件
     */
    public interface OnItemLongClickedListener {
        public void onItemLongClick(AdapterView<?> parent, View view, int position, long id);

    }

}

