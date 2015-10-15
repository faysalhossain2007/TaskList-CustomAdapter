package bsadd.TodoList;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import bsadd.TodoList.adapter.TaskListAdapter;
import bsadd.TodoList.dao.TaskDAO;
import bsadd.TodoList.dao.TaskDBHelper;
import bsadd.TodoList.model.Task;

public class CustomListViewActivity extends Activity {
	ListAdapter listAdapter;
	TaskDBHelper sqlHelper;
	CustomListViewActivity context;
	ListView lv;
	TaskListAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_task_list);		
		context = CustomListViewActivity.this;
		lv=(ListView) findViewById(R.id.lv_task_list);
		
		updateUI();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_add_task:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(getResources().getString(R.string.add_task));
			builder.setMessage(getResources().getString(R.string.dialog_desc));
			final EditText inputTitle = new EditText(this);
			inputTitle.setHint(getResources().getString(R.string.title));
			final EditText inputText = new EditText(this);
			inputText.setHint(getResources().getString(R.string.text));

			// adding two input field to a linear layout and attach it to the
			// dialog view
			LinearLayout ll = new LinearLayout(this);
			ll.setOrientation(LinearLayout.VERTICAL);
			ll.addView(inputTitle);
			ll.addView(inputText);
			builder.setView(ll);

			builder.setPositiveButton(getResources().getString(R.string.add),
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface,
								int i) {
							String title = inputTitle.getText().toString();
							String text = inputText.getText().toString();
							Task tsk = new Task(title, text);

							TaskDAO tskDao = new TaskDAO(context);
							tskDao.addTask(tsk);							

							updateUI();
						}
					});

			builder.setNegativeButton(
					getResources().getString(R.string.cancel), null);

			builder.create().show();
			return true;

		default:
			return false;
		}
	}

	private void updateUI() {
		TaskDAO tskDao = new TaskDAO(context);		
		List<Task> valueList=tskDao.getAllTasks();
		adapter = new TaskListAdapter(context, R.layout.task_list, valueList);
		lv.setAdapter(adapter);		
	}
	
	@Override
	public void onResume() {
	    super.onResume();	    
	}

}
