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

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.CardView;

import com.sailman.gaokao.R;

/**
 * Created by Monkey on 2015/6/29.
 */
//???
public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView mTitle;
    public TextView mLabel;
    public TextView mChapter;
    public TextView mKeyword;
    public TextView mPublicationDate;
    public TextView mComments;
    public CardView mCard;
    public TextView mVedio;
    public TextView mContent;
    public TextView mLearn;
    public TextView mStay;
    public TextView mReadcount;
    public TextView mMaxlookdate;

  public MyRecyclerViewHolder(View itemView) {
      super(itemView);

      mTitle =(TextView) itemView.findViewById(R.id.id_title);
      mLabel =(TextView) itemView.findViewById(R.id.id_label);
      mChapter = (TextView) itemView.findViewById(R.id.id_chapter);
      mKeyword = (TextView) itemView.findViewById(R.id.id_keyword);
      mPublicationDate =(TextView) itemView.findViewById(R.id.id_publicationdate);
      mComments = (TextView)itemView.findViewById(R.id.id_comments);
      mCard =(CardView)itemView.findViewById(R.id.id_cardview);
      mVedio =(TextView)itemView.findViewById(R.id.id_vedio);
      mContent =(TextView)itemView.findViewById(R.id.id_content);
      mLearn = (TextView)itemView.findViewById(R.id.id_learn);
      mStay = (TextView)itemView.findViewById(R.id.id_stay);
      mReadcount = (TextView)itemView.findViewById(R.id.id_readcount);
      mMaxlookdate = (TextView)itemView.findViewById(R.id.id_maxlookdate);
  }
}
