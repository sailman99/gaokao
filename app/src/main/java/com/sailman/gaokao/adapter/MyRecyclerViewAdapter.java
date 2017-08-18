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

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sailman.gaokao.Gaokao_vedioartitleSendPhone;
import com.sailman.gaokao.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monkey on 2015/6/29.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewHolder> {

  public interface OnItemClickListener {
    void onItemClick(View view, int position);

    void onItemLongClick(View view, int position);
  }

  public OnItemClickListener mOnItemClickListener;

  public void setOnItemClickListener(OnItemClickListener listener) {
    this.mOnItemClickListener = listener;
  }

  public Context mContext;
  public List<Gaokao_vedioartitleSendPhone> mDatas;
  public LayoutInflater mLayoutInflater;


  public MyRecyclerViewAdapter(Context mContext) {

    this.mContext = mContext;
    mLayoutInflater = LayoutInflater.from(mContext);
    mDatas = new ArrayList<Gaokao_vedioartitleSendPhone>();

  }

  /**
   * 创建ViewHolder
   */
  @Override public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View mView = mLayoutInflater.inflate(R.layout.item_main, parent, false);
    MyRecyclerViewHolder mViewHolder = new MyRecyclerViewHolder(mView);

    return mViewHolder;
  }

  /**
   * 绑定ViewHoler，给item中的控件设置数据
   */
  @Override public void onBindViewHolder(final MyRecyclerViewHolder holder, final int position) {
    if (mOnItemClickListener != null) {
      holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          mOnItemClickListener.onItemClick(holder.itemView, position);
        }
      });

      holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
        @Override public boolean onLongClick(View v) {
          mOnItemClickListener.onItemLongClick(holder.itemView, position);
          return true;
        }
      });
    }

   // holder.mTextView.setText(mDatas.get(position));
   // Log.v("TAG",String.valueOf(position));
    Gaokao_vedioartitleSendPhone gaokao_vedioartitleSendPhone=(Gaokao_vedioartitleSendPhone)mDatas.get(position);

    holder.mTitle.setText(gaokao_vedioartitleSendPhone.getTitle());
    holder.mLabel.setText(gaokao_vedioartitleSendPhone.getLabelclassificationname());
    holder.mChapter.setText(gaokao_vedioartitleSendPhone.getSubjectchaptername());
    holder.mKeyword.setText(gaokao_vedioartitleSendPhone.getKeyword());
    holder.mPublicationDate.setText(gaokao_vedioartitleSendPhone.getPublication());
    holder.mComments.setText(gaokao_vedioartitleSendPhone.getComments());
    if(gaokao_vedioartitleSendPhone.getReadcount()<1){
        holder.mCard.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }else{
        holder.mCard.setBackgroundColor(Color.parseColor("#f5e7f4"));
    }
    if("0".equals(gaokao_vedioartitleSendPhone.getVedio()))
        holder.mVedio.setText("视频清晰");
    if("1".equals(gaokao_vedioartitleSendPhone.getVedio()))
      holder.mVedio.setText("视频一般");
    if("2".equals(gaokao_vedioartitleSendPhone.getVedio()))
      holder.mVedio.setText("视频模糊");

    if("0".equals(gaokao_vedioartitleSendPhone.getContent()))
      holder.mContent.setText("内容很好");
    if("1".equals(gaokao_vedioartitleSendPhone.getContent()))
      holder.mContent.setText("内容一般");
    if("2".equals(gaokao_vedioartitleSendPhone.getContent()))
      holder.mContent.setText("没有价值");

    if("0".equals(gaokao_vedioartitleSendPhone.getLearn()))
      holder.mLearn.setText("很有借鉴");
    if("1".equals(gaokao_vedioartitleSendPhone.getLearn()))
      holder.mLearn.setText("我已掌握");
    if("2".equals(gaokao_vedioartitleSendPhone.getLearn()))
      holder.mLearn.setText("全是废话");
    if("0".equals(gaokao_vedioartitleSendPhone.getStay()))
      holder.mStay.setText("要反复看");
    if("1".equals(gaokao_vedioartitleSendPhone.getStay()))
      holder.mStay.setText("复习时看");
    if("2".equals(gaokao_vedioartitleSendPhone.getStay()))
      holder.mStay.setText("可以删掉");

    holder.mReadcount.setText("阅读次数: "+gaokao_vedioartitleSendPhone.getReadcount());
    holder.mMaxlookdate.setText(gaokao_vedioartitleSendPhone.getMaxlookdate());
  }

  @Override public int getItemCount() {
    //Log.v("TAG","getItemCount size="+String.valueOf(mDatas.size()));
    return mDatas.size();
  }
}
