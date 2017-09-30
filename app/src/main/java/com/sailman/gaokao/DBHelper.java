
package com.sailman.gaokao;



import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "gaokao.db";
	private static final int DATABASE_VERSION = 1;
	private Context context;
	public DBHelper(Context context) {
		//CursorFactory设置为null,使用默认值
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context=context;
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		try{
			// TODO Auto-generated method stub

			db.execSQL("CREATE TABLE IF NOT EXISTS gaokao_vedioartitle(vedioartitleid integer PRIMARY KEY," +
					"title char(400)," +
					"url   char(400)," +
					"subjectid char(3)," +
					"typeid char(3)," +
					"keyword char(200)," +
					"imageurl char(400)," +
					"comments char(1200)," +
					"publication date," +
					"inputdate date," +
					"labelclassificationid integer," +
					"subjectchapterid integer," +
					"vedio char(1)," +
					"content char(1)," +
					"learn char(1)," +
					"stay char(1))");
			db.execSQL("CREATE TABLE IF NOT EXISTS gaokao_labelclassification(labelclassificationid integer PRIMARY KEY," +
					"subjectid char(3)," +
					"labelname char(40))");
			db.execSQL("CREATE TABLE IF NOT EXISTS gaokao_subjectchapter(subjectchapterid integer PRIMARY KEY," +
					"subjectid char(3)," +
					"chaptername char(40))");
			db.execSQL("CREATE TABLE IF NOT EXISTS gaokao_lookrec(vedioartitleid integer ," +
					"lookdate date,  PRIMARY KEY(vedioartitleid,lookdate))");

		}catch(Exception e){
			System.out.println(e.toString());
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
