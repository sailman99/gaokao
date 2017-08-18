/*
 *
 *  *
 *  *  *
 *  *  *  * ===================================
 *  *  *  * Copyright (c) 2016.
 *  *  *  * 作者：安卓猴
 *  *  *  * 微博：@安卓猴
 *  *  *  * 博客：http://sunjiajia.com
 *  *  *  * Github：https://github.com/opengit
 *  *  *  *
 *  *  *  * 注意**：如果您使用或者修改该代码，请务必保留此版权信息。
 *  *  *  * ===================================
 *  *  *
 *  *  *
 *  *
 *
 */

package com.sailman.gaokao.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Monkey on 2015/6/29.
 */
//作为ViewPage适配器
public class MyViewPagerAdapter extends FragmentStatePagerAdapter {

  private String[] mTitles;
  //用Fragment填充FragmentStatePageAdapter
  private List<Fragment> mFragments;

  public MyViewPagerAdapter(FragmentManager fm, String[] mTitles, List<Fragment> mFragments) {
    super(fm);
    this.mTitles = mTitles;
    this.mFragments = mFragments;
  }
//返回对应页面的标题，这个和下面的几个都是容器自己自调用的，不用程序员再调用的
  @Override public CharSequence getPageTitle(int position) {
    return mTitles[position];
  }
//返回对应位置的Fragment,这个是对应的页面的Fragment,可以在这定义自定义页面
  @Override public Fragment getItem(int position) {
    return mFragments.get(position);
  }
//返回对应有多少页面数，容器自己会处理
  @Override public int getCount() {
    return mFragments.size();
  }
}
