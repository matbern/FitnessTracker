package se.chalmers.fitnesstracker;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

public class HomeFragment extends Fragment {

	private CalendarView calendar;
	private TextView text;

	public HomeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_home, container,
				false);

		//calendar = (CalendarView) rootView.findViewById(id.calendarView1);
		//calendar.setOnDateChangeListener(this);
		//calendar = (CalendarView) getView().findViewById(R.id.calendarView1);
		//text = (TextView) rootView.findViewById(id.textView1);
		
		return rootView;
	}



}
