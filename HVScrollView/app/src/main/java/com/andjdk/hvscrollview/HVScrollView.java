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
package com.andjdk.hvscrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.andjdk.hvscrollview.DisplayUtil.dip2px;

/**
 * Created by andjdk on 2015/11/3.
 *
 */
public class HVScrollView extends RelativeLayout {
    /**
     * 列表头的高和宽
     */
    private LinearLayout mLayoutTitleMovable;
    private float mStartX = 0;
    private int mMoveOffsetX = 0;
    private int mFixX = 0;

    private String[] mFixLeftListColumnsText ;
    private int[] mFixLeftListColumnsWidth;
    /**
     * 列表头的高和宽
     */
    private int mTitleHeight = 30;
    private int mTitleWidth = 60;

    private String[] mMovableListColumnsText=new String[]{};
    private int[] mMovableListColumnsWidth =null;

    private ListView mStockListView;

    private ArrayList<View> mMovableViewList = new ArrayList();
    public StockListAdapter mAdapter;
    private Context context;
    private int mMovableTotalWidth = 0;
    private List<StockDataInfo> mStockcodeDatas=new ArrayList<StockDataInfo>();

//    public static int dip2px(Context context,float dipValue)
//    {
//        final float scale = context.getResources().getDisplayMetrics().density;
//        return (int) (dipValue * scale + 0.5f);
//    }

    public HVScrollView(Context context) {
        this(context,null);
    }

