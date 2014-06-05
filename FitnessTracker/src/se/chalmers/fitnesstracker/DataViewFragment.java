package se.chalmers.fitnesstracker;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DataViewFragment extends Fragment {
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_dataview,
                container, false);
		
		Bundle args = this.getArguments();
		String name = args.getString("name");
		ArrayList<Integer> vals = args.getIntegerArrayList("values");
		int length = vals.size();
		TextView nameText  = (TextView) rootView.findViewById(R.id.dataViewName);
		TextView data1  = (TextView) rootView.findViewById(R.id.dataText1);
		TextView data2  = (TextView) rootView.findViewById(R.id.dataText2);
		TextView data3  = (TextView) rootView.findViewById(R.id.dataText3);
		nameText.setText(name);
		if (length >= 2) {
			data1.setText("Lätt: "+vals.get(0)+ "kcal/30min");
			data2.setText("Medel: "+vals.get(1)+ "kcal/30min");
			if (length >= 3)
				data3.setText("Tuff: "+vals.get(2)+ "kcal/30min");
		}
		else
			data1.setText("Förbränning: "+vals.get(0)+ "kcal/30min");
		
		
		return rootView;
	}
}
