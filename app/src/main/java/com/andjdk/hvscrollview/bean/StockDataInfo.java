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
package com.andjdk.hvscrollview.bean;

/**
 * Created by andjdk on 2016/10/11.
 */
public class StockDataInfo {
    private String stockName;
    private String StockCode;
    private String priceLastest;
    private String priceOffsetRate;
    private String priceOpen;
    private String priceHigh;
    private String priceLow;
    private String pricePreClose;
    private String tradVulumes;
    private String totalMarketValue;

    public String getTotalMarketValue() {
        return totalMarketValue;
    }

    public void setTotalMarketValue(String totalMarketValue) {
        this.totalMarketValue = totalMarketValue;
    }

    public String getTradVulumes() {
        return tradVulumes;
    }

    public void setTradVulumes(String tradVulumes) {
        this.tradVulumes = tradVulumes;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockCode() {
        return StockCode;
    }

    public void setStockCode(String stockCode) {
        StockCode = stockCode;
    }

    public String getPriceLastest() {
        return priceLastest;
    }

    public void setPriceLastest(String priceLastest) {
        this.priceLastest = priceLastest;
    }

    public String getPriceOffsetRate() {
        return priceOffsetRate;
    }

    public void setPriceOffsetRate(String priceOffsetRate) {
        this.priceOffsetRate = priceOffsetRate;
    }

    public String getPriceOpen() {
        return priceOpen;
    }

    public void setPriceOpen(String priceOpen) {
        this.priceOpen = priceOpen;
    }

    public String getPriceHigh() {
        return priceHigh;
    }

    public void setPriceHigh(String priceHigh) {
        this.priceHigh = priceHigh;
    }

    public String getPriceLow() {
        return priceLow;
    }

    public void setPriceLow(String priceLow) {
        this.priceLow = priceLow;
    }

    public String getPricePreClose() {
        return pricePreClose;
    }

    public void setPricePreClose(String pricePreClose) {
        this.pricePreClose = pricePreClose;
    }
}
