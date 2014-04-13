package se.chalmers.fitnesstracker.database.entitymanager;

import se.chalmers.fitnesstracker.database.entitymanager.impl.EntityManagerImpl;
import android.content.Context;


public class PersistenceFactory { // skapar ett objekt av entitymanagerimpl
	public static EntityManager getEntityManager(Context context){
		return EntityManagerImpl.getInstance(context);
	}
}
