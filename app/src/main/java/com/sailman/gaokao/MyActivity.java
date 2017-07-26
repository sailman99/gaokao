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

package com.sailman.gaokao;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.sailman.gaokao.R;
import com.sailman.gaokao.adapter.MyViewPagerAdapter;
import com.sailman.gaokao.utils.SnackbarUtil;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.support.design.widget.TabLayout.*;

public class MyActivity extends AppCompatActivity
    implements ViewPager.OnPageChangeListener, OnClickListener {

  //初始化各种控件，照着xml中的顺序写
  private DrawerLayout mDrawerLayout;
  private CoordinatorLayout mCoordinatorLayout;
  private AppBarLayout mAppBarLayout;
  private Toolbar mToolbar;
  private TabLayout mTabLayout;
  private ViewPager mViewPager;
  private FloatingActionButton mFloatingActionButton;
  private NavigationView mNavigationView;
  private Spinner mspinner_labelclassifition,mspinner_chapter;
  private Button mgetfromgaokao_vedioartitle;
  private ThreadLocal<String> ts = new ThreadLocal<String>();
  private MyApp myApp=new MyApp();
  // TabLayout中的tab标题
  private String[] mTitles;
  // 填充到ViewPager中的Fragment
  private List<Fragment> mFragments;
  // ViewPager的数据适配器
  private MyViewPagerAdapter mViewPagerAdapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my);

    // 初始化各种控件
    initViews();

    // 初始化mTitles、mFragments等ViewPager需要的数据
    //这里的数据都是模拟出来了，自己手动生成的，在项目中需要从网络获取数据
    initData();

    // 对各种控件进行设置、适配、填充数据
    configViews();
  }

  private void initData() {

    // Tab的标题采用string-array的方法保存，在res/values/arrays.xml中写
    mTitles = getResources().getStringArray(R.array.tab_titles);

    //初始化填充到ViewPager中的Fragment集合
    mFragments = new ArrayList<>();
    for (int i = 0; i < mTitles.length; i++) {
      Bundle mBundle = new Bundle();
      mBundle.putInt("flag", i);
      MyFragment mFragment = new MyFragment();
      mFragment.setArguments(mBundle);
      mFragments.add(i, mFragment);
    }


  }

  private void configViews() {

    // 设置显示Toolbar
    setSupportActionBar(mToolbar);

    // 设置Drawerlayout开关指示器，即Toolbar最左边的那个icon
    ActionBarDrawerToggle mActionBarDrawerToggle =
            new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
    mActionBarDrawerToggle.syncState();
    mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

    //给NavigationView填充顶部区域，也可在xml中使用app:headerLayout="@layout/header_nav"来设置
    mNavigationView.inflateHeaderView(R.layout.header_nav);
    //给NavigationView填充Menu菜单，也可在xml中使用app:menu="@menu/menu_nav"来设置
    mNavigationView.inflateMenu(R.menu.menu_nav);

    // 自己写的方法，设置NavigationView中menu的item被选中后要执行的操作
    onNavgationViewMenuItemSelected(mNavigationView);

    // 初始化ViewPager的适配器，并设置给它
    mViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), mTitles, mFragments);
    mViewPager.setAdapter(mViewPagerAdapter);
    // 设置ViewPager最大缓存的页面个数
    mViewPager.setOffscreenPageLimit(5);
    // 给ViewPager添加页面动态监听器（为了让Toolbar中的Title可以变化相应的Tab的标题）
    mViewPager.addOnPageChangeListener(this);

    mTabLayout.setTabMode(MODE_SCROLLABLE);
    // 将TabLayout和ViewPager进行关联，让两者联动起来
    mTabLayout.setupWithViewPager(mViewPager);
    // 设置Tablayout的Tab显示ViewPager的适配器中的getPageTitle函数获取到的标题
    mTabLayout.setTabsFromPagerAdapter(mViewPagerAdapter);

    // 设置FloatingActionButton的点击事件
    mFloatingActionButton.setOnClickListener(this);
    mgetfromgaokao_vedioartitle.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {

          String Sql="select vedioartitleid,title,url,subjectid,typeid,keyword,imageurl,comments,publication,inputdate,labelclassificationid,subjectchapterid from Gaokao_vedioartitle ";
          Sql = Sql+"where ";
      }
    });
  }
  /**
   * 设置NavigationView中menu的item被选中后要执行的操作
   *
   * @param mNav
   */
  private void onNavgationViewMenuItemSelected(NavigationView mNav) {
    mNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
      @Override public boolean onNavigationItemSelected(MenuItem menuItem){

        String msgString = "";

        switch (menuItem.getItemId()) {
          case R.id.nav_menu_home:
            msgString = (String) menuItem.getTitle();
            break;
          case R.id.nav_menu_chinese:
            myApp.setSubjectID("001");
            break;
          case R.id.nav_menu_math:
            myApp.setSubjectID("002");
            break;
          case R.id.nav_menu_english:
            myApp.setSubjectID("003");
            break;
          case R.id.nav_menu_physics:
            myApp.setSubjectID("004");
            break;
          case R.id.nav_menu_chemistry:
            myApp.setSubjectID("005");
            break;
          case R.id.nav_menu_biology:
            myApp.setSubjectID("006");
            break;
          case R.id.nav_menu_other:
            myApp.setSubjectID("007");
            break;
        }
        try {
          initSpinner(myApp.getSubjectID());
        }
        catch (Exception e){

        }
        // Menu item点击后选中，并关闭Drawerlayout
        menuItem.setChecked(true);
        mDrawerLayout.closeDrawers();

        // android-support-design兼容包中新添加的一个类似Toast的控件。
        SnackbarUtil.show(mViewPager, msgString, 0);

        return true;
      }
    });
  }
  private String readFromDBA(String path,String post) throws Exception{
    Log.v("TAG",path);
    URL url = null;
    try {
      url = new URL(path);
      HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
      httpURLConnection.setRequestProperty("connection", "Keep-Alive");
      httpURLConnection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

      httpURLConnection.setRequestMethod("POST");// 提交模式
      // conn.setConnectTimeout(10000);//连接超时 单位毫秒
      // conn.setReadTimeout(2000);//读取超时 单位毫秒




      // 发送POST请求必须设置如下两行
      httpURLConnection.setDoOutput(true);
      httpURLConnection.setDoInput(true);
      // 获取URLConnection对象对应的输出流
      PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
      // 发送请求参数
      printWriter.write(post);//post的参数 xx=xx&yy=yy
      // flush输出流的缓冲
      printWriter.flush();
      //开始获取数据
      BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      int len;
      byte[] arr = new byte[1024];
      while((len=bis.read(arr))!= -1){
        bos.write(arr,0,len);
        bos.flush();
      }
      bos.close();
      return bos.toString("utf-8");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
  private void initSpinner(String subjectID) throws Exception {
    //初始化spinner
    ArrayList<Labelclassifition> labelclassifitionsList = new ArrayList<>();
    //Add countries
    labelclassifitionsList.add(new Labelclassifition(0, "请选择视频或文章来源"));

    /*
    从后台获取数据
     */

    final String url="http://113.107.154.131:9001/gupiao/JsonActiongetGaokao_labelclassification";
    final String post="jsonString="+subjectID;
    //final String builder;

    new Thread() {
      public void run() {
        try {
          ts.set(readFromDBA(url,post));
          JSONArray jsonArray =new JSONArray(ts.toString());
          for(int i=0;i<jsonArray.length();i++) {
            Log.v("TAG", String.valueOf(jsonArray.getJSONObject(i).getInt("labelclassificationid")));
            Log.v("TAG", jsonArray.getJSONObject(i).getString("labelname"));
          }
        }catch (Exception e){Log.v("TAG",e.getMessage());}
      }}.start();
    Log.v("TAG",ts.toString());








    //fill data in spinner
    ArrayAdapter<Labelclassifition> adapter = new ArrayAdapter<Labelclassifition>(this, R.layout.simple_spinner_item, labelclassifitionsList);
    //设置下拉样式以后显示的样式
    adapter.setDropDownViewResource(R.layout.my_drop_down_item);
    mspinner_labelclassifition.setAdapter(adapter);
    mspinner_labelclassifition.setSelection(0,true);

    mspinner_labelclassifition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Labelclassifition labelclassifition = (Labelclassifition) parent.getSelectedItem();
        //this.setSelection(position,true);
        // Toast.makeText(this, "Country ID: "+labelclassifition.getLabelclassifitionID().toString()+",  Country Name : "+labelclassifition.getLabelclassifitionName(), Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
      }
    });
  }
  private void initViews() {
    mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerlayout);
    mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.id_coordinatorlayout);
    mAppBarLayout = (AppBarLayout) findViewById(R.id.id_appbarlayout);
    mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
    mTabLayout = (TabLayout) findViewById(R.id.id_tablayout);
    mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
    mFloatingActionButton = (FloatingActionButton) findViewById(R.id.id_floatingactionbutton);
    mNavigationView = (NavigationView) findViewById(R.id.id_navigationview);
    mspinner_labelclassifition = (Spinner)findViewById(R.id.spinner_labelclassifition);
    mspinner_chapter = (Spinner)findViewById(R.id.spinner_chapter);
    mgetfromgaokao_vedioartitle =(Button)findViewById(R.id.getfromgaokao_vedioartitle);
  }
  public String getFromGaokao_vedioartitle(String url) throws Exception{
       return "";
  }
  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_my, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override public void onPageSelected(int position) {
    mToolbar.setTitle(mTitles[position]);
  }

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

  }

  @Override public void onPageScrollStateChanged(int state) {

  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      // FloatingActionButton的点击事件
      case R.id.id_floatingactionbutton:
        SnackbarUtil.show(v, getString(R.string.plusone), 0);
        break;
    }
  }
}
