# HVScrollListView
---
HVScrollListView是一个可以实现横纵滑动的列表控件。如股票列表。以下为使用此控件实现的效果图：
![](https://github.com/andjdk/HVScrollListView/blob/master/index.gif)

##使用方式

###step 1：
    <com.andjdk.hvscrollview.view.HVScrollView
        android:id="@+id/hv_scrollview"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    </com.andjdk.hvscrollview.view.HVScrollView>

###step 2
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
        //定义顶部栏
        hvScrollView.setHeaderListData(new String[]{"最新价", "涨跌幅", "最高价", "最低价", "开盘价", "收盘价","成交量","总市值"});
		//初始化adapter
        StockListAdapter mAdapter=new StockListAdapter(this,stockDataInfoList,R.layout.item_layout);		
        hvScrollView.setAdapter(mAdapter);

        

注意：

- 1、StockListAdapter必须继承CommonAdapter
- 2、R.layout.item_layout布局中实现滑动部分的layout必须是**LinearLayout**包裹且定义的id必须定义为 **android:id="@+id/move\_layout"**
如下

   
        <LinearLayout
        	android:id="@+id/move_layout"
        	android:layout_width="wrap_content"
        	android:layout_height="wrap_content"
        	android:orientation="horizontal">
	        <TextView
	            android:id="@+id/text2"
	            android:layout_width="70dp"
	            android:layout_height="50dp"
	            android:gravity="center"
	            android:text="2"/>
	        <TextView
	            android:id="@+id/text3"
	            android:layout_width="70dp"
	            android:gravity="center"
	            android:layout_height="50dp"
	            android:text="3"/>
	        <TextView
	            android:id="@+id/text4"
	            android:layout_width="70dp"
	            android:gravity="center"
	            android:layout_height="50dp"
	            android:text="4"/>
	        <TextView
	            android:id="@+id/text5"
	            android:gravity="center"
	            android:layout_width="70dp"
	            android:layout_height="50dp"
	            android:text="5"/>
	        <TextView
	            android:id="@+id/text6"
	            android:gravity="center"
	            android:layout_width="70dp"
	            android:layout_height="50dp"
	            android:text="6"/>
	        <TextView
	            android:id="@+id/text7"
	            android:gravity="center"
	            android:layout_width="70dp"
	            android:layout_height="50dp"
	            android:text="7"/>
	        <TextView
	            android:id="@+id/text8"
	            android:gravity="center"
	            android:layout_width="70dp"
	            android:layout_height="50dp"
	            android:text="8"/>
	        <TextView
	            android:id="@+id/text9"
	            android:gravity="center"
	            android:layout_width="70dp"
	            android:layout_height="50dp"
	            android:text="9"/>
    </LinearLayout>
    


###step 3：在adapter中初始化数据

	
    public class StockListAdapter extends CommonAdapter<StockDataInfo> {


    public StockListAdapter(Context mContext, List<StockDataInfo> mDatas, int layoutId) {
        super(mContext, mDatas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, StockDataInfo stockDataInfo, int position, ArrayList<View> movableViewList) {
        holder.setText(R.id.text1,stockDataInfo.getStockName());
        holder.setText(R.id.text2,stockDataInfo.getPriceLastest());
        holder.setText(R.id.text3,stockDataInfo.getPriceOffsetRate());
        holder.setText(R.id.text4,stockDataInfo.getPriceHigh());
        holder.setText(R.id.text5,stockDataInfo.getPriceLow());
        holder.setText(R.id.text6,stockDataInfo.getPriceOpen());
        holder.setText(R.id.text7,stockDataInfo.getPricePreClose());
        holder.setText(R.id.text8,stockDataInfo.getTradVulumes());
        holder.setText(R.id.text9,stockDataInfo.getTotalMarketValue());
    }}


###实现点击列表

    	//点击列表item
        hvScrollView.setOnItemClick(new HVScrollView.OnItemClickedListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,position+"",Toast.LENGTH_SHORT).show();
            }
        });
        //点击头部按钮
        hvScrollView.setOnHeaderClickedListener(new HVScrollView.OnHeaderClickedListener() {
            @Override
            public void onHeadViewClick(String string) {
                Toast.makeText(MainActivity.this,string,Toast.LENGTH_SHORT).show();
            }
        });


到这里就可以是实现想要的功能了，是不是很简单。当然目前还没有增加上拉刷新向下加载更多的功能，这功能还在完善中，敬请期待。如在使用中遇到有什么问题也可以直接联系我。加群：462723796 欢迎大家star或者fork