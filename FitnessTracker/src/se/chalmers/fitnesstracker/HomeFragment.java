package se.chalmers.fitnesstracker;

import java.text.SimpleDateFormat;

import se.chalmers.fitnesstracker.R.id;
import se.chalmers.fitnesstracker.database.entities.CompletedWorkout;
import se.chalmers.fitnesstracker.database.entities.EatenFood;
import se.chalmers.fitnesstracker.database.entitymanager.EntityManager;
import se.chalmers.fitnesstracker.database.entitymanager.PersistenceFactory;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HomeFragment extends Fragment {

	private static MainActivity main;
	Handler handler = new Handler();
	private int year, month, day;

	ProgressBarUpdater progressBarUpdater = null;

	private volatile static int progressStatus = 0;
	int progressMax;
	private int progresstotal;
	private int progressfood;
	private int progressworkout;

	private ProgressBar progressTotal;
	private ProgressBar progressFood;
	private ProgressBar progressWorkout;

	private TextView textViewTotal;
	private TextView textViewFood;
	private TextView textViewWorkout;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private String date;
	private View rootView;

	public HomeFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_home, container,
				false);

		main = (MainActivity) getActivity();

		Bundle bundle = getArguments();
		year = bundle.getInt("year");
		month = bundle.getInt("month");
		day = bundle.getInt("day");

		Button addFood = (Button) rootView.findViewById(id.addFood);
		Button addWorkout = (Button) rootView.findViewById(id.addWorkout);
		Button seeAddedItem = (Button) rootView.findViewById(id.seeAddedItems);

		progressTotal = (ProgressBar) rootView
				.findViewById(id.progressBarTotal);
		progressFood = (ProgressBar) rootView.findViewById(id.progressBarCarbs);
		progressWorkout = (ProgressBar) rootView
				.findViewById(id.progressBarFat);

		// textViewTotal = (TextView) rootView.findViewById(id.textView_total);
		// textViewFood = (TextView) rootView.findViewById(id.textView_food);
		// textViewWorkout = (TextView)
		// rootView.findViewById(id.textView_workout);

		/*
		 * Här ska data hämtas från databasen och beräknas. ProgressBarsen går
		 * från 0-100. Visas i procent så data behövs omvandlas till %BMR=(BMR =
		 * 655.1 + (9.563 x weight in kg) + (1.850 x height in cm) - (4.676 x
		 * age in years))
		 */

		// double actLev =
		// Formatter.parseDouble(prefs.getString(MainActivity.ACTIVITY_LEVEL,"null"));
		SharedPreferences prefs = rootView.getContext()
				.getSharedPreferences(MainActivity.INIT_PREFS, 0);
		double height = Formatter.parseDouble(prefs.getString(
				MainActivity.HEIGHT, "null"));
		double age = Formatter.parseDouble(prefs.getString(
				MainActivity.AGE, "null"));
		String actLevel = prefs.getString(
				MainActivity.ACTIVITY_LEVEL, "null");
		double al = 0;
		if (actLevel.equalsIgnoreCase("minimal")) {
			al = 1.2;
		}
		if (actLevel.equalsIgnoreCase("låg")) {
			al = 1.37;
		}
		if (actLevel.equalsIgnoreCase("medel")) {
			al = 1.55;
		}
		
		if (actLevel.equalsIgnoreCase("hög")) {
			al = 1.75;
		}
		double weight = (double) prefs.getFloat(MainActivity.WEIGHT,0);
		double myBmr = (655.1 + (9.563 * weight) + (1.850 * height) -(4.676 *age));
		double myActivityWeightedBmr = myBmr * al;

		Log.i("date",
				"myActivityWeightedBmr: "
						+ myActivityWeightedBmr);

		int mo = month + 1; // Nåt fel i calendar, en månad fel. temp lösning
		date = Formatter.makeDateString(year, mo, day); // gör datumet i rätt
														// form så kan jämföras
		Log.i("date", "Date1: " + date);

		EntityManager em = PersistenceFactory.getEntityManager();

		TextView tv1 = (TextView) rootView.findViewById(R.id.sumMat);
		double sumF = 0;
		for (EatenFood ef : em.getAll(EatenFood.class)) {
			Log.i("date", "Date2: " + sdf.format(ef.getDate()));
			if (sdf.format(ef.getDate()).equals(date.trim())) {
				double s = Formatter.parseDouble(ef.getCalories());
				sumF = sumF + s;
			}
		}
		tv1.setText(Formatter.doubleToString(sumF));

		TextView tv2 = (TextView) rootView.findViewById(R.id.workoutsumma);
		double sumW = 0;
		for (CompletedWorkout cw : em.getAll(CompletedWorkout.class)) {
			if (sdf.format(cw.getDate()).equals(date.trim())) {
				double s = Formatter.parseDouble(cw.getCalories());
				sumW = sumW + s;
			}
		}
		tv2.setText(Formatter.doubleToString(sumW));

		TextView tv3 = (TextView) rootView.findViewById(R.id.totalCalSum);
		double totSum = sumF - sumW;
		tv3.setText(Formatter.doubleToString(totSum));

		TextView tv4 = (TextView) rootView.findViewById(R.id.goalSum);
		tv4.setText(Formatter.doubleToString(myActivityWeightedBmr));

		progressTotal.setProgress(75);
		progressFood.setProgress(50);
		progressWorkout.setProgress(80);

		updateProgressBar(0, 0);

		/*
		 * Listeners for the buttons
		 */
		addFood.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				main.displayView(4);

			}
		});

		addWorkout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				main.displayView(3);

			}
		});

		seeAddedItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				main.displayView(2);

			}
		});

		return rootView;
	}

	private void updateProgressBar(int progressBar, int progress) {

		switch (progressBar) {
		case R.id.progressBarTotal:
			progressTotal.incrementProgressBy(progress);
			break;

		case R.id.progressBarCarbs:
			progressFood.incrementProgressBy(progress);
			break;

		case R.id.progressBarFat:
			progressWorkout.incrementProgressBy(progress);
			break;

		default:
			break;
		}

		progresstotal = progressTotal.getProgress();
		progressfood = progressFood.getProgress();
		progressworkout = progressWorkout.getProgress();
		progressMax = Math.max(Math.max(progresstotal, progressfood),
				progressworkout);

		if (progressBarUpdater == null)
			progressBarUpdater = new ProgressBarUpdater();
		progressBarUpdater.execute(null, null, null);
	}

	private class ProgressBarUpdater extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			progressStatus = 0;
			while (progressStatus <= progressMax) {
				publishProgress();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			if (progressStatus <= progresstotal) {
				progressTotal.setProgress(progressStatus);
				// textViewTotal.setText(progressStatus + "%");
			}
			if (progressStatus <= progressfood) {
				progressFood.setProgress(progressStatus);
				// textViewFood.setText(progressStatus + "%");
			}

			if (progressStatus <= progressworkout) {
				progressWorkout.setProgress(progressStatus);
				// textViewWorkout.setText(progressStatus + "%");
			}
			progressStatus++;
		}

	}

}
