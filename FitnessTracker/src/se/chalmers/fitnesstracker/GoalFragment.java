package se.chalmers.fitnesstracker;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class GoalFragment extends Fragment {
	private View mRootView;
	private EditText selectedWeight;
	private Float WeightLoss1;

	public GoalFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mRootView = inflater.inflate(R.layout.fragment_goal, container,
				false);
		Button b = (Button) mRootView.findViewById(R.id.addgoal);
		selectedWeight = ((EditText) mRootView.findViewById(R.id.goalweight));
		selectedWeight.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				TextView t = (TextView) mRootView.findViewById(R.id.weightloss);
				try {
					Float f = Float.parseFloat(s.toString());

					SharedPreferences prefs = mRootView.getContext()
							.getSharedPreferences(MainActivity.INIT_PREFS, 0);
					WeightLoss1 = (prefs.getFloat(MainActivity.WEIGHT, 0) - f);
					String helo;
					if (WeightLoss1 > 0) {
						helo = "Du behöver gå ner ";
					} else {
						helo = "Du behöver gå upp ";
						WeightLoss1 *= -1;
					}

					String str = WeightLoss1.toString();
					if (str.length() > 4)
						str = str.substring(0, 4);
					t.setText(helo + " " + str + " kg");
				} catch (Exception e) {
					t.setText("");
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String t = selectedWeight.getText().toString();
				if (t == null || t.isEmpty()) {
					Toast.makeText(mRootView.getContext(),
							"You need to insert a goal weight",
							Toast.LENGTH_SHORT).show();
					return;
				}
				SharedPreferences prefs = mRootView.getContext()
						.getSharedPreferences(MainActivity.INIT_PREFS, 0);
				Editor e = prefs.edit();
				e.putString(MainActivity.GOAL_WEIGHT, t);
				e.apply();

				RadioGroup rg = (RadioGroup) mRootView
						.findViewById(R.id.radioGroup1);
				RadioButton rb = (RadioButton) mRootView.findViewById(rg
						.getCheckedRadioButtonId());
				//Log.e("Debug", rb.getText().toString());

				Editor e2 = prefs.edit();
				String vel = rb.getText().toString();
				e2.putString(MainActivity.GOAL_VELOCITY, vel);
				e2.apply();
				TextView te = (TextView) mRootView.findViewById(R.id.addedGoal);

				if (vel.equalsIgnoreCase("snabb")) {
					te.setText("Antal veckor: " + WeightLoss1);
				}

				if (vel.equalsIgnoreCase("medel")) {
					te.setText("Antal veckor: " + WeightLoss1 * 2);
				}

				if (vel.equalsIgnoreCase("långsam")) {
					te.setText("Antal veckor: " + WeightLoss1 * 4);
				}

			}
		});
		return mRootView;
	}
}
