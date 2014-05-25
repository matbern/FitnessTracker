package se.chalmers.fitnesstracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;

import se.chalmers.fitnesstracker.database.entities.CompletedWorkout;
import se.chalmers.fitnesstracker.database.entities.Workout;
import se.chalmers.fitnesstracker.database.entitymanager.EntityManager;
import se.chalmers.fitnesstracker.database.entitymanager.PersistenceFactory;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.DatePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class WorkoutFragment extends Fragment {
	public View rootView;
	private AutoCompleteTextView mSelectWorkout;
	private EditText date;
	private Workout selectedWorkout = null;

	public WorkoutFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater
				.inflate(R.layout.fragment_workout, container, false);
		mSelectWorkout = (AutoCompleteTextView) rootView
				.findViewById(R.id.workoutw);

		Calendar c = Calendar.getInstance();

		date = (EditText) rootView.findViewById(R.id.workoutwwww);
		date.setKeyListener(null);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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

		Button b1 = (Button) rootView.findViewById(R.id.addWo);
		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (selectedWorkout == null) {
					Toast.makeText(rootView.getContext(),
							"You need to select a workout item",
							Toast.LENGTH_SHORT).show();
					return;
				}
				EditText et = (EditText) rootView.findViewById(R.id.timeamount);
				String amountStr = et.getText().toString();
				if (amountStr.isEmpty()) {
					Toast.makeText(rootView.getContext(),
							"You need to insert an amount", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				Double amount = Formatter.parseDouble(amountStr) / 30;

				Double kcal = selectedWorkout.getCalories() * amount;

				((TextView) rootView.findViewById(R.id.workOname))
						.setText("Namn: " + selectedWorkout.getName());
				((TextView) rootView.findViewById(R.id.workamount))
						.setText("Tid:" + amountStr);
				((TextView) rootView.findViewById(R.id.workCalories))
						.setText("Kcal: " + Formatter.doubleToString(kcal));

				EntityManager em = PersistenceFactory.getEntityManager();
				CompletedWorkout cw = new CompletedWorkout();
				cw.setName(selectedWorkout.getName());
				cw.setCalories(Formatter.doubleToString(kcal));
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date dateStr = null;
				try {
					dateStr = formatter.parse(date.getText().toString());
				} catch (ParseException e) {
				}
				cw.setDate(dateStr);
				em.persist(cw);
				Log.i("" + WorkoutFragment.class,
						"Added workout:" + cw.toString());
			}
		});

		mSelectWorkout.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				selectedWorkout = null;
				EntityManager em = PersistenceFactory.getEntityManager();
				ArrayList<Workout> list = new ArrayList<Workout>();

				for (Workout w : em.getWhere(Workout.class,
						"name LIKE '%" + s.toString() + "%'", "4")) {
					list.add(w);
				}
				AutoCompleteTextView txt = (AutoCompleteTextView) rootView
						.findViewById(R.id.workoutw);
				ArrayAdapter<Workout> adp = new ArrayAdapter<Workout>(rootView
						.getContext(),
						android.R.layout.simple_dropdown_item_1line, list);
				txt.setThreshold(3);
				txt.setAdapter(adp);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		mSelectWorkout.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				rootView.findViewById(R.id.workamount).requestFocus();
				selectedWorkout = (Workout) parent.getAdapter().getItem(0);
			}
		});

		return rootView;
	}

	class DateSetListener implements OnDateSetListener {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			date.setText("" + year + "-" +

			Formatter.padStringWithZero(monthOfYear + 1) + "-"
					+ Formatter.padStringWithZero(dayOfMonth));

		}
	}
}
