package com.andjdk.hvscrollview;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.andjdk.hvscrollviewlibrary.HVScrollView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private StockListAdapter mAdapter;
    private HVScrollView hvScrollView;
    private ArrayList<StockDataInfo> stockDataInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hvScrollView = (HVScrollView) findViewById(R.id.hv_scrollview);
        stockDataInfoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            StockDataInfo stockDataInfo = new StockDataInfo();
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
        //定义顶部栏
        hvScrollView.setHeaderListData(new String[]{"最新价", "涨跌幅", "最高价", "最低价", "开盘价", "收盘价", "成交量", "总市值"});
        mAdapter = new StockListAdapter(this, stockDataInfoList, R.layout.item_layout);
        hvScrollView.setAdapter(mAdapter);
        //点击列表item
        hvScrollView.setOnItemClick(new HVScrollView.OnItemClickedListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, position + "", Toast.LENGTH_SHORT).show();
            }
        });
        //点击头部按钮
        hvScrollView.setOnHeaderClickedListener(new HVScrollView.OnHeaderClickedListener() {
            @Override
            public void onHeadViewClick(String string) {
                Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
            }
        });

        hvScrollView.setOnLoadMoreListener(new HVScrollView.OnLoadMoreListener() {
            @Override
            public void onLoadingMore() {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            handler.sendEmptyMessage(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }


    android.os.Handler handler = new android.os.Handler(new android.os.Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 5) {
                for (int i = 0; i < 10; i++) {
                    StockDataInfo stockDataInfo = new StockDataInfo();
                    stockDataInfo.setStockName("中国银行");
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
                    mAdapter.notifyDataSetChanged();
                }
                hvScrollView.onLoadingComplete();
            }
            return false;
        }
    });
}
