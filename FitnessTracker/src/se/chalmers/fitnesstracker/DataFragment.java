package se.chalmers.fitnesstracker;
 
import java.util.ArrayList;
import java.util.HashMap;
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
            	if (!text.equals("")) {
            		text = text.substring(0, 1).toUpperCase()+text.substring(1);
            		if (fragList.get(text) == null) {
            			Bundle args = new Bundle();
            			args.putString("name", text);
            			Fragment frag = new DataAddFragment();
            			frag.setArguments(args);
            	        FragmentManager fragmentManager = getFragmentManager();
            			fragmentManager.beginTransaction().addToBackStack(TAG)
            					.replace(R.id.frame_container, frag).commit();
                        edit.setText("");
            		}
            		else
            			edit.setText("Already registered");
            	}
            	else
            		Log.d(TAG, "Input string null");
            }
        };
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