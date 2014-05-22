package se.chalmers.fitnesstracker.database.entities;

import se.chalmers.fitnesstracker.database.annotations.GetColumn;
import se.chalmers.fitnesstracker.database.annotations.SetColumn;
import se.chalmers.fitnesstracker.database.annotations.Table;
import se.chalmers.fitnesstracker.database.entitymanager.Entity;
import se.chalmers.fitnesstracker.database.enums.Type;

public class Food extends Entity {
	private String mName = null;
	private String mProteins = null;
	private String mFat = null;
	private String mCarbs = null;
	private String mCalories = "";
 	// annotationerna gör att entitymanagern fattar vilka metoder som hör till
	// vilken kol/rad/tabell
	@Table(name = "Food")
	// säger att den här metodens tabell i databasen kmr heta food
	public Food() {
	}

	@GetColumn(name = "name", type = Type.TEXT, key = false)
	// kolumnen namn av typ text är inte nyckel
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

	public String toString() {
		return mName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((mCalories == null) ? 0 : mCalories.hashCode());
		result = prime * result + ((mCarbs == null) ? 0 : mCarbs.hashCode());
		result = prime * result + ((mFat == null) ? 0 : mFat.hashCode());
		result = prime * result + ((mName == null) ? 0 : mName.hashCode());
		result = prime * result
				+ ((mProteins == null) ? 0 : mProteins.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Food))
			return false;
		Food other = (Food) obj;
		if (mCalories == null) {
			if (other.mCalories != null)
				return false;
		} else if (!mCalories.equals(other.mCalories))
			return false;
		if (mCarbs == null) {
			if (other.mCarbs != null)
				return false;
		} else if (!mCarbs.equals(other.mCarbs))
			return false;
		if (mFat == null) {
			if (other.mFat != null)
				return false;
		} else if (!mFat.equals(other.mFat))
			return false;
		if (mName == null) {
			if (other.mName != null)
				return false;
		} else if (!mName.equals(other.mName))
			return false;
		if (mProteins == null) {
			if (other.mProteins != null)
				return false;
		} else if (!mProteins.equals(other.mProteins))
			return false;
		return true;
	}
}
