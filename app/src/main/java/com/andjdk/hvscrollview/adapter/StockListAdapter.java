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
package com.andjdk.hvscrollview.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.andjdk.hvscrollview.R;
import com.andjdk.hvscrollview.base.CommonAdapter;
import com.andjdk.hvscrollview.base.ViewHolder;
import com.andjdk.hvscrollview.bean.StockDataInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andjdk on 2016/10/13.
 */
public class StockListAdapter extends CommonAdapter<StockDataInfo> {


    public StockListAdapter(Context mContext, List<StockDataInfo> mDatas, int layoutId) {
        super(mContext, mDatas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, StockDataInfo stockDataInfo, int position, ArrayList<View> movableViewList) {
        LinearLayout moveLayout=holder.getView(R.id.move_view);
        //可滑动的布局LinearLayout
        movableViewList.add(moveLayout);
        holder.setText(R.id.text1,stockDataInfo.getStockName());
        holder.setText(R.id.text2,stockDataInfo.getPriceLastest());
        holder.setText(R.id.text3,stockDataInfo.getPriceOffsetRate());
        holder.setText(R.id.text4,stockDataInfo.getPriceHigh());
        holder.setText(R.id.text5,stockDataInfo.getPriceLow());
        holder.setText(R.id.text6,stockDataInfo.getPriceOpen());
        holder.setText(R.id.text7,stockDataInfo.getPricePreClose());
        holder.setText(R.id.text8,stockDataInfo.getTradVulumes());
        holder.setText(R.id.text9,stockDataInfo.getTotalMarketValue());
    }
}
