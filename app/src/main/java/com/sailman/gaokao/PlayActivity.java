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
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class PlayActivity extends Activity {

    private Button bitem_clicked_net;
    private Button bitem_clicked_location;
    private EditText mEditText_label;
    private Spinner  mPlay_spinner_labelclassifition;
    private Spinner  mPlay_spinner_chapter;
    private EditText  mEditText_keyword;
    private EditText  mEditText_comments;
    private RadioGroup mRadiogroup_vedio;
    private RadioGroup mRadiogroup_content;
    private RadioGroup mRadiogroup_learn;
    private RadioGroup mRadiogroup_stay;
    private RadioGroup mRadiogroup_look;

    private RadioButton mRadioButton_vedioa;
    private RadioButton mRadioButton_vediob;
    private RadioButton mRadioButton_vedioc;

    private RadioButton mRadioButton_contenta;
    private RadioButton mRadioButton_contentb;
    private RadioButton mRadioButton_contentc;

    private RadioButton mRadioButton_learna;
    private RadioButton mRadioButton_learnb;
    private RadioButton mRadioButton_learnc;

    private RadioButton mRadioButton_staya;
    private RadioButton mRadioButton_stayb;
    private RadioButton mRadioButton_stayc;

    private RadioButton mRadioButton_looka;
    private RadioButton mRadioButton_lookb;

    private Button mSaveandclose_PlayActivity;

    private Integer vedioartitleid;
    private String title;
    private String url;
    private String subjectid;
    private String typeid;
    private String keyword;
    private String imageurl;
    private String comments;
    private String publication;
    private Integer labelclassificationid;
    private Integer subjectchapterid;
    private String vedio;
    private String content;
    private String learn;
    private String stay;
    private String look;
    private String locationpath;
    private boolean b_fileExists;
    private MyApp myApp;
    private Context context;
    private String[] rootPath;
    private String label_ThreadVar;
    private String chapter_ThreadVar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_main);
        Intent intent = getIntent();
        vedioartitleid = intent.getIntExtra("vedioartitleid",-1);
        url = intent.getStringExtra("url");
        title = intent.getStringExtra("title");
        subjectid =  intent.getStringExtra("subjectid");
        typeid = intent.getStringExtra("typeid");
        keyword = intent.getStringExtra("keyword");
        imageurl = intent.getStringExtra("imageurl");
        comments = intent.getStringExtra("comments");
        publication = intent.getStringExtra("publicationdate");
        labelclassificationid = intent.getIntExtra("labelclassificationid",0);
        subjectchapterid =  intent.getIntExtra("subjectchapterid",0);
        vedio = intent.getStringExtra("vedio");
        content = intent.getStringExtra("content");
        learn = intent.getStringExtra("learn");
        stay = intent.getStringExtra("stay");
        label_ThreadVar=intent.getStringExtra("label_ThreadVar");
        chapter_ThreadVar=intent.getStringExtra("chapter_ThreadVar");
        look="1";
        myApp = new MyApp();
        context = this;
        locationpath="";
        rootPath=MyTools.getRootPath(this);

        b_fileExists=false;
        String[] locationfile;
        locationfile=title.split("\\(");

        if(locationfile.length>2){
            String str="";
            for(int i=0;i<locationfile.length-1;i++){
                if(i==0){
                    str=str+locationfile[i];
                }else{
                    str=str+"("+locationfile[i];
                }
            }

            if(MyTools.fileIsExists(rootPath,str)!=null){
                locationpath=str;
                b_fileExists=true;
            }else{
                b_fileExists=false;
            }

        }else{
            if(locationfile.length>1) {
                if (MyTools.fileIsExists(rootPath,locationfile[locationfile.length - 2])!=null) {
                    locationpath = locationfile[locationfile.length - 2];
                    b_fileExists = true;
                } else {
                    b_fileExists = false;
                }
            }else{
                b_fileExists = false;
            }
        }
        initView(url,locationpath);

    }

    private void initView(final String url,final String locationpath){
        bitem_clicked_net=(Button)findViewById(R.id.play_item_net);
        bitem_clicked_location=(Button)findViewById(R.id.play_item_location);
        mEditText_label = (EditText)findViewById(R.id.editText_label);
        mPlay_spinner_labelclassifition = (Spinner)findViewById(R.id.play_spinner_labelclassifition);
        mPlay_spinner_chapter = (Spinner)findViewById(R.id.play_spinner_chapter);
        mEditText_keyword = (EditText)findViewById(R.id.editText_keyword);
        mEditText_comments = (EditText)findViewById(R.id.editText_comments);
        mSaveandclose_PlayActivity = (Button)findViewById(R.id.saveandclose_PlayActivity);
        mRadiogroup_vedio = (RadioGroup)findViewById(R.id.radiogroup_vedio);
        mRadiogroup_content =(RadioGroup)findViewById(R.id.radiogroup_content);
        mRadiogroup_learn = (RadioGroup)findViewById(R.id.radiogroup_learn);
        mRadiogroup_stay = (RadioGroup)findViewById(R.id.radiogroup_stay);
        mRadiogroup_look = (RadioGroup)findViewById(R.id.radiogroup_look);
        mRadioButton_vedioa =(RadioButton)findViewById(R.id.radioButton_vedioa);
        mRadioButton_vediob =(RadioButton)findViewById(R.id.radioButton_vediob);
        mRadioButton_vedioc =(RadioButton)findViewById(R.id.radioButton_vedioc);
        mRadioButton_contenta =(RadioButton)findViewById(R.id.radioButton_contenta);
        mRadioButton_contentb =(RadioButton)findViewById(R.id.radioButton_contentb);
        mRadioButton_contentc =(RadioButton)findViewById(R.id.radioButton_contentc);
        mRadioButton_learna =(RadioButton)findViewById(R.id.radioButton_learna);
        mRadioButton_learnb =(RadioButton)findViewById(R.id.radioButton_learnb);
        mRadioButton_learnc =(RadioButton)findViewById(R.id.radioButton_learnc);
        mRadioButton_staya =(RadioButton)findViewById(R.id.radioButton_staya);
        mRadioButton_stayb =(RadioButton)findViewById(R.id.radioButton_stayb);
        mRadioButton_stayc =(RadioButton)findViewById(R.id.radioButton_stayc);
        mRadioButton_looka =(RadioButton)findViewById(R.id.radioButton_looka);
        mRadioButton_lookb =(RadioButton)findViewById(R.id.radioButton_lookb);
        mSaveandclose_PlayActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject jsonParam = new JSONObject();
                try{
                    jsonParam.put("vedioartitleid", vedioartitleid);
                    jsonParam.put("subject", subjectid);
                    jsonParam.put("title", title);
                    jsonParam.put("videoartitle", typeid);
                    jsonParam.put("url", url);
                    jsonParam.put("publication",publication);
                    jsonParam.put("label",labelclassificationid);
                    jsonParam.put("chapter", myApp.getSubjectchapterID());
                    jsonParam.put("keyword", mEditText_keyword.getText());
                    jsonParam.put("relationimage", imageurl);
                    jsonParam.put("comments", mEditText_comments.getText());
                    jsonParam.put("vedio", vedio);
                    jsonParam.put("content", content);
                    jsonParam.put("learn", learn);
                    jsonParam.put("stay", stay);
                    jsonParam.put("look",look);
                }catch(Exception e){}


                final String url="http://113.107.154.131:9001/gupiao/JsonActionUpdateGaokao_videoartitle";
                final String post="jsonString=["+jsonParam.toString()+"]";

                new Thread() {
                    public void run() {
                        try {
                            myApp.setReturn_result(MyTools.readFromDBA(url,post));
                            Message msg = new Message();
                            msg.what = 2;
                            handler.sendMessage(msg);
                        }catch (Exception e){Log.v("TAG",e.getMessage());}
                    }}.start();

                }
        });
        mRadiogroup_vedio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup rg,int checkedId)
            {
                switch(checkedId){
                    case R.id.radioButton_vedioa:vedio="0";break;
                    case R.id.radioButton_vediob:vedio="1";break;
                    case R.id.radioButton_vedioc:vedio="2";break;
                }
            }
        });
        mRadiogroup_content.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup rg,int checkedId)
            {
                switch(checkedId){
                    case R.id.radioButton_contenta:content="0";break;
                    case R.id.radioButton_contentb:content="1";break;
                    case R.id.radioButton_contentc:content="2";break;
                }
            }
        });
        mRadiogroup_learn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup rg,int checkedId)
            {
                switch(checkedId){
                    case R.id.radioButton_learna:learn="0";break;
                    case R.id.radioButton_learnb:learn="1";break;
                    case R.id.radioButton_learnc:learn="2";break;
                }
            }
        });
        mRadiogroup_stay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup rg,int checkedId)
            {
                switch(checkedId){
                    case R.id.radioButton_staya:stay="0";break;
                    case R.id.radioButton_stayb:stay="1";break;
                    case R.id.radioButton_stayc:stay="2";break;
                }
            }
        });
        mRadiogroup_look.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup rg,int checkedId)
            {
                switch(checkedId){
                    case R.id.radioButton_looka:look="0";break;
                    case R.id.radioButton_lookb:look="1";break;
                }
            }
        });
        bitem_clicked_net.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WebViewActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });
        bitem_clicked_location.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //  1.  先设定好Intent
                Intent intent = new Intent(v.getContext(), LocalVideoActivity.class);

                intent.putExtra("uri", MyTools.fileIsExists(rootPath,locationpath));
                startActivity(intent);

            }
        });
        bitem_clicked_location.setClickable(b_fileExists);
        try {
            initSpinner(subjectid);
        }catch (Exception e){}
        mEditText_label.setText(title);
        mEditText_keyword.setText(keyword);
        mEditText_comments.setText(comments);
        if("0".equals(vedio))
            mRadioButton_vedioa.setChecked(true);
        if("1".equals(vedio))
            mRadioButton_vediob.setChecked(true);
        if("2".equals(vedio))
            mRadioButton_vedioc.setChecked(true);
        if("0".equals(content))
            mRadioButton_contenta.setChecked(true);
        if("1".equals(content))
            mRadioButton_contentb.setChecked(true);
        if("2".equals(content))
            mRadioButton_contentc.setChecked(true);
        if("0".equals(learn))
            mRadioButton_learna.setChecked(true);
        if("1".equals(learn))
            mRadioButton_learnb.setChecked(true);
        if("2".equals(learn))
            mRadioButton_learnc.setChecked(true);
        if("0".equals(stay))
            mRadioButton_staya.setChecked(true);
        if("1".equals(stay))
            mRadioButton_stayb.setChecked(true);
        if("2".equals(stay))
            mRadioButton_stayc.setChecked(true);
    }


       private void initSpinner(String subjectID) throws Exception {
        //初始化spinner

        final String urla="http://113.107.154.131:9001/gupiao/JsonActiongetGaokao_labelclassification";
        final String urlb="http://113.107.154.131:9001/gupiao/JsonActiongetGaokao_subjectchapter";
        final String post="jsonString="+subjectID;

        new Thread() {
            public void run() {
                try {
                   // myApp.setLabel_ThreadVar(MyTools.readFromDBA(urla,post));
                    myApp.setLabel_ThreadVar(label_ThreadVar);
                    Message msg = new Message();
                    msg.what = 0; //labelclassification
                    handler.sendMessage(msg);
                }catch (Exception e){Log.v("TAG",e.getMessage());}
            }}.start();
        new Thread() {
            public void run() {
                try {
                  //  myApp.setChapter_ThreadVar(MyTools.readFromDBA(urlb,post));
                    myApp.setChapter_ThreadVar(chapter_ThreadVar);
                    Message msg = new Message();
                    msg.what = 1; //subjectchapter
                    handler.sendMessage(msg);
                }catch (Exception e){Log.v("TAG",e.getMessage());}
            }}.start();

    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0 ) {
                Integer labelclassificationidposition=0;
                try {
                    ArrayList<Labelclassifition> labelclassifitionsList = new ArrayList<>();
                    labelclassifitionsList.add(new Labelclassifition(0, "请选择视频或文章来源"));
                    JSONArray jsonArray = new JSONArray(myApp.getLabel_ThreadVar());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        labelclassifitionsList.add(new Labelclassifition(jsonArray.getJSONObject(i).getInt("labelclassificationid"), jsonArray.getJSONObject(i).getString("labelname")));
                        if(labelclassificationid==jsonArray.getJSONObject(i).getInt("labelclassificationid")){
                            labelclassificationidposition=i+1;
                        }
                    }
                    ArrayAdapter<Labelclassifition> adapter = new ArrayAdapter<Labelclassifition>(context, R.layout.simple_spinner_item, labelclassifitionsList);
                    //设置下拉样式以后显示的样式

                    adapter.setDropDownViewResource(R.layout.my_drop_down_item);
                    mPlay_spinner_labelclassifition.setAdapter(adapter);
                    mPlay_spinner_labelclassifition.setSelection(labelclassificationidposition, true);

                    myApp.setLabelclassificationID(labelclassificationid);

                    mPlay_spinner_labelclassifition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    Integer subjectchapteridposition=0;
                    ArrayList<Chapter> chapterList = new ArrayList<>();
                    chapterList.add(new Chapter(0, "请选择所属章节"));
                    JSONArray jsonArray = new JSONArray(myApp.getChapter_ThreadVar());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        chapterList.add(new Chapter(jsonArray.getJSONObject(i).getInt("subjectchapterid"), jsonArray.getJSONObject(i).getString("chaptername")));
                        if(subjectchapterid==jsonArray.getJSONObject(i).getInt("subjectchapterid")){
                            subjectchapteridposition=i+1;
                        }
                    }
                    ArrayAdapter<Chapter> adapter = new ArrayAdapter<Chapter>(context, R.layout.simple_spinner_item, chapterList);
                    //设置下拉样式以后显示的样式
                    adapter.setDropDownViewResource(R.layout.my_drop_down_item);
                    mPlay_spinner_chapter.setAdapter(adapter);
                    mPlay_spinner_chapter.setSelection(subjectchapteridposition, true);
                    myApp.setSubjectchapterID(subjectchapterid);
                    mPlay_spinner_chapter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
            if (msg.what == 2 ) {
                try {
                    finish();
                }catch (Exception e){Log.v("TAG",e.getMessage());}
            }
        }
    };
}