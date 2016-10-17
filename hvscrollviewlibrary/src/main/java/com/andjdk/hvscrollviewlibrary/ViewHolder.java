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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by andjdk on 2016/10/13.
 */
public class ViewHolder {
    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    public ViewHolder() {
        super();
    }

    public ViewHolder(Context context, ViewGroup parent, int layoutId, int position){
        this.mPosition=position;
        this.mViews=new SparseArray<View>();
        mConvertView= LayoutInflater.from(context).inflate(layoutId, parent,false);
        mConvertView.setTag(this);
    }

    public static ViewHolder get(Context context,View convertView,ViewGroup parent,int layoutId,int position){
        if(convertView==null){
            return new ViewHolder(context, parent, layoutId, position);
        }else{
            ViewHolder holder=(ViewHolder) convertView.getTag();
            return holder;
        }
    }

    /**
     * 通过viewId获取控件
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId){
        View view=mViews.get(viewId);
        if(view==null){
            view=mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }


    public View getConvertView(){
        return mConvertView;
    }

    /**
     * 设置ViewHolder里面TextView的值
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId,String text){
        TextView tv=getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 设置ViewHolder里面TextColor值
     * @param viewId
     * @param textColor
     * @return
     */
    public ViewHolder setTextColor(int viewId,int textColor){
        TextView tv=getView(viewId);
        tv.setTextColor(textColor);
        return this;
    }

    /**
     * 设置ViewHolder里面ImageView的值
     * @param viewId
     * @param resId
     * @return
     */
    public ViewHolder setImageResource(int viewId,int resId){
        ImageView view=getView(viewId);
        view.setImageResource(resId);
        return this;
    }
    /**
     * 设置ViewHolder里面ImageView的值
     * @param viewId
     * @param bitmap
     * @return
     */
    public ViewHolder setImageBitmap(int viewId,Bitmap bitmap){
        ImageView view=getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }
    /**
     * 设置ViewHolder里面ImageView的值
     * @param viewId
     * @param drawable
     * @return
     */
    public ViewHolder setImageDrawable(int viewId,Drawable drawable){
        ImageView view=getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }
    /**
     * 设置ViewHolder里面ImageView的值
     * @param viewId
     * @param url
     * @return
     */
    public ViewHolder setImageURI(int viewId,String url){
        ImageView view=getView(viewId);
//		Imageloader.getLoader(view,url);
        return this;
    }
}
