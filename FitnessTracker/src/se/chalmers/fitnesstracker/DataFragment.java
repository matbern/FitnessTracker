package se.chalmers.fitnesstracker;
 
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.chalmers.fitnesstracker.database.entities.Workout;
import se.chalmers.fitnesstracker.database.entitymanager.EntityManager;
import se.chalmers.fitnesstracker.database.entitymanager.PersistenceFactory;
import android.R.id;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
 
public class DataFragment extends ListFragment {
	public static final String TAG = DataFragment.class.getSimpleName();
    private static ArrayList<String> nameList = new ArrayList<String>();
    private static Map<String, ArrayList<Integer>> fragList = null;
    private static ArrayAdapter<String> adapter;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_data,
                        container, false);
        ListView lv = (ListView) rootView.findViewById(id.list);
        adapter = new ArrayAdapter<String>(this.getActivity(),
                        android.R.layout.simple_list_item_1, nameList);
        EntityManager em = PersistenceFactory.getEntityManager();
        lv.setLongClickable(true);
        lv.setOnItemLongClickListener(new OnItemLongClickListener() {
        	public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                    int pos, long id) {
        		final int p = pos;
        		final String name = nameList.get(pos);
        		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        		    @Override
        		    public void onClick(DialogInterface dialog, int which) {
        		        switch (which){
        		        case DialogInterface.BUTTON_POSITIVE:
        		        	EntityManager em = PersistenceFactory.getEntityManager();
        		        	List<Workout> entries = em.getWhere(Workout.class, "name LIKE '%" + name + "%'");
        		        	for(Workout w : entries) {
        		        		em.delete(w);
        		        		Log.d(TAG, "Removed entry: "+w.getName());
        		        	}
        	        		nameList.remove(p);
        	        		fragList.remove(name);
        	        		adapter.notifyDataSetChanged();
        		            break;

        		        case DialogInterface.BUTTON_NEGATIVE:
        		            break;
        		        }
        		    }
        		};
        		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        		builder.setMessage("Delete "+name+"?").setPositiveButton("Yes", dialogClickListener)
        		    .setNegativeButton("No", dialogClickListener).show();
        		return true;
        	}
        });

        setListAdapter(adapter);
        
        if ((fragList == null) || (MainActivity.workoutAdded)) {
        	fragList = new HashMap<String, ArrayList<Integer>>();
        	nameList.clear();
	        for (Workout w : em.getAll(Workout.class)) {
	        	String name = w.getName().split(",")[0];
	        	int cals = w.getCalories();
	        	ArrayList<Integer> vals = fragList.get(name);
	        	if (vals == null) {
	        		ArrayList<Integer> val = new ArrayList<Integer>();
	        		val.add(cals);
	        		fragList.put(name, val);
	        		nameList.add(name);
	        		Collections.sort(nameList);
	        	}
	        	else {
	        		vals.add(cals);
	        		Collections.sort(vals);
	        		fragList.put(name, vals);
	        	}
	        	
			}
	        MainActivity.workoutAdded = false;
        }
        
        adapter.notifyDataSetChanged();
        return rootView;
    }
   
    /*
     * Skall öppna ny vy beroende på klickad knapp. Ladda namn från databas.
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        
        Bundle args = new Bundle();
        String name = nameList.get(position);
        ArrayList<Integer> vals = fragList.get(name);
        args.putIntegerArrayList("values", vals);
        args.putString("name", name);
        Fragment frag = new DataViewFragment();
        frag.setArguments(args);
        FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().addToBackStack(TAG)
				.replace(R.id.frame_container, frag).commit();
    }
}