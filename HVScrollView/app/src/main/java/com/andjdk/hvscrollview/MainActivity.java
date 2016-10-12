package com.andjdk.hvscrollview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HVScrollView hvScrollView= (HVScrollView) findViewById(R.id.hv_scrollview);
        List<StockDataInfo> stockDataInfoList=new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            StockDataInfo stockDataInfo=new StockDataInfo();
            stockDataInfo.setStockName("浦发银行");
            stockDataInfo.setStockCode("600000");
            stockDataInfo.setPriceLastest("13.08");
            stockDataInfo.setPriceOffsetRate("0.10");
            stockDataInfo.setPriceHigh("13.10");
            stockDataInfo.setPriceLow("12.80");
            stockDataInfo.setPriceOpen("12.90");
            stockDataInfo.setPricePreClose("12.90");
            stockDataInfo.setTradVulumes("12.90");
            stockDataInfo.setTotalMarketValue("12.90");
            stockDataInfoList.add(stockDataInfo);
        }
        hvScrollView.setHeaderListData(new String[]{"最新价", "涨跌幅", "最高价", "最低价", "开盘价", "收盘价","成交量","总市值"});
        hvScrollView.setListData(stockDataInfoList);
    }
}
