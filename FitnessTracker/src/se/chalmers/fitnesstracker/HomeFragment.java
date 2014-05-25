package se.chalmers.fitnesstracker;

import se.chalmers.fitnesstracker.R.id;
import android.app.Fragment;
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

	private volatile int progressStatus = 0;
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
		
		progressTotal.setProgress(75);
		progressFood.setProgress(50);
		progressWorkout.setProgress(80);
		
		
		new Thread(new Runnable() {
		     public void run() {
		    	progresstotal =  progressTotal.getProgress();
		    	progressfood =  progressFood.getProgress();
		    	progressworkout =  progressWorkout.getProgress();
		    	
		    	int progress = Math.max(Math.max(progresstotal, progressfood), progressworkout);
		    	progressStatus = 0;
		        while (progressStatus <= progress) {
		           progressStatus += 1;
		    // Update the progress bar and display the 
		    //current value in the text view
		    handler.post(new Runnable() {
		    public void run() {
		    
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
		    
		    }
		        });
		        try {
		           // Sleep for 200 milliseconds. 
		                         //Just to display the progress slowly
		           Thread.sleep(50);
		        } catch (InterruptedException e) {
		           e.printStackTrace();
		        }
		     }
		  }
		  }).start();
		
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



}
