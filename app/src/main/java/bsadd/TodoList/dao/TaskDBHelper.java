package bsadd.TodoList.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import bsadd.TodoList.util.DatabaseConstant;

public class TaskDBHelper extends SQLiteOpenHelper {

	String TAG = "TaskDBHelper";

	public TaskDBHelper(Context context) {
		super(context, DatabaseConstant.DB_NAME, null,
				DatabaseConstant.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String query = String.format("CREATE TABLE "
				+ DatabaseConstant.TASK_TABLE + " ("
				+ DatabaseConstant.KEY_TASK_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ DatabaseConstant.KEY_TASK_TITLE + " TEXT, "
				+ DatabaseConstant.KEY_TASK_TEXT + " TEXT )");

		Log.v(TAG, "Query : " + query);
		db.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqlDB, int oldVersion, int newVersion) {
		Log.v(TAG, oldVersion + " -> " + newVersion);

		sqlDB.execSQL("DROP TABLE IF EXISTS " + DatabaseConstant.TASK_TABLE);
		onCreate(sqlDB);
	}
}
