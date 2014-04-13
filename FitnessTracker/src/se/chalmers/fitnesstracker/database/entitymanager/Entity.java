package se.chalmers.fitnesstracker.database.entitymanager;

public abstract class Entity {
	protected int mID = 0;
	public abstract int getID();
	public abstract void setID(int id);
}
