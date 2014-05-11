package se.chalmers.fitnesstracker;

import java.util.ArrayList;

import se.chalmers.fitnesstracker.database.entities.Food;
import se.chalmers.fitnesstracker.database.entitymanager.EntityManager;
import se.chalmers.fitnesstracker.database.entitymanager.PersistenceFactory;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

public class FoodFragment extends Fragment {
	public View rootView;
	public FoodFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		 rootView = inflater.inflate(R.layout.fragment_pages, container,
				false);

		AutoCompleteTextView txt = (AutoCompleteTextView) rootView
				.findViewById(R.id.autoCompleteTextView1);
		txt.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				EntityManager em = PersistenceFactory.getEntityManager(null);
				ArrayList<Food> list = new ArrayList<Food>();

				for (Food f : em.getWhere(Food.class,"name LIKE '%"+s.toString()+"%'", "4")) {
					list.add(f);
				}
				AutoCompleteTextView txt = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteTextView1);
				ArrayAdapter<Food> adp = new ArrayAdapter<Food>(rootView.getContext(),android.R.layout.simple_dropdown_item_1line, list);
				txt.setThreshold(3);
				txt.setAdapter(adp);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		txt.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Food f = (Food) parent.getAdapter().getItem(0);
				((TextView) rootView.findViewById(R.id.foodname)).setText("Namn: "+f.getName());
				  ((TextView) rootView.findViewById(R.id.foodCalories)).setText("Kcal: "+f.getCalories());
				  ((TextView) rootView.findViewById(R.id.foodCarbs)).setText("Kolhydrater: "+f.getCarbs());
				((TextView) rootView.findViewById(R.id.foodFat)).setText("Fett: " +f.getFat());
				((TextView) rootView.findViewById(R.id.foodProtein)).setText("Protein: "+f.getProteins()); 


			}
		});

		return rootView;
	}
}
