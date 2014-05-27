package se.chalmers.fitnesstracker;

import se.chalmers.fitnesstracker.R.id;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class FirstLaunchFragment extends Fragment {
	

	
	private Button applyButton;
	private MainActivity main;
	private View rootView;
	EditText person_name;
	EditText person_age;
	EditText person_height;
	EditText person_weigth;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			
		 rootView = inflater.inflate(R.layout.fragment_first_launch, container,
				false);
		
		main = (MainActivity) getActivity();
		applyButton = (Button) rootView.findViewById(id.register_button_apply);
		applyButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences prefs = getActivity().getSharedPreferences(MainActivity.INIT_PREFS, 0);
				SharedPreferences.Editor editor = prefs.edit();
				
				person_name = ((EditText) rootView.findViewById(R.id.editText_person_name));
				person_age = ((EditText) rootView.findViewById(R.id.editText_person_age));
				person_height = ((EditText) rootView.findViewById(R.id.editText_person_height));
				person_weigth = ((EditText) rootView.findViewById(R.id.editText_person_weight));
				/*
				person_name.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						person_name.setText("");
						
					}
				});
				
				person_age.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						person_age.setText("");
						
					}
				});
				
				person_height.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						person_height.setText("");
						
					}
				});
				
				person_weigth.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						person_weigth.setText("");
						
					}
				});
				*/
				String name = person_name.getText().toString();
				String age = person_age.getText().toString();
				String height = person_height.getText().toString();
				String weight = person_weigth.getText().toString();
				
				RadioGroup rg1 = (RadioGroup) 
						rootView.findViewById(R.id.radioGroup1);
				
				RadioButton rb = (RadioButton) rootView.findViewById(rg1
						.getCheckedRadioButtonId());
				
				RadioGroup rg2 = (RadioGroup) 
						rootView.findViewById(R.id.aktivitylevel);
				
				RadioButton rb1 = (RadioButton) rootView.findViewById(rg2
						.getCheckedRadioButtonId());
				
			
				
				if (name == null || name.equals("Enter your name") || name.isEmpty() ) {
					Toast.makeText(rootView.getContext(),
							"You need to enter a name",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (age == null||age.equals("Enter your age") || age.isEmpty() ) {
					Toast.makeText(rootView.getContext(),
							"You need to enter a age",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (height == null||height.equals("Enter your height") || height.isEmpty()) {
					Toast.makeText(rootView.getContext(),
							"You need to enter a height",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (weight == null||weight.equals("Enter your weight") || weight.isEmpty() ) {
					Toast.makeText(rootView.getContext(),
							"You need to enter a weight",
							Toast.LENGTH_SHORT).show();
					return;
				}

				editor.putBoolean(MainActivity.FIRST_TIME, false);
				editor.putString(MainActivity.NAME,name);
				editor.putString(MainActivity.AGE, age);
				editor.putString(MainActivity.HEIGHT, height);
				editor.putFloat(MainActivity.WEIGHT, Float.parseFloat(weight));
				editor.putString(MainActivity.GENDER, rb.getText().toString());
				editor.putString(MainActivity.ACTIVITY_LEVEL, rb1.getText().toString());
				
				editor.apply();
				
				Log.i("User loaded", "Name: "+prefs.getString(MainActivity.NAME,"null"));
				Log.i("User loaded", "Age: "+prefs.getString(MainActivity.AGE,"null"));
				Log.i("User loaded", "weight: "+prefs.getFloat(MainActivity.WEIGHT,0));
				Log.i("User loaded", "height: "+prefs.getString(MainActivity.HEIGHT,"null"));
				Log.i("User loaded", "gender: "+prefs.getString(MainActivity.GENDER,"null"));
				Log.i("User loaded", "activitylevel: "+prefs.getString(MainActivity.ACTIVITY_LEVEL,"null"));
				Log.i("User loaded", "goal velocity: "+prefs.getString(MainActivity.GOAL_VELOCITY,"null"));
				Log.i("User loaded", "goal weight: "+prefs.getString(MainActivity.GOAL_WEIGHT,"null"));
				
				main.displayView(0);
			}
		});
		
		return rootView;
	}
	
	public void onButtonApply(View view){
		
		
		
		
	
	}
}
