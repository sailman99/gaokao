
package com.sailman.gaokao;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sailman.gaokao.adapter.MyRecyclerViewAdapter;

import org.json.JSONArray;


/**
 * Created by Monkey on 2015/6/29.
 */
//这个类是给MyViewPagerAdapter用的
public class MyFragment extends Fragment
    implements SwipeRefreshLayout.OnRefreshListener, MyRecyclerViewAdapter.OnItemClickListener{//监听了三个事件

  private View mView;
  private SwipeRefreshLayout mSwipeRefreshLayout; //下拉刷新用的
  private RecyclerView mRecyclerView;//通过设置它提供的不同LayoutManager，ItemDecoration , ItemAnimator实现令人瞠目的效果
  private RecyclerView.LayoutManager mLayoutManager;//管理器
  private MyRecyclerViewAdapter mRecyclerViewAdapter;//自定义RecyclerViewAdapter适配器


  private static final int VERTICAL_LIST = 0;
  private static final int HORIZONTAL_LIST = 1;
  private static final int VERTICAL_GRID = 2;
  private static final int HORIZONTAL_GRID = 3;
  private static final int STAGGERED_GRID = 4;

  private static final int SPAN_COUNT = 2;
  private int flag = 0;

  private String label_ThreadVar;
  private String chapter_ThreadVar;

  public void setLabel_ThreadVar(String label_threadVar){
    this.label_ThreadVar=label_threadVar;
  }

  public void setChapter_ThreadVar(String chapter_threadVar){
    this.chapter_ThreadVar=chapter_threadVar;
  }
  public void addData(String vedioartitle){
      mRecyclerViewAdapter.mDatas.clear();
     // ArrayList<Gaokao_vedioartitleSendPhone> gaokao_vedioartitleSendPhoneList = new ArrayList<>();
      try {
        JSONArray jsonArray = new JSONArray(vedioartitle);
        for (int i = 0; i < jsonArray.length(); i++) {
          Gaokao_vedioartitleSendPhone gaokao_vedioartitleSendPhone = new Gaokao_vedioartitleSendPhone();
          gaokao_vedioartitleSendPhone.setVedioartitleid(jsonArray.getJSONObject(i).getInt("vedioartitleid"));
          gaokao_vedioartitleSendPhone.setTitle(jsonArray.getJSONObject(i).getString("title"));
          gaokao_vedioartitleSendPhone.setLabelclassificationname(jsonArray.getJSONObject(i).getString("labelclassificationname"));
          gaokao_vedioartitleSendPhone.setSubjectchaptername(jsonArray.getJSONObject(i).getString("subjectchaptername"));
          gaokao_vedioartitleSendPhone.setPublication(jsonArray.getJSONObject(i).getString("publication"));
          gaokao_vedioartitleSendPhone.setKeyword(jsonArray.getJSONObject(i).getString("keyword"));
          gaokao_vedioartitleSendPhone.setUrl(jsonArray.getJSONObject(i).getString("url"));
          gaokao_vedioartitleSendPhone.setSubjectid(jsonArray.getJSONObject(i).getString("subjectid"));
          gaokao_vedioartitleSendPhone.setTypeid(jsonArray.getJSONObject(i).getString("typeid"));
          gaokao_vedioartitleSendPhone.setImageurl(jsonArray.getJSONObject(i).getString("imageurl"));
          gaokao_vedioartitleSendPhone.setComments(jsonArray.getJSONObject(i).getString("comments"));
          gaokao_vedioartitleSendPhone.setLabelclassificationid(jsonArray.getJSONObject(i).getInt("labelclassificationid"));
          gaokao_vedioartitleSendPhone.setSubjectchapterid(jsonArray.getJSONObject(i).getInt("subjectchapterid"));
          gaokao_vedioartitleSendPhone.setVedio(jsonArray.getJSONObject(i).getString("vedio"));
          gaokao_vedioartitleSendPhone.setContent(jsonArray.getJSONObject(i).getString("content"));
          gaokao_vedioartitleSendPhone.setLearn(jsonArray.getJSONObject(i).getString("learn"));
          gaokao_vedioartitleSendPhone.setStay(jsonArray.getJSONObject(i).getString("stay"));
          gaokao_vedioartitleSendPhone.setReadcount(jsonArray.getJSONObject(i).getInt("readcount"));
          gaokao_vedioartitleSendPhone.setMaxlookdate(jsonArray.getJSONObject(i).getString("maxlookdate"));
          mRecyclerViewAdapter.mDatas.add(i,gaokao_vedioartitleSendPhone );
        }
      }catch (Exception e){}
     // Log.v("TAG",String.valueOf(mRecyclerViewAdapter.mDatas.size()));
      mRecyclerViewAdapter.notifyDataSetChanged();
  }


  //应定义一构造函数，通过构造函数把要显示的数据传递给下一级MyRecyclerViewAdapter的构造函数
  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    mView = inflater.inflate(R.layout.frag_main, container, false);//SwipeRefreshLayout下拉刷新组件
    return mView;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.id_swiperefreshlayout);
    mRecyclerView = (RecyclerView) mView.findViewById(R.id.id_recyclerview);

    flag = (int) getArguments().get("flag");
    configRecyclerView();

    // 刷新时，指示器旋转后变化的颜色
    mSwipeRefreshLayout.setColorSchemeResources(R.color.main_blue_light, R.color.main_blue_dark);
    mSwipeRefreshLayout.setOnRefreshListener(this);
  }

  private void configRecyclerView() {

    switch (flag) {
      case VERTICAL_LIST:
        mLayoutManager =
            new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        break;
      case HORIZONTAL_LIST:
        mLayoutManager =
            //new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        break;
      case VERTICAL_GRID:
        mLayoutManager =
           // new GridLayoutManager(getActivity(), SPAN_COUNT, GridLayoutManager.VERTICAL, false);
            new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        break;
      case HORIZONTAL_GRID:
        mLayoutManager =
            //new GridLayoutManager(getActivity(), SPAN_COUNT, GridLayoutManager.HORIZONTAL, false);
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        break;
      case STAGGERED_GRID:
        mLayoutManager =
            //new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        break;
    }

    if (flag != STAGGERED_GRID) {
      //应该在这传数据给MyRecyclerViewAdapter
      mRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(),MyTools.getRootPath(getActivity()));
      mRecyclerViewAdapter.setOnItemClickListener(this);
      mRecyclerView.setAdapter(mRecyclerViewAdapter);//和下拉刷新组件关联起来了
    }

    mRecyclerView.setLayoutManager(mLayoutManager);





  }

  @Override public void onRefresh() {
    mSwipeRefreshLayout.setRefreshing(false);

    // 刷新时模拟数据的变化
    /*
    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        mSwipeRefreshLayout.setRefreshing(false);
        int temp = (int) (Math.random() * 10);
        if (flag != STAGGERED_GRID) {
          mRecyclerViewAdapter.mDatas.add(0, "new" + temp);
          mRecyclerViewAdapter.notifyDataSetChanged();
        } else {
          mStaggeredAdapter.mDatas.add(0, "new" + temp);
          mStaggeredAdapter.mHeights.add(0, (int) (Math.random() * 300) + 200);
          mStaggeredAdapter.notifyDataSetChanged();
        }
      }
    }, 1000);*/
  }

  @Override public void onItemClick(View view, int position) {

    //SnackbarUtil.show(mRecyclerView, getString(R.string.item_clicked), 0);
    Gaokao_vedioartitleSendPhone gaokao_vedioartitleSendPhone=new Gaokao_vedioartitleSendPhone();
    gaokao_vedioartitleSendPhone=(Gaokao_vedioartitleSendPhone) mRecyclerViewAdapter.mDatas.get(position);
    /*
    Intent intent = new Intent(view.getContext(), WebViewActivity.class);
    intent.putExtra("url", gaokao_vedioartitleSendPhone.getUrl());
    startActivity(intent);*/
    Intent intent = new Intent(view.getContext(), PlayActivity.class);
    intent.putExtra("vedioartitleid",gaokao_vedioartitleSendPhone.getVedioartitleid());
    intent.putExtra("url", gaokao_vedioartitleSendPhone.getUrl());
    intent.putExtra("title",gaokao_vedioartitleSendPhone.getTitle());
    intent.putExtra("keyword",gaokao_vedioartitleSendPhone.getKeyword());
    intent.putExtra("publicationdate",gaokao_vedioartitleSendPhone.getPublication());
    intent.putExtra("subjectid",gaokao_vedioartitleSendPhone.getSubjectid());
    intent.putExtra("typeid",gaokao_vedioartitleSendPhone.getTypeid());
    intent.putExtra("imageurl",gaokao_vedioartitleSendPhone.getImageurl());
    intent.putExtra("comments",gaokao_vedioartitleSendPhone.getComments());
    intent.putExtra("labelclassificationid",gaokao_vedioartitleSendPhone.getLabelclassificationid());
    intent.putExtra("subjectchapterid",gaokao_vedioartitleSendPhone.getSubjectchapterid());
    intent.putExtra("vedio",gaokao_vedioartitleSendPhone.getVedio());
    intent.putExtra("content",gaokao_vedioartitleSendPhone.getContent());
    intent.putExtra("learn",gaokao_vedioartitleSendPhone.getLearn());
    intent.putExtra("stay",gaokao_vedioartitleSendPhone.getStay());
    intent.putExtra("label_ThreadVar",label_ThreadVar);
    intent.putExtra("chapter_ThreadVar",chapter_ThreadVar);
    startActivity(intent);
  }

  @Override public void onItemLongClick(View view, int position) {


   // SnackbarUtil.show(mRecyclerView, getString(R.string.item_longclicked), 0);
  }
}
