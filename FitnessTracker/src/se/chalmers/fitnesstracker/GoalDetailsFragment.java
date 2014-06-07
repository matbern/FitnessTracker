package se.chalmers.fitnesstracker;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class GoalDetailsFragment extends Fragment {
	public View rootView;
	public TextView et; 
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
			rootView = inflater.inflate(R.layout.fragment_goaldetails,
                container, false);
			
			et = (TextView) rootView.findViewById(R.id.goalDetailsView);
			
			SharedPreferences prefs = rootView.getContext().getSharedPreferences(
					MainActivity.INIT_PREFS, 0);
			String p1 = prefs.getString(MainActivity.GOAL_VELOCITY,"null");
			String p2 = prefs.getString(MainActivity.GOAL_WEIGHT,"null");
			if (p1.equalsIgnoreCase("null") || p2.equalsIgnoreCase("null")){
				et.setText("Du har inte specificerat något mål");
			}
			else {
				et.setText("Din målvikt är: " + p2 +"kg" + "\nDin målhastighet är: " + p1);
			}
			
			Button b1 = (Button) rootView.findViewById(R.id.deleteGoal);
			b1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					SharedPreferences prefs = rootView.getContext().getSharedPreferences(
							MainActivity.INIT_PREFS, 0);
					Editor e = prefs.edit();
					e.putString(MainActivity.GOAL_WEIGHT, "null");
					e.apply();
					e.putString(MainActivity.GOAL_VELOCITY, "null");
					e.apply();
					et.setText("Du har inte specificerat något mål");
				
				}
			});
			return rootView;
	}
	

}
