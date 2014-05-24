package se.chalmers.fitnesstracker.database.entities;

import java.util.Date;

import se.chalmers.fitnesstracker.database.annotations.GetColumn;
import se.chalmers.fitnesstracker.database.annotations.SetColumn;
import se.chalmers.fitnesstracker.database.annotations.Table;
import se.chalmers.fitnesstracker.database.entitymanager.Entity;
import se.chalmers.fitnesstracker.database.enums.Type;

public class CompletedWorkout extends Entity{
	private String cwName = null;
	private String cwCalories = "";
	private Date mDate;
	
	@Table(name = "CompletedWorkout")
	public CompletedWorkout() {
	}
	
	@GetColumn(name = "name", type = Type.TEXT, key = false)
	public String getName() {
		return cwName;
	}

	@SetColumn(name = "name", type = Type.TEXT)
	public void setName(String name) {
		this.cwName = name;
	}
	
	//antal kalorier per trettio minuter ska finns lagrat
	@GetColumn(name = "energy", type = Type.TEXT, key = false)
	public String getCalories() {
		return cwCalories;
	}

	@SetColumn(name = "energy", type = Type.TEXT)
	public void setCalories(String calories) {
		this.cwCalories = calories;
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
	
	@GetColumn(name = "date", type = Type.DATE, key = false)
	public Date getDate() {
		return mDate;
	}

	@SetColumn(name = "date", type = Type.DATE)
	public void setDate(Date value) {
		this.mDate = value;
	}

	public String toString() {
		return "Namn:" +cwName;
	}

}
