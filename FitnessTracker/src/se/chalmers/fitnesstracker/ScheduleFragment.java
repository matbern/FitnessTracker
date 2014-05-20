package se.chalmers.fitnesstracker;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;

public class ScheduleFragment extends Fragment {
	
	private CalendarView cal;
	Long date;
	
	public ScheduleFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);
        cal = (CalendarView) rootView.findViewById(R.id.calendarView1);
        date = cal.getDate();
        cal.setOnDateChangeListener(new OnDateChangeListener() {
			
		@Override
		public void onSelectedDayChange(CalendarView view, int year, int month,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			if(cal.getDate() != date){
			MainActivity main = (MainActivity)getActivity();
			MainActivity.bundle.putInt("year", year);
			MainActivity.bundle.putInt("month", month);
			MainActivity.bundle.putInt("day", dayOfMonth);
			
			main.displayView(0);
			
			Toast.makeText(getActivity(),"Selected Date is\n\n"
				+dayOfMonth+" : "+ (month+1) +" : "+year , 
				Toast.LENGTH_LONG).show();
			}
		}
	});
        
        
        return rootView;
    }
	
	
}
