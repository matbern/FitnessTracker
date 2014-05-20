package se.chalmers.fitnesstracker.database.entitymanager;

import se.chalmers.fitnesstracker.database.entitymanager.impl.EntityManagerImpl;


public class PersistenceFactory { // skapar ett objekt av entitymanagerimpl
	public static EntityManager getEntityManager(){
		return EntityManagerImpl.getInstance();
	}
}
