package se.chalmers.fitnesstracker;

import se.chalmers.fitnesstracker.R.id;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
	private int year,month,day;

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
	
	
	public HomeFragment() {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			
		View rootView = inflater.inflate(R.layout.fragment_home, container,
				false);
		
		main = (MainActivity)getActivity();
		
		Bundle bundle = getArguments();
		year = bundle.getInt("year");
		month = bundle.getInt("month");
		day = bundle.getInt("day");
		
		Button addFood = (Button) rootView.findViewById(id.addFood);
		Button addWorkout = (Button) rootView.findViewById(id.addWorkout);
		Button seeAddedItem = (Button) rootView.findViewById(id.seeAddedItems);
		
		progressTotal = (ProgressBar) rootView.findViewById(id.progressBarTotal);
		progressFood = (ProgressBar) rootView.findViewById(id.progressBarFood);
		progressWorkout = (ProgressBar) rootView.findViewById(id.progressBarWorkout);
		
		textViewTotal = (TextView) rootView.findViewById(id.textView_total);
		textViewFood = (TextView) rootView.findViewById(id.textView_food);
		textViewWorkout = (TextView) rootView.findViewById(id.textView_workout);
		
		/*
		 * Här ska data hämtas från databasen och beräknas.
		 * ProgressBarsen går från 0-100. Visas i procent så data behövs omvandlas till %
		 */
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

	private void updateProgressBar(int progressBar, int progress){
		
		switch (progressBar) {
		case R.id.progressBarTotal:
			progressTotal.incrementProgressBy(progress);
			break;

		case R.id.progressBarFood:
			progressFood.incrementProgressBy(progress);
			break;
			
		case R.id.progressBarWorkout:
			progressWorkout.incrementProgressBy(progress);
			break;
			
		default:
			break;
		}
		
		progresstotal =  progressTotal.getProgress();
    	progressfood =  progressFood.getProgress();
    	progressworkout =  progressWorkout.getProgress();
		progressMax = Math.max(Math.max(progresstotal, progressfood), progressworkout);
		
		if(progressBarUpdater == null)
			progressBarUpdater = new ProgressBarUpdater();
		progressBarUpdater.execute(null,null,null);
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
	    	if(progressStatus <= progresstotal ){
			       progressTotal.setProgress(progressStatus);
			       textViewTotal.setText(progressStatus+"%");
			    }
			    if(progressStatus <= progressfood){
				       progressFood.setProgress(progressStatus);
				       textViewFood.setText(progressStatus+"%");
				}
			    
			    if(progressStatus <= progressworkout){
				       progressWorkout.setProgress(progressStatus);
				       textViewWorkout.setText(progressStatus+"%");
				}
	        progressStatus++;
	    }
	}


}
