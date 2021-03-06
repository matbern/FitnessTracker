package se.chalmers.fitnesstracker;

import se.chalmers.fitnesstracker.database.entities.Food;
import se.chalmers.fitnesstracker.database.entitymanager.EntityManager;
import se.chalmers.fitnesstracker.database.entitymanager.PersistenceFactory;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewFoodFragment extends Fragment{
	public View rootView;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
			rootView = inflater.inflate(R.layout.fragment_addnewfoodview,
                container, false);
		
		Button b = (Button) rootView.findViewById(R.id.fAdd);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText FoodName = (EditText) rootView.findViewById(R.id.fnamn);
				String fNameStr = FoodName.getText().toString().trim();
				if (fNameStr.isEmpty()) {
					Toast.makeText(rootView.getContext(),
							"M�ste ange namn",
							Toast.LENGTH_SHORT).show();
					return;
				}
				EditText et = (EditText) rootView.findViewById(R.id.fkcal);
				String kalori = et.getText().toString().trim();
				if (kalori.isEmpty()) {
					Toast.makeText(rootView.getContext(),
							"M�ste ange kalorim�ngd", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				EditText et2 = (EditText) rootView.findViewById(R.id.fcarb);
				String carb = et2.getText().toString().trim();
				if (carb.isEmpty()) {
					Toast.makeText(rootView.getContext(),
							"M�ste ange kolhydratm�ngd", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				EditText et3 = (EditText) rootView.findViewById(R.id.fprot);
				String prote = et3.getText().toString().trim();
				if (prote.isEmpty()) {
					Toast.makeText(rootView.getContext(),
							"M�ste ange proteinm�ngd", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				EditText et4 = (EditText) rootView.findViewById(R.id.ffat);
				String fett = et4.getText().toString().trim();
				if (fett.isEmpty()) {
					Toast.makeText(rootView.getContext(),
							"M�ste ange fettm�ngd", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				EntityManager em = PersistenceFactory.getEntityManager();
				Food f = new Food();
				f.setName(fNameStr);
				f.setCalories(kalori);
				f.setCarbs(carb);
				f.setProteins(prote);
				f.setFat(fett);
				em.persist(f);
				et.setText("");
				et2.setText("");
				et3.setText("");
				et4.setText("");
				Log.i("" + AddNewFoodFragment.class, "Added food:" + f.toString());
				Toast.makeText(rootView.getContext(),
						"Tillagd i databasen", Toast.LENGTH_SHORT)
						.show();
			}
		});
		
		return  rootView;
	}

}
