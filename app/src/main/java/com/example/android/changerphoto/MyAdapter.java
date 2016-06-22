package com.example.android.changerphoto;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Android on 2016/6/22.
 */
public class MyAdapter extends PagerAdapter {
    private Integer mInteger;
    private ArrayList<ImageView> mImageViews;

    public MyAdapter(Integer integer, ArrayList<ImageView> imageViews ) {
        this.mInteger = integer;
        this.mImageViews = imageViews;

    }

    //获取图片数量
    @Override
    public int getCount() {
        return mInteger.MAX_VALUE;
    }

    //  指定复用的判断逻辑, 固定写法
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //返回要显示的条目内容, 创建条目
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        int newposition = position % mImageViews.size();
        ImageView imageView=mImageViews.get(newposition);
        // a. 把View对象添加到container中
        container.addView(imageView);
        // b. 把View对象返回给框架, 适配器
        return imageView;// 必须重写, 否则报异常
    }

    //销毁项目
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);// object 要销毁的对象

    }
}
