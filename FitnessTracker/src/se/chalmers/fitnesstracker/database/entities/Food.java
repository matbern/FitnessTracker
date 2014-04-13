package se.chalmers.fitnesstracker.database.entities;

import se.chalmers.fitnesstracker.database.annotations.GetColumn;
import se.chalmers.fitnesstracker.database.annotations.SetColumn;
import se.chalmers.fitnesstracker.database.annotations.Table;
import se.chalmers.fitnesstracker.database.entitymanager.Entity;
import se.chalmers.fitnesstracker.database.enums.Type;



public class Food extends Entity {
	private String mName = null;
	private int mAmount = 0;
	// annotationerna gör att entitymanagern fattar vilka metoder som hör till vilken kol/rad/tabell
	@Table(name ="Food") // säger att den här metodens tabell i databasen kmr heta food
	public Food(){
	}

	@GetColumn(name="Name", type=Type.TEXT, key=false) // kolumnen namn av typ text är inte nyckel
	public String getName() {
		return mName;
	}
	@SetColumn(name="Name", type=Type.TEXT) 
	public void setName(String name) {
		this.mName = name;
	}
	@GetColumn(name="Amount", type=Type.INT, key=false)
	public int getAmount() {
		return mAmount;
	}
	@SetColumn(name="Amount", type=Type.INT)
	public void setAmount(int amount) {
		this.mAmount = amount;
	}

	@GetColumn(name="id", type=Type.INT, key=true)
	@Override
	public int getID() {
		return mID;
	}

	@SetColumn(name="id", type=Type.INT)
	@Override
	public void setID(int id) {
		mID =id;
	}
	public String toString(){
		return mName;
	}
}
