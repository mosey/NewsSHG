package com.example.administrator.news;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.example.administrator.fragment.HomePageFragment;
import com.example.administrator.fragment.ProfileFragment;
import com.example.administrator.fragment.TabFragment_a;
import com.example.administrator.fragment.TabFragment_b;
import com.example.administrator.fragment.TimePageFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
/*
*
* */
@ContentView(value = R.layout.activity_main)
public class MainActivity extends FragmentActivity {

    @ViewInject(R.id.rgGroug)
    private RadioGroup radioGroup;
    @ViewInject(R.id.viewpager)
    private ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        x.view().inject(this);
        // 默认配置被选中的单选按钮
        radioGroup.check(R.id.rbHome);
        initData();
    }

    // 进行组件的赋值操作
    private void initData(){
        // 给ViewPager添加数据,
//        viewpager.setAdapter(new MainPage());
        // 如果添加Fragment则需要相应的适配器支持
        viewpager.setAdapter(new MainFragmentPage(getSupportFragmentManager()));
        // 给按钮组注册单击事件,让单击触发ViewPager页面切换
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override  // Called when the checked radio button has changed. When the selection is cleared, checkedId is -1.
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                   switch (checkedId){
                           case R.id.rbHome:
                               // 手动调用viewPager切换页面
                               viewpager.setCurrentItem(0,true);
                               break;
                           case R.id.rbTime:
                               viewpager.setCurrentItem(1,true);
                               break;
                           case R.id.rbMe:
                               viewpager.setCurrentItem(3,true);
                               break;
                   }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.exit(0);  // 退出app 0 代表正常退出
    }

    private class MainFragmentPage extends FragmentPagerAdapter {

        List<Fragment> iList=new ArrayList<Fragment>();

        public MainFragmentPage(FragmentManager fm) {
            super(fm);
            iList.add(new HomePageFragment());
            iList.add(new TimePageFragment());
            iList.add(new ProfileFragment());
        }

        @Override
        public Fragment getItem(int position) {
            return iList.get(position);
        }

        @Override
        public int getCount() {
            return iList.size();
        }
    }
}