    public HVScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HVScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context= context;
//        initData(context);
//        initView();
    }

    private void initData(Context context) {
        mMovableListColumnsWidth= new int[]{DisplayUtil.dip2px(context,70),DisplayUtil.dip2px(context,70),DisplayUtil.dip2px(context,70),DisplayUtil.dip2px(context,70),DisplayUtil.dip2px(context,70),DisplayUtil.dip2px(context,70)};
        mFixLeftListColumnsWidth=new int[]{DisplayUtil.dip2px(context,80)};
//        mMovableListColumnsText=new String[]{"最新价", "涨跌幅", "最高价", "最低价", "开盘价", "收盘价"};
//        mMovableListColumnsText=new String[]{};
        mFixLeftListColumnsText=new String[]{"名称"};
    }

    private void initView() {
        LinearLayout linearLayout=new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(buildHeadLayout());

        linearLayout.addView(buildMoveableListView());

        this.addView(linearLayout,new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }


    private View buildHeadLayout() {
        LinearLayout headLayout=new LinearLayout(getContext());
        headLayout.setGravity(Gravity.CENTER);
        LinearLayout fixHeadLayout = new LinearLayout(getContext());
        addListHeaderTextView(mFixLeftListColumnsText[0],mFixLeftListColumnsWidth[0],fixHeadLayout);
        fixHeadLayout.setGravity(Gravity.CENTER);
        headLayout.addView(fixHeadLayout,0,new ViewGroup.LayoutParams(dip2px(context,80),dip2px(context,50)));

        mLayoutTitleMovable=new LinearLayout(getContext());
        for (int i = 0; i < mMovableListColumnsText.length; i++) {
            TextView textView=addListHeaderTextView(mMovableListColumnsText[i],mMovableListColumnsWidth[i],mLayoutTitleMovable);
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 点击设置升序，降序
                    Toast.makeText(getContext(), ((TextView)v).getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        headLayout.addView(mLayoutTitleMovable);
        return headLayout;
    }



    private View buildMoveableListView() {
        LinearLayout linearLayout=new LinearLayout(getContext());
        mStockListView=new ListView(getContext());
        mAdapter=new StockListAdapter(getContext(),mMovableViewList,mMovableListColumnsText.length);
        mAdapter.setStockListDatas(mStockcodeDatas);
        mStockListView.setAdapter(mAdapter);
        mStockListView.setOnItemClickListener(mOnItemClickListener);
        mStockListView.setOnItemLongClickListener(mOnItemLongClickListener);
        linearLayout.addView(mStockListView,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        return linearLayout;
    }

    private AdapterView.OnItemClickListener mOnItemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(null !=onItemClickedListener){
                onItemClickedListener.onItemClick(parent, view, position, id);
            }
        }
    };

    private AdapterView.OnItemLongClickListener mOnItemLongClickListener=new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            if(null !=onItemLongClickedListener){
                onItemLongClickedListener.onItemLongClick(parent,view,position,id);
            }
            return false;
        }
    };

    private OnItemClickedListener onItemClickedListener;
    private OnItemLongClickedListener onItemLongClickedListener;

    /**
     * 列表item单机事件
     */
    public void setOnItemClick(OnItemClickedListener onItemClickedListener){
        this.onItemClickedListener=onItemClickedListener;
    }

    /**
     * 列表item长按事件
     */
    public void setOnItemLongClick(OnItemLongClickedListener onItemLongClickedListener){
        this.onItemLongClickedListener=onItemLongClickedListener;
    }

    private TextView addListHeaderTextView(String headerName, int AWidth,LinearLayout fixHeadLayout) {
        TextView textView=new TextView(getContext());
        textView.setText(headerName);
        textView.setGravity(Gravity.CENTER);
        fixHeadLayout.addView(textView,AWidth,dip2px(context,50));
        return textView;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX=ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX= (int) Math.abs(ev.getX()-mStartX);
                if(offsetX>30){
                    return true;
                }else{
                    return false;
                }
            case MotionEvent.ACTION_UP:
                actionUP();
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private void actionUP() {
        if (mFixX<0){
            mFixX=0;
            mLayoutTitleMovable.scrollTo(0,0);
            if(null !=mMovableViewList){
                for (int i = 0; i < mMovableViewList.size(); i++) {
                    mMovableViewList.get(i).scrollTo(0,0);
                }
            }

        }else {
            if (mLayoutTitleMovable.getWidth()+Math.abs(mFixX)>MovableTotalWidth()) {
                mLayoutTitleMovable.scrollTo(MovableTotalWidth() - mLayoutTitleMovable.getWidth(), 0);
                if (null != mMovableViewList) {
                    for (int i = 0; i < mMovableViewList.size(); i++) {
                        mMovableViewList.get(i).scrollTo(MovableTotalWidth() -mLayoutTitleMovable.getWidth(), 0);
                    }
                }
            }
        }
    }


    private int MovableTotalWidth() {
        if(0==mMovableTotalWidth){
            for (int i = 0; i <mMovableListColumnsWidth.length; i ++) {
                mMovableTotalWidth = mMovableTotalWidth + mMovableListColumnsWidth[i];
            }
        }
        return mMovableTotalWidth;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mStartX=event.getX();
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
                        for (int i = 0; i < mMovableViewList.size(); i++) {
                            mMovableViewList.get(i).scrollTo(mMoveOffsetX, 0);
                        }
                    }
                    // mLayoutMovable.scrollTo(mOffsetX, 0);
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
     * 初始化列表数据
     * @param stockList
     */
    public void setListData(List<StockDataInfo> stockList){
        this.mStockcodeDatas=stockList;
        mAdapter.setStockListDatas(mStockcodeDatas);
        mStockListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 必须先初始化顶部标题栏
     * @param headerListData
     */
    public void setHeaderListData(String[] headerListData){
        if(headerListData ==null){
            return;
        }
        this.mMovableListColumnsText=headerListData;
        mMovableListColumnsWidth=new int[headerListData.length];
        for (int i = 0; i < headerListData.length; i++) {
            mMovableListColumnsWidth[i]=DisplayUtil.dip2px(context,70);
        }
//        mMovableListColumnsWidth= new int[]{DisplayUtil.dip2px(context,70),DisplayUtil.dip2px(context,70),DisplayUtil.dip2px(context,70),DisplayUtil.dip2px(context,70),DisplayUtil.dip2px(context,70),DisplayUtil.dip2px(context,70)};
        mFixLeftListColumnsWidth=new int[]{DisplayUtil.dip2px(context,80)};
//        mMovableListColumnsText=new String[]{"最新价", "涨跌幅", "最高价", "最低价", "开盘价", "收盘价"};
        mFixLeftListColumnsText=new String[]{"名称"};

        initView();
    }


    /**
     * 列头点击事件
     *
     */
    public interface OnHeaderClickedListener {
        public void onClick(int headerID, int direction);

    }

    /**
     * listview item单击事件
     *
     */
    public interface OnItemClickedListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id);

    }

    /**
     * listview item单击事件
     *
     */
    public interface OnItemLongClickedListener {
        public void onItemLongClick(AdapterView<?> parent, View view, int position, long id);

    }

}

