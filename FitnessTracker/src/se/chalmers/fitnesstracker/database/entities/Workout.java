package se.chalmers.fitnesstracker.database.entities;

import se.chalmers.fitnesstracker.database.annotations.GetColumn;
import se.chalmers.fitnesstracker.database.annotations.SetColumn;
import se.chalmers.fitnesstracker.database.annotations.Table;
import se.chalmers.fitnesstracker.database.entitymanager.Entity;
import se.chalmers.fitnesstracker.database.enums.Type;

public class Workout extends Entity{
	private String woName = null;
	private int woCalories = 0;

	@Table(name = "Workout")
	// säger att den här metodens tabell i databasen kmr heta workout
	public Workout() {
	}
	
	@GetColumn(name = "name", type = Type.TEXT, key = false)
	// kolumnen namn av typ text är inte nyckel
	public String getName() {
		return woName;
	}

	@SetColumn(name = "name", type = Type.TEXT)
	public void setName(String name) {
		this.woName = name;
	}
	//antal kalorier per trettio minuter ska finns lagrat
	@GetColumn(name = "energy", type = Type.INT, key = false)
	public int getCalories() {
		return woCalories;
	}

	@SetColumn(name = "energy", type = Type.INT)
	public void setCalories(int calories) {
		this.woCalories = calories;
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
	
	public String toString() {
		return woName;
	}
}
