package se.chalmers.fitnesstracker.database.entitymanager.impl;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class Database extends SQLiteAssetHelper {
	public Database(Context context){
		super(context, "ourfitness.db",null,10);
	}
}
