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

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


import static android.support.design.widget.TabLayout.*;

public class MyActivity extends AppCompatActivity
    implements ViewPager.OnPageChangeListener, OnClickListener {

    //初始化各种控件，照着xml中的顺序写
    private DrawerLayout mDrawerLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private CoordinatorLayout mCoordinatorLayout;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    //private FloatingActionButton mFloatingActionButton;
    private NavigationView mNavigationView;
    private Spinner mspinner_labelclassifition, mspinner_chapter;
    private Button mgetfromgaokao_vedioartitle;
    private EditText mid_keyword;
    private ThreadLocal<String> ts = new ThreadLocal<String>();
    private Context context;
    private Activity activity;
    private DbAdaptor dbAdaptor;
    private MyApp myApp = new MyApp();
    // TabLayout中的tab标题
    private String[] mTitles;
    // 填充到ViewPager中的Fragment
    private List<Fragment> mFragments;
    // ViewPager的数据适配器
    private MyViewPagerAdapter mViewPagerAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        context = this;
        activity = this;
        dbAdaptor=DbAdaptor.getInstance(getApplicationContext());


        // 初始化各种控件
        initViews();

        // 初始化mTitles、mFragments等ViewPager需要的数据
        //这里的数据都是模拟出来了，自己手动生成的，在项目中需要从网络获取数据
        initData();

        // 对各种控件进行设置、适配、填充数据
        configViews();
        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        try {
            mCollapsingToolbarLayout.setTitle("语文");
            myApp.setSubjectID("001");
            myApp.setTypeid("001");//视频类型“001”，文章“002”，公式“003”，测试“004”
            initSpinner("001");

        }catch (Exception e){ }

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
        //mFloatingActionButton.setOnClickListener(this);
        mgetfromgaokao_vedioartitle.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

               //String Sql = "select vedioartitleid,title,url,subjectid,typeid,keyword,imageurl,comments,publication,inputdate,labelclassificationid,subjectchapterid from Gaokao_vedioartitle ";
                String Sql =  " where subjectid='"+myApp.getSubjectID()+"' and typeid='"+myApp.getTypeid()+"' ";
                if(myApp.getLabelclassificationID()>0){
                    Sql=Sql+" and  labelclassificationid="+myApp.getLabelclassificationID();
                }
                if(myApp.getSubjectchapterID()>0){
                    Sql=Sql+" and  subjectchapterid="+myApp.getSubjectchapterID();
                }
                if(mid_keyword.getText().toString().trim().length()>0){
                    String str_keyword=mid_keyword.getText().toString().trim();
                    Sql=Sql+" and (title like '%"+str_keyword+"%' or keyword like '%"+str_keyword+"%' or comments like '%"+str_keyword+"%')";
                }
                try {
                    Sql = URLEncoder.encode(Sql, "utf-8");
                }catch(Exception e){}
                final String url="http://113.107.154.131:9001/gupiao/JsonActiongetGaokao_vedioartitle";
                final String post="jsonString="+Sql;
                new Thread() {
                    public void run() {
                        try {
                            myApp.setVedioartitle_ThreadVar(MyTools.readFromDBA(url,post));
                            Message msg = new Message();
                            msg.what = 2; //subjectchapter
                            handler.sendMessage(msg);
                        }catch (Exception e){Log.v("TAG",e.getMessage());}
                    }}.start();


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
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {



                switch (menuItem.getItemId()) {

                    case R.id.nav_menu_chinese:
                        mCollapsingToolbarLayout.setTitle("语文");
                        myApp.setSubjectID("001");
                        break;
                    case R.id.nav_menu_math:
                        mCollapsingToolbarLayout.setTitle("数学");
                        myApp.setSubjectID("002");
                        break;
                    case R.id.nav_menu_english:
                        mCollapsingToolbarLayout.setTitle("英语");
                        myApp.setSubjectID("003");
                        break;
                    case R.id.nav_menu_physics:
                        mCollapsingToolbarLayout.setTitle("物理");
                        myApp.setSubjectID("004");
                        break;
                    case R.id.nav_menu_chemistry:
                        mCollapsingToolbarLayout.setTitle("化学");
                        myApp.setSubjectID("005");
                        break;
                    case R.id.nav_menu_biology:
                        mCollapsingToolbarLayout.setTitle("生物");
                        myApp.setSubjectID("006");
                        break;
                    case R.id.nav_menu_other:
                        mCollapsingToolbarLayout.setTitle("其他");
                        myApp.setSubjectID("007");
                        break;
                    case R.id.nav_menu_food:
                        mCollapsingToolbarLayout.setTitle("美食");
                        myApp.setSubjectID("008");
                        break;
                    case R.id.nav_menu_sport:
                        mCollapsingToolbarLayout.setTitle("健身");
                        myApp.setSubjectID("009");
                        break;
                }
                try {
                    initSpinner(myApp.getSubjectID());
                } catch (Exception e) {

                }
                // Menu item点击后选中，并关闭Drawerlayout
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                // android-support-design兼容包中新添加的一个类似Toast的控件。
               // SnackbarUtil.show(mViewPager, msgString, 0);

                return true;
            }
        });
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0 ) {
                try {
                    ArrayList<Labelclassifition> labelclassifitionsList = new ArrayList<>();
                    labelclassifitionsList.add(new Labelclassifition(0, "请选择视频或文章来源"));
                    JSONArray jsonArray = new JSONArray(myApp.getLabel_ThreadVar());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        labelclassifitionsList.add(new Labelclassifition(jsonArray.getJSONObject(i).getInt("labelclassificationid"), jsonArray.getJSONObject(i).getString("labelname")));
                    }
                    ArrayAdapter<Labelclassifition> adapter = new ArrayAdapter<Labelclassifition>(context, R.layout.simple_spinner_item, labelclassifitionsList);
                    //设置下拉样式以后显示的样式
                    adapter.setDropDownViewResource(R.layout.my_drop_down_item);
                    mspinner_labelclassifition.setAdapter(adapter);
                    mspinner_labelclassifition.setSelection(0, true);
                    myApp.setLabelclassificationID(0);
                    mspinner_labelclassifition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Labelclassifition labelclassifition = (Labelclassifition) parent.getSelectedItem();
                            myApp.setLabelclassificationID(labelclassifition.getLabelclassifitionID());
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }catch (Exception e){Log.v("TAG",e.getMessage());}


            }
            if (msg.what == 1 ) {
                try {
                    ArrayList<Chapter> chapterList = new ArrayList<>();
                    chapterList.add(new Chapter(0, "请选择所属章节"));
                    JSONArray jsonArray = new JSONArray(myApp.getChapter_ThreadVar());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        chapterList.add(new Chapter(jsonArray.getJSONObject(i).getInt("subjectchapterid"), jsonArray.getJSONObject(i).getString("chaptername")));
                    }
                    ArrayAdapter<Chapter> adapter = new ArrayAdapter<Chapter>(context, R.layout.simple_spinner_item, chapterList);
                    //设置下拉样式以后显示的样式
                    adapter.setDropDownViewResource(R.layout.my_drop_down_item);
                    mspinner_chapter.setAdapter(adapter);
                    mspinner_chapter.setSelection(0, true);
                    myApp.setSubjectchapterID(0);
                    mspinner_chapter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Chapter chapter = (Chapter) parent.getSelectedItem();
                            myApp.setSubjectchapterID(chapter.getChapterID());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }catch (Exception e){Log.v("TAG",e.getMessage());}


            }
            if(msg.what==2){
                MyFragment mFragment;

                try {
                    if("001".equals(myApp.getTypeid())) {
                        mFragment = (MyFragment) mFragments.get(0);
                        mFragment.addData(myApp.getVedioartitle_ThreadVar());
                        mFragment.setLabel_ThreadVar(myApp.getLabel_ThreadVar());
                        mFragment.setChapter_ThreadVar(myApp.getChapter_ThreadVar());
                    }
                    if("002".equals(myApp.getTypeid())) {
                        mFragment = (MyFragment) mFragments.get(1);
                        mFragment.addData(myApp.getVedioartitle_ThreadVar());
                        mFragment.setLabel_ThreadVar(myApp.getLabel_ThreadVar());
                        mFragment.setChapter_ThreadVar(myApp.getChapter_ThreadVar());
                    }
                    if("003".equals(myApp.getTypeid())) {
                        mFragment = (MyFragment) mFragments.get(2);
                        mFragment.addData(myApp.getVedioartitle_ThreadVar());
                        mFragment.setLabel_ThreadVar(myApp.getLabel_ThreadVar());
                        mFragment.setChapter_ThreadVar(myApp.getChapter_ThreadVar());
                    }
                    if("004".equals(myApp.getTypeid())) {
                        mFragment = (MyFragment) mFragments.get(3);
                        mFragment.addData(myApp.getVedioartitle_ThreadVar());
                        mFragment.setLabel_ThreadVar(myApp.getLabel_ThreadVar());
                        mFragment.setChapter_ThreadVar(myApp.getChapter_ThreadVar());
                    }

                }catch (Exception e){}
            }


        }
    };
    private void initSpinner(String subjectID) throws Exception {
    //初始化spinner

        final String urla="http://113.107.154.131:9001/gupiao/JsonActiongetGaokao_labelclassification";
        final String urlb="http://113.107.154.131:9001/gupiao/JsonActiongetGaokao_subjectchapter";
        final String post="jsonString="+subjectID;

        new Thread() {
          public void run() {
            try {
                myApp.setLabel_ThreadVar(MyTools.readFromDBA(urla,post));
                Message msg = new Message();
                msg.what = 0; //labelclassification
                handler.sendMessage(msg);
            }catch (Exception e){Log.v("TAG",e.getMessage());}
          }}.start();
        new Thread() {
            public void run() {
                try {
                    myApp.setChapter_ThreadVar(MyTools.readFromDBA(urlb,post));
                    Message msg = new Message();
                    msg.what = 1; //subjectchapter
                    handler.sendMessage(msg);
                }catch (Exception e){Log.v("TAG",e.getMessage());}
            }}.start();

  }
  private void initViews() {

    mDrawerLayout = (DrawerLayout) this.findViewById(R.id.id_drawerlayout);
    mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.id_coordinatorlayout);
    mAppBarLayout = (AppBarLayout) findViewById(R.id.id_appbarlayout);
    mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
    mTabLayout = (TabLayout) findViewById(R.id.id_tablayout);
    mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
    //mFloatingActionButton = (FloatingActionButton) findViewById(R.id.id_floatingactionbutton);
    mNavigationView = (NavigationView) findViewById(R.id.id_navigationview);
    mspinner_labelclassifition = (Spinner)findViewById(R.id.spinner_labelclassifition);
    mspinner_chapter = (Spinner)findViewById(R.id.spinner_chapter);
    mgetfromgaokao_vedioartitle =(Button)findViewById(R.id.getfromgaokao_vedioartitle);
    mCollapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
    mid_keyword =(EditText)findViewById(R.id.id_keyword);
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
        //Intent intent=new Intent(context,DownloadFileService.class);
        //intent.putExtra("rootPath",MyTools.getRootPath(this));
        //context.startService(intent);
        Toast.makeText(getApplicationContext(),MyTools.getRootPathMsg(activity),Toast.LENGTH_LONG).show();


        return true;
    }
      if (id == R.id.download_db) {
          final String url="http://113.107.154.131:9001/gupiao/JsonActionDownloadGaokao_vedioartitle";
          final String post="jsonString= where 1=1";
          new Thread() {
              public void run() {
                  try {
                      myApp.setDownloadVedioartitle_ThreadVar(MyTools.readFromDBA(url,post));
                      Message msg = new Message();
                      msg.what = 5; //subjectchapter
                      handler.sendMessage(msg);
                  }catch (Exception e){Log.v("TAG",e.getMessage());}
              }}.start();


          return true;
      }

    return super.onOptionsItemSelected(item);
  }

  @Override public void onPageSelected(int position) {
    mToolbar.setTitle(mTitles[position]);
  }

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      if(position==0){
          myApp.setTypeid("001");
      }
      if(position==1){
          myApp.setTypeid("002");
      }
      if(position==2){
          myApp.setTypeid("003");
      }
      if(position==3){
          myApp.setTypeid("004");
      }
  }

  @Override public void onPageScrollStateChanged(int state) {

  }

  @Override public void onClick(View v) {
      /*
    switch (v.getId()) {
      // FloatingActionButton的点击事件
      case R.id.id_floatingactionbutton:
        SnackbarUtil.show(v, getString(R.string.plusone), 0);
        break;
    }*/
  }


}
