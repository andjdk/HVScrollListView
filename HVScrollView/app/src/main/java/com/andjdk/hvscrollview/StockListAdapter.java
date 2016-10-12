package com.andjdk.hvscrollview;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andjdk on 2015/11/3.
 *
 */
public class StockListAdapter extends BaseAdapter {
    private List<StockDataInfo> mStockList;
    private Context mContext;
    private ArrayList<View> mMovableViewList;
    private String[] mMovableListColumnsText;

    int columnMovablePriceLastest = 0;
    int columnMovablePriceOffsetRate = 1;
    int columnMovablePriceHigh = 2;
    int columnMovablePriceLow = 3;
    int columnMovablePriceOpen = 4;
    int columnMovablePriceClose = 5;
    int columnMovableTradVulumes = 6;
    int columnMovableTotalMarketValue = 7;

    private int[] mMovableListColumnsWidth;


    public StockListAdapter(Context context,ArrayList<View> movableViewList,int length) {
        this.mContext=context;
        this.mMovableViewList=movableViewList;

        mMovableListColumnsText =new String[length];
        mMovableListColumnsWidth = new int[length];
        for (int i = 0; i < length; i++) {
            mMovableListColumnsWidth[i]=DisplayUtil.dip2px(context, 70);
        }
    }

    public void setStockListDatas(List<StockDataInfo> stockList){
        this.mStockList=stockList;
    }

    @Override
    public int getCount() {
        return null !=mStockList ? mStockList.size():0;
    }

    @Override
    public Object getItem(int position) {
        return mStockList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = buildListViewLayout();
        }
        StockListItemViewHolder holder = (StockListItemViewHolder) convertView.getTag();
        StockDataInfo stockItem = mStockList.get(position);
        holder.mTextViewStockName.setText(stockItem.getStockName());
        holder.mTextViewStockCode.setText(stockItem.getStockCode());
        holder.mTextViewPriceLastest.setText(stockItem.getPriceLastest());
        holder.mTextViewPriceOffsetRate.setText(stockItem.getPriceOffsetRate());
        holder.mTextViewPriceHigh.setText(stockItem.getPriceHigh());
        holder.mTextViewPriceLow.setText(stockItem.getPriceLow());
        holder.mTextViewPriceOpen.setText(stockItem.getPriceOpen());
        holder.mTextViewPricePreClose.setText(stockItem.getPricePreClose());
        holder.mTextViewTradVulumes.setText(stockItem.getTradVulumes());
        holder.mTextViewTotalMarketValue.setText(stockItem.getTotalMarketValue());
        return convertView;
    }

    private View buildListViewLayout(){
        LinearLayout result = new LinearLayout(mContext);
        StockListItemViewHolder holder=new StockListItemViewHolder();

        LinearLayout fixHeadLayout = new LinearLayout(mContext);
        LinearLayout fixColumn1Layout = new LinearLayout(mContext);
        fixColumn1Layout.setGravity(Gravity.CENTER);
        fixColumn1Layout.setOrientation(LinearLayout.VERTICAL);

        holder.mTextViewStockName = new TextView(mContext);

        fixColumn1Layout.addView(holder.mTextViewStockName,
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        holder.mTextViewStockCode = new TextView(mContext);
        fixColumn1Layout.addView(holder.mTextViewStockCode,
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        fixHeadLayout.addView(fixColumn1Layout,0,new ViewGroup.LayoutParams(DisplayUtil.dip2px(mContext,80),DisplayUtil.dip2px(mContext,50)));

        result.addView(fixHeadLayout);

        LinearLayout movableLayout = new LinearLayout(mContext);
        mMovableViewList.add(movableLayout);
        TextView textview = null;
        for (int i = 0; i < mMovableListColumnsText.length; i++) {
            textview = addListItemTextView(mMovableListColumnsText[i], mMovableListColumnsWidth[i], movableLayout);

            if (columnMovablePriceLastest == i) holder.mTextViewPriceLastest = textview;
            if (columnMovablePriceOffsetRate == i) holder.mTextViewPriceOffsetRate = textview;
            if (columnMovablePriceHigh == i) holder.mTextViewPriceHigh = textview;
            if (columnMovablePriceLow == i) holder.mTextViewPriceLow = textview;
            if (columnMovablePriceOpen == i) holder.mTextViewPriceOpen = textview;
            if (columnMovablePriceClose == i) holder.mTextViewPricePreClose = textview;
            if (columnMovableTradVulumes == i) holder.mTextViewTradVulumes = textview;
            if (columnMovableTotalMarketValue == i) holder.mTextViewTotalMarketValue = textview;
        }
        result.addView(movableLayout);
        result.setTag(holder);
        return result;
    }

    private class StockListItemViewHolder {
        private TextView mTextViewStockName = null;
        private TextView mTextViewStockCode = null;
        private TextView mTextViewPriceLastest = null;
        private TextView mTextViewPriceOffsetRate = null;
        private TextView mTextViewPriceOpen = null;
        private TextView mTextViewPriceHigh = null;
        private TextView mTextViewPriceLow = null;
        private TextView mTextViewPricePreClose = null;
        private TextView mTextViewTradVulumes = null;
        private TextView mTextViewTotalMarketValue = null;
    }

    private TextView addListItemTextView(String headerName, int width, LinearLayout layout) {
        TextView result = new TextView(mContext);
        result.setText(headerName);
        result.setGravity(Gravity.CENTER);
        layout.addView(result, width, DisplayUtil.dip2px(mContext,50));
        return result;
    }



}
