package se.chalmers.fitnesstracker;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



/*
 * Kan just nu enbart lägga till en enda intensitet, enkelt fixat
 * vid behov för annat. Lägger ej till i databas för stunden.
 */
public class DataAddFragment extends Fragment{
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_dataadd,
                container, false);
		final Bundle args = this.getArguments();
		final String name = args.getString("name");
		TextView nameText  = (TextView) rootView.findViewById(R.id.dataAddName);
		Button btn = (Button) rootView.findViewById(R.id.btnCnfrm);
		OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
            	EditText edit = (EditText) rootView.findViewById(R.id.addEdit1);
            	int val = Integer.parseInt(edit.getText().toString());
            	ArrayList<Integer> vals = new ArrayList<Integer>();
            	vals.add(val);
            	DataFragment.nameList.add(name);
            	Collections.sort(DataFragment.nameList);
            	DataFragment.fragList.put(name, vals);
            	DataFragment.adapter.notifyDataSetChanged();
            	//Lägg till i databas här
            	
            	getFragmentManager().popBackStackImmediate();
            }
		};
		
		nameText.setText(name);
		btn.setOnClickListener(listener);
		
		return rootView;
	}

}
