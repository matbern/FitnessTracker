package se.chalmers.fitnesstracker;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends Fragment {

	private Bundle bundle;
	private int year,month,day;
	private TextView text;

	public HomeFragment() {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			
		View rootView = inflater.inflate(R.layout.fragment_home, container,
				false);
		bundle = getArguments();
		year = bundle.getInt("year");
		month = bundle.getInt("month");
		day = bundle.getInt("day");
		
		text = (TextView) rootView.findViewById(R.id.textView1);
		text.setText(day + "/" + (month+1) + "/" + year);
		
		return rootView;
	}



}
