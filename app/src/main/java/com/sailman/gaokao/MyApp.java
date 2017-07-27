

package com.sailman.gaokao;


import android.app.Application;

import java.text.SimpleDateFormat;
import java.util.TimeZone;


public class MyApp extends Application {

	private Integer labelclassificationID;
	private Integer subjectchapterID;
	private String subjectID;
	private String label_ThreadVar;
	private String chapter_ThreadVar;

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
}
