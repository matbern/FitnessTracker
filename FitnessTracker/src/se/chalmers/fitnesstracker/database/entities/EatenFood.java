package se.chalmers.fitnesstracker.database.entities;

import java.util.Date;

import se.chalmers.fitnesstracker.database.annotations.GetColumn;
import se.chalmers.fitnesstracker.database.annotations.SetColumn;
import se.chalmers.fitnesstracker.database.annotations.Table;
import se.chalmers.fitnesstracker.database.entitymanager.Entity;
import se.chalmers.fitnesstracker.database.enums.Type;

public class EatenFood extends Entity {
	private String mName = null;
	private String mProteins = null;
	private String mFat = null;
	private String mCarbs = null;
	private String mCalories = "";
	private Date mDate;
	@Table(name = "EatenFood")
	public EatenFood() {
	}
	@GetColumn(name = "name", type = Type.TEXT, key = false)
	public String getName() {
		return mName;
	}

	@SetColumn(name = "name", type = Type.TEXT)
	public void setName(String name) {
		this.mName = name;
	}

	@GetColumn(name = "energi", type = Type.TEXT, key = false)
	public String getCalories() {
		return mCalories;
	}

	@SetColumn(name = "energi", type = Type.TEXT)
	public void setCalories(String calories) {
		this.mCalories = calories;
	}

	@GetColumn(name = "id", type = Type.INT, key = true)
	@Override
	public int getID() {
		return mID;
	}

	@SetColumn(name = "id", type = Type.INT)
	@Override
	public void setID(int id) {
		mID = id;
	}
	
	@GetColumn(name = "protein", type = Type.TEXT, key = false)
	public String getProteins() {
		return mProteins;
	}

	@SetColumn(name = "protein", type = Type.TEXT)
	public void setProteins(String value) {
		this.mProteins = value;
	}
	
	@GetColumn(name = "fett", type = Type.TEXT, key = false)
	public String getFat() {
		return mFat;
	}

	@SetColumn(name = "fett", type = Type.TEXT)
	public void setFat(String value) {
		this.mFat = value;
	}
	
	@GetColumn(name = "kolhydrater", type = Type.TEXT, key = false)
	public String getCarbs() {
		return mCarbs;
	}

	@SetColumn(name = "kolhydrater", type = Type.TEXT)
	public void setCarbs(String value) {
		this.mCarbs = value;
	}
	
	@GetColumn(name = "date", type = Type.DATE, key = false)
	public Date getDate() {
		return mDate;
	}

	@SetColumn(name = "date", type = Type.DATE)
	public void setDate(Date value) {
		this.mDate = value;
	}

	public String toString() {
		return "Namn:" +mName;
	}
}
