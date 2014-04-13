package se.chalmers.fitnesstracker;

import java.util.ArrayList;

import se.chalmers.fitnesstracker.database.entities.Food;
import se.chalmers.fitnesstracker.database.entitymanager.EntityManager;
import se.chalmers.fitnesstracker.database.entitymanager.PersistenceFactory;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

public class FoodFragment extends Fragment {

	public FoodFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_pages, container,
				false);

		EntityManager em = PersistenceFactory.getEntityManager(null);
		ArrayList<Food> list = new ArrayList<Food>();

		for (Food f : em.getAll(Food.class)) {
			list.add(f);
		}

		AutoCompleteTextView txt = (AutoCompleteTextView) rootView
				.findViewById(R.id.autoCompleteTextView1);
		ArrayAdapter<Food> adp = new ArrayAdapter<Food>(
				rootView.getContext(),
				android.R.layout.simple_dropdown_item_1line, list);
		txt.setAdapter(adp);
		txt.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Food f = (Food) parent.getAdapter().getItem(0);
				Toast.makeText(parent.getContext(), "Namn:"+f.getName()+", Kcal:" + f.getAmount(), Toast.LENGTH_LONG).show();
				
				
			}
		});

		return rootView;
	}
}
