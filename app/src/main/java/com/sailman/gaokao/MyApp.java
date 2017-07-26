

package com.sailman.gaokao;


import android.app.Application;

import java.text.SimpleDateFormat;
import java.util.TimeZone;


public class MyApp extends Application {

	private Integer labelclassificationID;
	private Integer subjectchapterID;
	private String subjectID;

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
}
