package se.chalmers.fitnesstracker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import se.chalmers.fitnesstracker.database.entities.CompletedWorkout;
import se.chalmers.fitnesstracker.database.entities.EatenFood;
import se.chalmers.fitnesstracker.database.entitymanager.EntityManager;
import se.chalmers.fitnesstracker.database.entitymanager.PersistenceFactory;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class AddedItemsFragment extends Fragment {
	public static final String TAG = AddedItemsFragment.class.getSimpleName();
	private EditText date;
	public View rootView;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public AddedItemsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_added_items, container,
				false);

		Calendar c = Calendar.getInstance();

		date = (EditText) rootView.findViewById(R.id.addedDates);
		date.setKeyListener(null);
		date.setText(sdf.format(c.getTime()));

		date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String dateText = ((TextView) v).getText().toString();
				int year, month, day;
				String temp[] = dateText.split("-");
				year = Integer.valueOf(temp[0]);
				month = Integer.valueOf(temp[1]);
				day = Integer.valueOf(temp[2]);
				DatePickerDialog dtpkrdlg = new DatePickerDialog(rootView
						.getContext(), 0, new DateSetListener(), year,
						month - 1, day);
				dtpkrdlg.show();

			}

		});
		updateData();

		return rootView;
	}

	class DateSetListener implements OnDateSetListener {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			date.setText("" + year + "-" +

			Formatter.padStringWithZero(monthOfYear + 1) + "-"
					+ Formatter.padStringWithZero(dayOfMonth));
			updateData();
		}
	}

	public void updateData() {

		EntityManager em = PersistenceFactory.getEntityManager();
		final LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.layout);
		ll.removeAllViews();

		for (final EatenFood ef : em.getAll(EatenFood.class)) {
			if (sdf.format(ef.getDate()).equals(
					date.getText().toString().trim())) {
				StringBuffer sb = new StringBuffer();
				sb.append(ef.getDate() + "\n");
				sb.append(ef.getName() + "\n");
				sb.append(ef.getCalories() + "\n");
				sb.append(ef.getCarbs() + "\n");
				sb.append(ef.getFat() + "\n");
				sb.append(ef.getProteins() + "\n");
				sb.append("\n\n");
				final TextView tv = new TextView(rootView.getContext());
				tv.setText(sb.toString());
				LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				ll.addView(tv, lp);
				final Button b = new Button(rootView.getContext());
				b.setText("Ta bort");
				b.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						EntityManager em = PersistenceFactory.getEntityManager();
						em.delete(ef);
						String name = v.toString();
						Log.d(TAG, "Name: "+name);
						ll.removeView(tv);
						ll.removeView(b);
					}
				});
				LayoutParams lp2 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				ll.addView(b, lp2);
				
			}
		}
		for (final CompletedWorkout ef : em.getAll(CompletedWorkout.class)) {
			if (sdf.format(ef.getDate()).equals(
					date.getText().toString().trim())) {
				StringBuffer sb = new StringBuffer();
				sb.append(ef.getDate() + "\n");
				sb.append(ef.getName() + "\n");
				sb.append(ef.getCalories() + "\n");
				sb.append("\n\n");
				final TextView tv2 = new TextView(rootView.getContext());
				tv2.setText(sb.toString());
				LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				ll.addView(tv2, lp);
				final Button b = new Button(rootView.getContext());
				b.setText("Ta bort");
				b.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						EntityManager em = PersistenceFactory.getEntityManager();
						em.delete(ef);
						ll.removeView(tv2);
						ll.removeView(b);
					}
				});
				LayoutParams lp2 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				ll.addView(b, lp2);
			}
		}
	}
}
