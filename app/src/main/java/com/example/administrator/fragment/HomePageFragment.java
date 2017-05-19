package com.example.administrator.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.news.R;
import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;

/**
 *  Fragment需要添加xml布局文件
 */

@ContentView(value = R.layout.home_page)
public class HomePageFragment extends Fragment {

    private TabLayout tab_title;                            //定义TabLayout
    private ViewPager vp_pager;                             //定义viewPager

    @Nullable
    @Override  // 每个Fragment都有自己的XML配置文件
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("jxy",this.getClass() + "---->2: onCreateView");
        //View view = x.view().inject(this,inflater,null);
        View view = View.inflate(this.getActivity(), R.layout.home_page,null);
        initControls(view);
        return view;
    }

    private void initControls(View view) {
        tab_title = (TabLayout)view.findViewById(R.id.tab_title);
        vp_pager = (ViewPager)view.findViewById(R.id.vp_pager);

        vp_pager.setAdapter(new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager()));
        tab_title.setupWithViewPager(vp_pager);
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        List<String> iTab = new ArrayList<String>();
        List<Fragment> iList=new ArrayList<Fragment>();

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            iTab.add("要闻");
            iTab.add("直播");
            iTab.add("V观");
            iTab.add("联播");
            iTab.add("夜读");
            iTab.add("国际");
            iList.add(new TabFragment_a());
            iList.add(new TabFragment_b());
            iList.add(new TabFragment_c());
            iList.add(new TabFragment_d());
            iList.add(new TabFragment_e());
            iList.add(new TabFragment_f());
        }

        @Override
        public Fragment getItem(int position) {
            return iList.get(position);
        }

        @Override
        public int getCount() {
            return iTab.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return iTab.get(position);
        }
    }
    }
