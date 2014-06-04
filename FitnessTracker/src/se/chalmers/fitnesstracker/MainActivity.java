package se.chalmers.fitnesstracker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import se.chalmers.fitnesstracker.database.entitymanager.EntityManager;
import se.chalmers.fitnesstracker.database.entitymanager.PersistenceFactory;
import SlidingMenu.adapter.NavDrawerListAdapter;
import SlidingMenu.model.NavDrawerItem;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends Activity {
	public final static String INIT_PREFS = "InitPrefs";
	public final static String FIRST_TIME = "FirstTime";
	public final static String GOAL_VELOCITY = "GoalVelocity";
	public final static String GOAL_WEIGHT = "GoalWeight";
	public final static String NAME = "Name";
	public final static String AGE = "Age";
	public final static String HEIGHT = "Height";
	public final static String WEIGHT = "Weight";
	public final static String GENDER = "Gender";
	public final static String ACTIVITY_LEVEL = "ActivityLevel";
	public final static String TAG = MainActivity.class.getSimpleName();
	private SharedPreferences prefs;
	
	//Used for interaction between schedule and homefragment
	private Calendar calendar;
	public static Bundle bundle = new Bundle();
	


	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		calendar = new GregorianCalendar();
		prefs = getSharedPreferences(INIT_PREFS, 0);	
		
		Log.i("User loaded", "Name: "+prefs.getString(NAME,"null"));
		Log.i("User loaded", "Age: "+prefs.getString(AGE,"null"));
		Log.i("User loaded", "weight: "+prefs.getFloat(WEIGHT,0));
		Log.i("User loaded", "height: "+prefs.getString(HEIGHT,"null"));
		Log.i("User loaded", "gender: "+prefs.getString(GENDER,"null"));
		Log.i("User loaded", "activitylevel: "+prefs.getString(ACTIVITY_LEVEL,"null"));
		Log.i("User loaded", "goal velocity: "+prefs.getString(GOAL_VELOCITY,"null"));
		Log.i("User loaded", "goal weight: "+prefs.getString(GOAL_WEIGHT,"null"));
		
		EntityManager em = PersistenceFactory.getEntityManager();
		em.init(this);
		
		//temp
		//tömmer databasen ( i added items) varje gång appen startas (för testning).
		em.dropTables(true);
		
		// icke temp
		
		
		em.createTables(false);
		
		setContentView(R.layout.activity_main);

		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		// Schedule
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));
		// Added Items
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1)));
		// Workout, Will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
				.getResourceId(3, -1)));
		// Food
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
				.getResourceId(4, -1)));
		// Goal, We will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons
				.getResourceId(5, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons
				.getResourceId(6, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons
				.getResourceId(7, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();

			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			 
			//Used to start the ActivityFirstLaunch
			if (prefs.getBoolean(FIRST_TIME, true)){
				displayView(7);
			}	
			// on first time display view for first nav item
			else 
				displayView(0);
		}
	}
	

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			SharedPreferences.Editor editor = prefs.edit();
			editor.putBoolean(FIRST_TIME, true);
			editor.apply();
			return true;
		case R.id.schedule:
			displayView(1);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	public void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		String name = null;
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			name = HomeFragment.TAG;
			if(bundle.isEmpty()){
				bundle.putInt("year", calendar.get(Calendar.YEAR));
				bundle.putInt("day", calendar.get(Calendar.DAY_OF_MONTH));
				bundle.putInt("month", calendar.get(Calendar.MONTH));
			}
			fragment.setArguments(bundle);
				
			break;
		case 1:
			fragment = new ScheduleFragment();
			name = ScheduleFragment.TAG;
			break;
		case 2:
			fragment = new AddedItemsFragment();
			name = AddedItemsFragment.TAG;
			break;
		case 3:
			fragment = new WorkoutFragment();
			name = WorkoutFragment.TAG;
			break;
		case 4:
			fragment = new FoodFragment();
			name = FoodFragment.TAG;
			break;
		case 5:
			fragment = new GoalFragment();
			name = GoalFragment.TAG;
			break;
		case 6:
			fragment = new DataFragment();
			name = DataFragment.TAG;
			break;
		case 7:
			fragment = new FirstLaunchFragment();
			name = FirstLaunchFragment.TAG;

		default:
			break;
		}
		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			if (name.equals(FirstLaunchFragment.TAG))
				fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();
			else
				fragmentManager.beginTransaction().addToBackStack(name)
					.replace(R.id.frame_container, fragment).commit();
			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	private int getPositionFromName(String name) {
		int pos = -1;
		if (name.equals(HomeFragment.TAG))
			pos = 0;
		else if (name.equals(ScheduleFragment.TAG))
			pos = 1;
		else if (name.equals(AddedItemsFragment.TAG))
			pos = 2;
		else if (name.equals(WorkoutFragment.TAG) || name.equals(AddNewWorkoutFragment.TAG))
			pos = 3;
		else if (name.equals(FoodFragment.TAG) || name.equals(AddNewFoodFragment.TAG))
			pos = 4;
		else if (name.equals(GoalFragment.TAG) || name.equals(GoalDetailsFragment.TAG))
			pos = 5;
		else if (name.equals(DataFragment.TAG))
			pos = 6;
		
		return pos;
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
	public void onBackPressed(){
		FragmentManager fm = getFragmentManager();
		int count = fm.getBackStackEntryCount();
		if (count > 1) {
			int pos = getPositionFromName(fm.getBackStackEntryAt(count-2).getName());
			fm.popBackStack();
			mDrawerList.setItemChecked(pos, true);
			mDrawerList.setSelection(pos);
			setTitle(navMenuTitles[pos]);
			mDrawerLayout.closeDrawer(mDrawerList);
		}
	}

}
