package bsadd.TodoList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import bsadd.TodoList.dao.TaskDAO;
import bsadd.TodoList.dao.TaskDBHelper;
import bsadd.TodoList.model.Task;
import bsadd.TodoList.util.DatabaseConstant;

public class MainActivity extends ListActivity {
	private ListAdapter listAdapter;
	private TaskDBHelper sqlHelper;
	private MainActivity context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activy_main);
		context = MainActivity.this;
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
		Cursor cursor = tskDao.getTasksCursor();

		// default adapter
		listAdapter = new SimpleCursorAdapter(this, R.layout.task_list, cursor,
				new String[] { DatabaseConstant.KEY_TASK_TITLE },
				new int[] { R.id.tv_title }, 0);
		
		this.setListAdapter(listAdapter);



	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Toast.makeText(MainActivity.this, "You Touched at " + position,
				Toast.LENGTH_SHORT).show();
	}

	public void onDoneButtonClick(View view) {
		View v = (View) view.getParent();
		TextView tvTitle = (TextView) v.findViewById(R.id.tv_title);
		String title = tvTitle.getText().toString();

		TaskDAO tskDao = new TaskDAO(context);
		tskDao.deleteTask(title);

		updateUI();
	}
}
