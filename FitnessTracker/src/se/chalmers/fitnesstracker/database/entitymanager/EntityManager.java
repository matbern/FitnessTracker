package se.chalmers.fitnesstracker.database.entitymanager;

import java.util.List;

import android.content.Context;
// EntityManager Interface
public interface EntityManager {
	public void createTables();
	public void init(Context context);
	public void dropTables(boolean save);

	public <T> void persist(Entity entity);
	public <T> void update(Entity entity);
	public <T> void delete(Entity entity);


	public <T> List<T> getWhere(Class<T> clazz, String where,String limit);
	public <T> List<T> getWhere(Class<T> clazz, String where);

	public <T> List<T> getAll(Class<T> clazz);
	
	
}
