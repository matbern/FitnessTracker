package se.chalmers.fitnesstracker;

import se.chalmers.fitnesstracker.database.entities.Workout;
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

public class AddNewWorkoutFragment extends Fragment{
	public View rootView;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
			rootView = inflater.inflate(R.layout.fragment_addnewworkoutview,
                container, false);
			
			Button b = (Button) rootView.findViewById(R.id.saveWo);
			b.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					EditText et1 = (EditText) rootView.findViewById(R.id.workName);
					String fNameStr = et1.getText().toString().trim();
					if (fNameStr.isEmpty()) {
						Toast.makeText(rootView.getContext(),
								"You need to insert a workout name",
								Toast.LENGTH_SHORT).show();
						return;
					}
					EditText et2 = (EditText) rootView.findViewById(R.id.workCal);
					String kalori = et2.getText().toString().trim();
					if (kalori.isEmpty()) {
						Toast.makeText(rootView.getContext(),
								"You need to insert a calorie amount", Toast.LENGTH_SHORT)
								.show();
						return;
					}
					int kal = Integer.valueOf(kalori);
					EntityManager em = PersistenceFactory.getEntityManager();
					Workout w = new Workout();
					w.setName(fNameStr);
					w.setCalories(kal);
					em.persist(w);
					MainActivity.workoutAdded = true;
					Log.i("" + AddNewWorkoutFragment.class, "Added workout:" + w.toString());
					Toast.makeText(rootView.getContext(),
							"Added to database", Toast.LENGTH_SHORT)
							.show();
					
				}
				
			});
			
			
			return  rootView;
	}
}
