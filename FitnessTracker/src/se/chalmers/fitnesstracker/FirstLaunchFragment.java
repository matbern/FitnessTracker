package se.chalmers.fitnesstracker;

import se.chalmers.fitnesstracker.R.id;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FirstLaunchFragment extends Fragment {
	
	private static String FIRST_TIME = "FirstTime";
	private static String INIT_PREFS = "InitPrefs";
	
	private Button applyButton;
	private MainActivity main;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			
		View rootView = inflater.inflate(R.layout.fragment_first_launch, container,
				false);
		
		main = (MainActivity) getActivity();
		applyButton = (Button) rootView.findViewById(id.register_button_apply);
		applyButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences prefs = getActivity().getSharedPreferences(INIT_PREFS, 0);
				SharedPreferences.Editor editor = prefs.edit();
				editor.putBoolean(FIRST_TIME, false);
				editor.apply();
				
				main.displayView(0);
			}
		});
		
		return rootView;
	}
	
	public void onButtonApply(View view){
		
		
		
		
	
	}
}
