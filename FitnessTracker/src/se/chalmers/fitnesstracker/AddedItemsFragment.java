package se.chalmers.fitnesstracker;

import se.chalmers.fitnesstracker.database.entities.EatenFood;
import se.chalmers.fitnesstracker.database.entitymanager.EntityManager;
import se.chalmers.fitnesstracker.database.entitymanager.PersistenceFactory;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AddedItemsFragment extends Fragment {
	
	public AddedItemsFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_photos, container, false);
        
    	EntityManager em = PersistenceFactory.getEntityManager();
        TextView tv = (TextView) rootView.findViewById(R.id.allFood);
        
        StringBuffer sb = new StringBuffer();
		for(EatenFood ef : em.getAll(EatenFood.class)){
			sb.append(ef.getDate()+"\n");
			sb.append(ef.getName()+"\n");
			sb.append(ef.getCalories()+"\n");
			sb.append(ef.getCarbs()+"\n");
			sb.append(ef.getFat()+"\n");
			sb.append(ef.getProteins()+"\n");
			sb.append("\n\n");
		}
		tv.setText(sb.toString());
        
        return rootView;
    }
}
