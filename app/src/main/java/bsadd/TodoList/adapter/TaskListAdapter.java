package bsadd.TodoList.adapter;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import bsadd.TodoList.R;
import bsadd.TodoList.dao.TaskDAO;
import bsadd.TodoList.model.Task;

public class TaskListAdapter extends ArrayAdapter<Task> {

	private int rowview_id;
	private Context mContext;
	private List<Task> valueList;

	public TaskListAdapter(Context context, int textViewResourceId,
			List<Task> valueList) {
		super(context, textViewResourceId, valueList);
		this.mContext = context;
		this.rowview_id = textViewResourceId;
		this.valueList = valueList;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {

		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(rowview_id, null);
		}
		TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);

		Button bDone = (Button) view.findViewById(R.id.b_done);

		Typeface font = Typeface.createFromAsset(mContext.getAssets(),
				"pmoc.ttf");
		tvTitle.setTypeface(font);

		tvTitle.setText(valueList.get(position).getTitle());

		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, valueList.get(position).getText(),
						Toast.LENGTH_LONG).show();

			}
		});
		bDone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				TaskDAO tskDao = new TaskDAO(mContext);
				tskDao.deleteTask(valueList.get(position).getTitle());
				valueList.remove(valueList.get(position));
				notifyDataSetChanged();
			}
		});

		view.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				builder.setItems(R.array.option_arr,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// The 'which' argument contains the index
								// position
								// of the selected item
								switch (which) {

								case 0:
									Toast.makeText(mContext,
											"You Have Selected " + which,
											Toast.LENGTH_LONG).show();
									break;
								case 1:
									Toast.makeText(mContext,
											"You Have Selected " + which,
											Toast.LENGTH_LONG).show();
									break;
								default:
									break;
								}
							}
						}).show();
				return false;

			}
		});

		return view;
	}

}
