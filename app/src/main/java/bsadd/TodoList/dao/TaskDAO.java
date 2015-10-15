package bsadd.TodoList.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import bsadd.TodoList.model.Task;
import bsadd.TodoList.util.DatabaseConstant;

public class TaskDAO {

	TaskDBHelper initializer;
	Context context;

	public TaskDAO(Context mContext) {
		context = mContext;
		initializer = new TaskDBHelper(context);
		initializer.getWritableDatabase().close();
	}

	public int getCount(int categoryId) {
		return 0;

	}


	public Cursor getTasksCursor()
	{
		SQLiteDatabase db = initializer.getWritableDatabase();
		Cursor cursor = db.query(DatabaseConstant.TASK_TABLE,
				new String[] { DatabaseConstant.KEY_TASK_ID,
						DatabaseConstant.KEY_TASK_TITLE }, null, null, null,
				null, null);
		return cursor;
	}
	
	public List<Task> getAllTasks() {

		SQLiteDatabase db = initializer.getWritableDatabase();
		List<Task> tasks = new ArrayList<Task>();

		Cursor cursor = db.query(DatabaseConstant.TASK_TABLE, null, null,
				null, null, null, null);

		int id;
		String title, text;
		if (cursor.moveToFirst()) {
			do {
				id = cursor.getInt(cursor
						.getColumnIndex(DatabaseConstant.KEY_TASK_ID));
				title = cursor.getString(cursor
						.getColumnIndex(DatabaseConstant.KEY_TASK_TITLE));
				text = cursor.getString(cursor
						.getColumnIndex(DatabaseConstant.KEY_TASK_TEXT));

				tasks.add(new Task(id, title, text));

			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return tasks;
	}

	public void addTask(Task tsk) {

		SQLiteDatabase db = initializer.getWritableDatabase();

		ContentValues values = new ContentValues();
		// values.put(DatabaseInitializer.KEY_TASK_ID, tsk.getID());
		values.put(DatabaseConstant.KEY_TASK_TITLE, tsk.getTitle());
		values.put(DatabaseConstant.KEY_TASK_TEXT, tsk.getText());

		db.insert(DatabaseConstant.TASK_TABLE, null, values);

		db.close();
	}

	public void deleteTask(String title) {

		SQLiteDatabase db = initializer.getWritableDatabase();
		String sql = String.format("DELETE FROM %s WHERE %s = '%s'",
				DatabaseConstant.TASK_TABLE,
				DatabaseConstant.KEY_TASK_TITLE, title);
		db.execSQL(sql);
		db.close();
	}

}
