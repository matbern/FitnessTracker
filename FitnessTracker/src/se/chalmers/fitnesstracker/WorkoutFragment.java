package se.chalmers.fitnesstracker;

import java.text.SimpleDateFormat;
import java.util.Calendar;




import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.DatePicker;

public class WorkoutFragment extends Fragment {
	public View rootView;
	private AutoCompleteTextView mSelectWorkout;
	private EditText date;
	
	public WorkoutFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        rootView = inflater.inflate(R.layout.fragment_workout, container, false);
        mSelectWorkout = (AutoCompleteTextView) rootView
				.findViewById(R.id.workoutw);
        
        Calendar c = Calendar.getInstance();

		date = (EditText) rootView.findViewById(R.id.workoutwwww);
		date.setKeyListener(null);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		date.setText(sdf.format(c.getTime()));

		date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String dateText = ((TextView) v).getText().toString();
				int year, month, day;
				String temp[] = dateText.split("-");
				year = Integer.valueOf(temp[0]);
				month = Integer.valueOf(temp[1]);
				day = Integer.valueOf(temp[2]);
				DatePickerDialog dtpkrdlg = new DatePickerDialog(rootView
						.getContext(), 0, new DateSetListener(), year,
						month - 1, day);
				dtpkrdlg.show();
				
			}
			
		});
         
        return rootView;
    }
	class DateSetListener implements OnDateSetListener {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			date.setText("" + year + "-" +

			Formatter.padStringWithZero(monthOfYear + 1) + "-"
					+ Formatter.padStringWithZero(dayOfMonth));

		}
	}}
