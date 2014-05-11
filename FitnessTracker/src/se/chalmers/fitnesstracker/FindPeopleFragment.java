package se.chalmers.fitnesstracker;

import java.util.ArrayList;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
 
public class FindPeopleFragment extends ListFragment {
    ArrayList<String> list = new ArrayList<String>();
    ArrayAdapter<String> adapter;
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        final View rootView = inflater.inflate(R.layout.fragment_find_people, 
        		container, false);
        Button btn = (Button) rootView.findViewById(R.id.btnAdd);
        adapter = new ArrayAdapter<String>(this.getActivity(), 
        		android.R.layout.simple_list_item_1, list);
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit = (EditText) rootView.findViewById(R.id.txtItem);
                list.add(edit.getText().toString());
                edit.setText("");
                adapter.notifyDataSetChanged();
            }
        };
 
        btn.setOnClickListener(listener);
        setListAdapter(adapter);
        
        
        
        return rootView;
    }
    
    /*
     * Skall öppna ny vy beroende på klickad knapp. Ladda namn från databas.
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Toast.makeText(getActivity(), "clicked: " + list.get(position), 
        		Toast.LENGTH_SHORT).show();
    }
}