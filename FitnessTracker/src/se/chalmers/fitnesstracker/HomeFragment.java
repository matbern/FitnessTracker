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
import android.widget.Toast;

public class HomeFragment extends Fragment {

	private static MainActivity main;
	Handler handler = new Handler();
	private int year, month, day;

	ProgressBarUpdater progressBarUpdater = null;

	private volatile static int progressStatus = 0;
	int progressMax;
	private int progresstotal;
	private int progressfat;
	private int progressprotein;
	private int progresscarbs;

	private ProgressBar progressTotal;
	private ProgressBar progressCarbs;
	private ProgressBar progressFat;
	private ProgressBar progressProtein;

	private TextView textViewTotal;
	private TextView textViewFat;
	private TextView textViewCarbs;
	private TextView textViewProtein;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private String date;
	private View rootView;

	public HomeFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_home, container, false);

		main = (MainActivity) getActivity();

		Bundle bundle = getArguments();
		year = bundle.getInt("year");
		month = bundle.getInt("month");
		day = bundle.getInt("day");

		Button addFood = (Button) rootView.findViewById(id.addFood);
		Button addWorkout = (Button) rootView.findViewById(id.addWorkout);
		Button seeAddedItem = (Button) rootView.findViewById(id.seeAddedItems);

		progressTotal = (ProgressBar) rootView.findViewById(id.progressBarTotal);
		progressCarbs = (ProgressBar) rootView.findViewById(id.progressBarCarbs);
		progressFat = (ProgressBar) rootView.findViewById(id.progressBarFat);
		progressProtein = (ProgressBar) rootView.findViewById(id.ProgressBarProtein);

		textViewTotal = (TextView) rootView.findViewById(id.textView_total);
		textViewFat = (TextView) rootView.findViewById(id.fat_progress);
		textViewCarbs = (TextView) rootView.findViewById(id.carbs_progress);
		textViewProtein = (TextView) rootView.findViewById(id.protein_progress);

		/*
		 * Här ska data hämtas från databasen och beräknas. ProgressBarsen går
		 * från 0-100. Visas i procent så data behövs omvandlas till %BMR=(BMR =
		 * 655.1 + (9.563 x weight in kg) + (1.850 x height in cm) - (4.676 x
		 * age in years))
		 */

		// double actLev =
		// Formatter.parseDouble(prefs.getString(MainActivity.ACTIVITY_LEVEL,"null"));
		SharedPreferences prefs = rootView.getContext().getSharedPreferences(
				MainActivity.INIT_PREFS, 0);
		double height = Formatter.parseDouble(prefs.getString(
				MainActivity.HEIGHT, "null"));
		double age = Formatter.parseDouble(prefs.getString(MainActivity.AGE,
				"null"));
		String actLevel = prefs.getString(MainActivity.ACTIVITY_LEVEL, "null");
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
		double weight = (double) prefs.getFloat(MainActivity.WEIGHT, 0);
		double myBmr = (655.1 + (9.563 * weight) + (1.850 * height) - (4.676 * age));
		double myActivityWeightedBmr = myBmr * al;

		Log.i("date", "myActivityWeightedBmr: " + myActivityWeightedBmr);

		int mo = month + 1; // Nåt fel i calendar, en månad fel. temp lösning
		date = Formatter.makeDateString(year, mo, day); // gör datumet i rätt
														// form så kan jämföras
		Log.i("date", "Date1: " + date);

		EntityManager em = PersistenceFactory.getEntityManager();

		TextView tv1 = (TextView) rootView.findViewById(R.id.sumMat);
		double sumF = 0;
		double sumKolhydrater = 0;
		double sumProtein = 0;
		double sumFat = 0;
		double sumAmount = 0;
		for (EatenFood ef : em.getAll(EatenFood.class)) {
			Log.i("date", "Date2: " + sdf.format(ef.getDate()));
			if (sdf.format(ef.getDate()).equals(date.trim())) {
				double s = Formatter.parseDouble(ef.getCalories());
				sumF = sumF + s;
				double k = Formatter.parseDouble(ef.getCarbs());
				sumKolhydrater = sumKolhydrater + k;
				double p = Formatter.parseDouble(ef.getProteins());
				sumProtein = sumProtein + p;
				double f = Formatter.parseDouble(ef.getFat());
				sumFat = sumFat + f;
				double sa = Formatter.parseDouble(ef.getAmount());
				sumAmount = sumAmount + sa;
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
		String goalspeed = prefs.getString(MainActivity.GOAL_VELOCITY, "null");
		double nyttBmr = myActivityWeightedBmr;
		boolean upp1 = GoalFragment.upp;
		if (goalspeed.isEmpty() || goalspeed.equalsIgnoreCase("null")) {
			nyttBmr = myActivityWeightedBmr;
		}
		if (goalspeed.equalsIgnoreCase("snabb") && upp1 == false) {

			nyttBmr = myActivityWeightedBmr - 1000;
		}
		if (goalspeed.equalsIgnoreCase("medel") && upp1 == false) {

			nyttBmr = myActivityWeightedBmr - 500;
		}
		if (goalspeed.equalsIgnoreCase("långsam") && upp1 == false) {

			nyttBmr = myActivityWeightedBmr - 250;
		}
		if (goalspeed.equalsIgnoreCase("snabb") && upp1 == true) {

			nyttBmr = myActivityWeightedBmr + 1000;
		}
		if (goalspeed.equalsIgnoreCase("medel") && upp1 == true) {

			nyttBmr = myActivityWeightedBmr + 500;
		}
		if (goalspeed.equalsIgnoreCase("långsam") && upp1 == true) {

			nyttBmr = myActivityWeightedBmr + 250;
		}
		tv4.setText(Formatter.doubleToString(nyttBmr));

		TextView tv5 = (TextView) rootView.findViewById(R.id.goalMinusCal);
		tv5.setText(Formatter.doubleToString(nyttBmr - totSum));

		Double totProg = (totSum / nyttBmr) * 100;
		Integer tp = totProg.intValue();
		Log.i("date", "Date2: " + tp);
		progressTotal.setProgress(tp);
		if (tp > 100) {
			Toast.makeText(rootView.getContext(),
					"You have consumed more calories than your goal amount",
					Toast.LENGTH_SHORT).show();
		}

		Double procentK = (sumKolhydrater / sumAmount) * 100;
		Double procentF = (sumFat / sumAmount) * 100;
		Double procentP = (sumProtein / sumAmount) * 100;

		Integer pK = procentK.intValue();
		Integer pF = procentF.intValue();
		Integer pP = procentP.intValue();

		progressCarbs.setProgress(pK);
		progressFat.setProgress(pF);
		progressProtein.setProgress(pP);
		progressCarbs.setMax(100);
		progressFat.setMax(100);
		progressProtein.setMax(100);
		progressTotal.setMax(100);
		
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
			progressCarbs.incrementProgressBy(progress);
			break;

		case R.id.progressBarFat:
			progressFat.incrementProgressBy(progress);
			break;
		case R.id.ProgressBarProtein:
			progressProtein.incrementProgressBy(progress);
			break;

		default:
			break;
		}

		progresstotal = progressTotal.getProgress();
		progresscarbs = progressCarbs.getProgress();
		progressfat = progressFat.getProgress();
		progressprotein = progressProtein.getProgress();
		progressMax = 100;

		//if (progressBarUpdater == null)
			progressBarUpdater = new ProgressBarUpdater();
		progressBarUpdater.execute(null, null, null);
		progressBarUpdater.getStatus();
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
				textViewTotal.setText(progressStatus + "%");
			}
			if (progressStatus <= progresscarbs) {
				progressCarbs.setProgress(progressStatus);
				textViewCarbs.setText(progressStatus + "%");
			}

			if (progressStatus <= progressfat) {
				progressFat.setProgress(progressStatus);
				textViewFat.setText(progressStatus + "%");
			}
			if (progressStatus <= progressprotein) {
				progressProtein.setProgress(progressStatus);
				textViewProtein.setText(progressStatus + "%");
			}
			progressStatus++;
			
		}

	}

}
