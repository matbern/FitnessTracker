package se.chalmers.fitnesstracker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ActivityFirstLaunch extends Activity {
	private static String FIRST_TIME = "FirstTime";
	private static String INIT_PREFS = "InitPrefs";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_launch);
		
	}
	
	public void onButtonApply(View view){
		
		SharedPreferences prefs = getSharedPreferences(INIT_PREFS, 0);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean(FIRST_TIME, false);
		editor.apply();
		
		Intent intent = new Intent(this, MainActivity.class);
	    startActivity(intent);
	    finish();
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	
}
