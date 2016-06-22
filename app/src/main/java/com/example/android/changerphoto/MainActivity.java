package com.example.android.changerphoto;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private ArrayList<ImageView> mViewArrayList;
    private boolean isRunning = false;
    private ViewPager mviewPager;
    private TextView tv_desc;
    private LinearLayout ll_point_container;
    private int[] imageResIds;
    private String[] contentDescs;
    private int previousSelectedPosition = 0;
    private MyAdapter mMyAdapter;
    private Integer mInteger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化布局 View视图
        initViews();
        // Model数据
        initData();
        // Controller 控制器
        initAdapter();
        //开启一个线程图片切换
        initThread();
    }

    //开启一个线程
    private void initThread() {
        new Thread() {
            @Override
            public void run() {
                isRunning = true;
                while (isRunning) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 往下跳一位
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mviewPager.setCurrentItem(mviewPager.getCurrentItem() + 1);
                        }
                    });
                }
            }
        }.start();

    }


    // 初始化布局 View视图
    private void initViews() {
        mviewPager = (ViewPager) findViewById(R.id.viewpager);
        mviewPager.setOnPageChangeListener(this);// 设置页面更新监听
        ll_point_container = (LinearLayout) findViewById(R.id.ll_point_container);

        tv_desc = (TextView) findViewById(R.id.tv_desc);
    }

    // Model数据
    private void initData() {
        imageResIds = new int[]{R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e};

// 文本描述
        contentDescs = new String[]{
                "巩俐不低俗，我就不能低俗",
                "扑树又回来啦！再唱经典老歌引万人大合唱",
                "揭秘北京电影如何升级",
                "乐视网TV版大派送",
                "热血屌丝的反杀"};
        mViewArrayList = new ArrayList<ImageView>();
        ImageView imageview;
        View pointView;
        LayoutParams layoutParams;
        for (int i = 0; i < imageResIds.length; i++) {
            // 初始化要显示的图片对象
            imageview = new ImageView(this);
            imageview.setBackgroundResource(imageResIds[i]);
            mViewArrayList.add(imageview);
            // 加小白点, 指示器
            pointView = new View(this);
            pointView.setBackgroundResource(R.drawable.selector_bg_point);
            layoutParams = new LinearLayout.LayoutParams(5, 5);
            if (i != 0) {
                layoutParams.leftMargin = 10;
            }
            // 设置默认所有都不可用
            pointView.setEnabled(false);
            ll_point_container.addView(pointView, layoutParams);
        }


    }

    // Controller 控制器
    private void initAdapter() {
        ll_point_container.getChildAt(0).setEnabled(true);
        tv_desc.setText(contentDescs[0]);
        previousSelectedPosition = 0;
        // 设置适配器
        mMyAdapter = new MyAdapter(mInteger, mViewArrayList);
        mviewPager.setAdapter(mMyAdapter);
        // 默认设置到中间的某个位置
        int pos = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % mViewArrayList.size());
        mviewPager.setCurrentItem(pos);
    }

    // 滚动时调用
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    // 新的条目被选中时调用
    @Override
    public void onPageSelected(int position) {
        int newposition = position % mViewArrayList.size();
        //设置文本
        tv_desc.setText(contentDescs[newposition]);
        // 把之前的禁用, 把最新的启用, 更新指示器
        ll_point_container.getChildAt(previousSelectedPosition).setEnabled(false);
        ll_point_container.getChildAt(newposition).setEnabled(true);
        // 记录之前的位置
        previousSelectedPosition = newposition;

    }

    // 滚动状态变化时调用
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }
}
