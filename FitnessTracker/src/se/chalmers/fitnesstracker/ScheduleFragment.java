package se.chalmers.fitnesstracker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import se.chalmers.fitnesstracker.R.id;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
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
        
        
        Button todayDate = (Button) rootView.findViewById(id.dateOfToday);
        todayDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MainActivity main = (MainActivity)getActivity();
				Calendar calendar = new GregorianCalendar();
				MainActivity.bundle.putInt("year", calendar.get(Calendar.YEAR));
				MainActivity.bundle.putInt("day", calendar.get(Calendar.DAY_OF_MONTH));
				MainActivity.bundle.putInt("month", calendar.get(Calendar.MONTH));
				
				main.displayView(0);
				String selDate = Formatter.makeDateString(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH)+1), calendar.get(Calendar.DAY_OF_MONTH));
				Toast.makeText(getActivity(),"Selected Date is\n\n"
						+selDate, 
						Toast.LENGTH_LONG).show();
			}
		});
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
			String selDate2 = Formatter.makeDateString(year, (month+1), dayOfMonth);
			Toast.makeText(getActivity(),"Selected Date is\n\n"
				+selDate2, 
				Toast.LENGTH_LONG).show();
			}
		}
	});
        
        
        return rootView;
    }
	
	
}
