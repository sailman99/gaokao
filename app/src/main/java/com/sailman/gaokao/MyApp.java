

package com.sailman.gaokao;


import android.app.Application;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;


public class MyApp extends Application {

	private Integer labelclassificationID;
	private Integer subjectchapterID;
	private String subjectID;
	private String label_ThreadVar;
	private String chapter_ThreadVar;
	private String vedioartitle_ThreadVar;
	private String downloadVedioartitle_ThreadVar;
	private String typeid;
	private String return_result;
    private List<Gaokao_vedioartitleSendPhone> list=new ArrayList<Gaokao_vedioartitleSendPhone>();

    public List<Gaokao_vedioartitleSendPhone> getList(){
        return list;
    }
    public void addList(Gaokao_vedioartitleSendPhone gaokao_vedioartitleSendPhone){

        list.add(gaokao_vedioartitleSendPhone);
    }
    public void removeList(int i){
		list.remove(i);
	}
	public Integer getLabelclassificationID() {
		return labelclassificationID;
	}

	public void setLabelclassificationID(Integer labelclassificationID) {
		this.labelclassificationID = labelclassificationID;
	}

	public Integer getSubjectchapterID() {
		return subjectchapterID;
	}

	public void setSubjectchapterID(Integer subjectchapterID) {
		this.subjectchapterID = subjectchapterID;
	}

	public String getSubjectID() {
		return subjectID;
	}

	public void setSubjectID(String subjectID) {
		this.subjectID = subjectID;
	}

	public String getLabel_ThreadVar() {
		return label_ThreadVar;
	}

	public void setLabel_ThreadVar(String label_ThreadVar) {
		this.label_ThreadVar = label_ThreadVar;
	}

	public String getChapter_ThreadVar() {
		return chapter_ThreadVar;
	}

	public void setChapter_ThreadVar(String chapter_ThreadVar) {
		this.chapter_ThreadVar = chapter_ThreadVar;
	}

	public String getVedioartitle_ThreadVar() {
		return vedioartitle_ThreadVar;
	}

	public void setVedioartitle_ThreadVar(String vedioartitle_ThreadVar) {
		this.vedioartitle_ThreadVar = vedioartitle_ThreadVar;
	}

	public String getTypeid() {
		return typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	public String getReturn_result() {
		return return_result;
	}

	public void setReturn_result(String return_result) {
		this.return_result = return_result;
	}

	public String getDownloadVedioartitle_ThreadVar() {
		return downloadVedioartitle_ThreadVar;
	}

	public void setDownloadVedioartitle_ThreadVar(String downloadVedioartitle_ThreadVar) {
		this.downloadVedioartitle_ThreadVar = downloadVedioartitle_ThreadVar;
	}
}
