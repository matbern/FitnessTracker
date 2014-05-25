package se.chalmers.fitnesstracker;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import se.chalmers.fitnesstracker.database.entities.Workout;
import se.chalmers.fitnesstracker.database.entitymanager.EntityManager;
import se.chalmers.fitnesstracker.database.entitymanager.PersistenceFactory;
import android.R.id;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
 
public class DataFragment extends ListFragment {
	private static final String TAG = DataFragment.class.getSimpleName();
    static ArrayList<String> nameList = new ArrayList<String>();
    static Map<String, ArrayList<Integer>> fragList = null;
    static ArrayAdapter<String> adapter;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        final View rootView = inflater.inflate(R.layout.fragment_data,
                        container, false);
        Button btn = (Button) rootView.findViewById(R.id.btnAdd);
        ListView lv = (ListView) rootView.findViewById(id.list);
        adapter = new ArrayAdapter<String>(this.getActivity(),
                        android.R.layout.simple_list_item_1, nameList);
        EntityManager em = PersistenceFactory.getEntityManager();
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
            	EditText edit = (EditText) rootView.findViewById(R.id.txtItem);
            	String text = edit.getText().toString();
            	if (text != null) {
            		if (fragList.get(text) == null) {
            			Bundle args = new Bundle();
            			args.putString("name", text);
            			Fragment frag = new DataAddFragment();
            			frag.setArguments(args);
            	        FragmentManager fragmentManager = getFragmentManager();
            			fragmentManager.beginTransaction().addToBackStack(TAG)
            					.replace(R.id.frame_container, frag).commit();
            			//nameList.add(text);
                        edit.setText("");
                        //adapter.notifyDataSetChanged();
            		}
            		else
            			edit.setText("Already registered");
            	}
            	else
            		Log.e(TAG, "Input string null");
            }
        };
        lv.setLongClickable(true);
        lv.setOnItemLongClickListener(new OnItemLongClickListener() {
        	public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                    int pos, long id) {
        		String name = nameList.get(pos);
        		nameList.remove(pos);
        		fragList.remove(name);
        		adapter.notifyDataSetChanged();
        		return true;
        	}
        });

        btn.setOnClickListener(listener);
        setListAdapter(adapter);
        
        if (fragList == null) {
        	fragList = new HashMap<String, ArrayList<Integer>>();
	        for (Workout w : em.getAll(Workout.class)) {
	        	String name = w.getName().split(",")[0];
	        	int cals = w.getCalories();
	        	ArrayList<Integer> vals = fragList.get(name);
	        	if (vals == null) {
	        		ArrayList<Integer> val = new ArrayList<Integer>();
	        		val.add(cals);
	        		fragList.put(name, val);
	        		nameList.add(name);
	        	}
	        	else {
	        		vals.add(cals);
	        		fragList.put(name, vals);
	        	}
	        	
			}
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
 
        //Toast.makeText(getActivity(), "clicked: " + nameList.get(position),
        //                Toast.LENGTH_SHORT).show();
        Bundle args = new Bundle();
        String name = nameList.get(position);
        ArrayList<Integer> vals = fragList.get(name);
        args.putIntegerArrayList("values", vals);
        args.putString("name", name);
        Fragment frag = new DataViewFragment();
        frag.setArguments(args);
        FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.frame_container, frag).commit();
    }
}