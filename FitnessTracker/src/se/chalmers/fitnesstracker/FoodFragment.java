package se.chalmers.fitnesstracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import se.chalmers.fitnesstracker.database.entities.EatenFood;
import se.chalmers.fitnesstracker.database.entities.Food;
import se.chalmers.fitnesstracker.database.entitymanager.EntityManager;
import se.chalmers.fitnesstracker.database.entitymanager.PersistenceFactory;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

public class FoodFragment extends Fragment {
	public View rootView;
	private EditText date;
	private Food selectedFood = null;
	private AutoCompleteTextView mSelectFood;

	public FoodFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_food, container, false);

		mSelectFood = (AutoCompleteTextView) rootView
				.findViewById(R.id.foodfood);

		Calendar c = Calendar.getInstance();

		date = (EditText) rootView.findViewById(R.id.fooddate);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		date.setText(sdf.format(c.getTime()));
		date.setKeyListener(null);
		date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String dateText = ((TextView) v).getText().toString();
				int year, month, day;
				String temp[] = dateText.split("-");
				year = Integer.valueOf(temp[0]);
				month = Integer.valueOf(temp[1]);
				day = Integer.valueOf(temp[2]);
				DatePickerDialog dtpkrdlg = new DatePickerDialog(rootView
						.getContext(), 0, new DateSetListener(), year,
						month - 1, day);
				dtpkrdlg.show();

			}
		});
		Button b = (Button) rootView.findViewById(R.id.add);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (selectedFood == null) {
					Toast.makeText(rootView.getContext(),
							"You need to select a food item",
							Toast.LENGTH_SHORT).show();
					return;
				}
				EditText et = (EditText) rootView.findViewById(R.id.amount);
				String amountStr = et.getText().toString();
				if (amountStr.isEmpty()) {
					Toast.makeText(rootView.getContext(),
							"You need to select amount", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				Double amount = Formatter.parseDouble(amountStr) / 100;

				Double kcal = Formatter.parseDouble(selectedFood.getCalories())
						* amount;

				Double carbs = Formatter.parseDouble(selectedFood.getCarbs())
						* amount;
				Double fat = Formatter.parseDouble(selectedFood.getFat())
						* amount;
				Double prot = Formatter.parseDouble(selectedFood.getProteins())
						* amount;

				((TextView) rootView.findViewById(R.id.foodname))
						.setText("Namn: " + selectedFood.getName());
				((TextView) rootView.findViewById(R.id.foodCalories))
						.setText("Kcal: " + Formatter.doubleToString(kcal));
				((TextView) rootView.findViewById(R.id.foodCarbs))
						.setText("Kolhydrater: "
								+ Formatter.doubleToString(carbs));
				((TextView) rootView.findViewById(R.id.foodFat))
						.setText("Fett: " + Formatter.doubleToString(fat));
				((TextView) rootView.findViewById(R.id.foodProtein))
						.setText("Protein: " + Formatter.doubleToString(prot));

				EntityManager em = PersistenceFactory.getEntityManager();
				EatenFood ef = new EatenFood();
				ef.setName(selectedFood.getName());
				ef.setCalories(Formatter.doubleToString(kcal));
				ef.setCarbs(Formatter.doubleToString(carbs));
				ef.setFat(Formatter.doubleToString(fat));
				ef.setProteins(Formatter.doubleToString(prot));
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date dateStr = null;
				try {
					dateStr = formatter.parse(date.getText().toString());
				} catch (ParseException e) {
				}
				ef.setDate(dateStr);
				em.persist(ef);
				Log.i("" + FoodFragment.class, "Added food:" + ef.toString());
			}
		});

		mSelectFood.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				selectedFood = null;
				EntityManager em = PersistenceFactory.getEntityManager();
				ArrayList<Food> list = new ArrayList<Food>();

				for (Food f : em.getWhere(Food.class,
						"name LIKE '%" + s.toString() + "%'", "4")) {
					list.add(f);
				}
				AutoCompleteTextView txt = (AutoCompleteTextView) rootView
						.findViewById(R.id.foodfood);
				ArrayAdapter<Food> adp = new ArrayAdapter<Food>(rootView
						.getContext(),
						android.R.layout.simple_dropdown_item_1line, list);
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
		mSelectFood.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				rootView.findViewById(R.id.amount).requestFocus();
				selectedFood = (Food) parent.getAdapter().getItem(0);
			}
		});
		
		Button b1 = (Button) rootView.findViewById(R.id.create);
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 Fragment frag = new AddNewFoodFragment();
			        FragmentManager fragmentManager = getFragmentManager();
					fragmentManager.beginTransaction()
							.replace(R.id.frame_container, frag).commit();
			}
		});
		

		return rootView;
	}

	class DateSetListener implements OnDateSetListener {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			date.setText("" + year + "-" +

			Formatter.padStringWithZero(monthOfYear + 1) + "-"
					+ Formatter.padStringWithZero(dayOfMonth));

		}

	}
}
