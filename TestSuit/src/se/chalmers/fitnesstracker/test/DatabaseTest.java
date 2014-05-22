package se.chalmers.fitnesstracker.test;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.fitnesstracker.database.entities.EatenFood;
import se.chalmers.fitnesstracker.database.entities.Food;
import se.chalmers.fitnesstracker.database.entities.Workout;
import se.chalmers.fitnesstracker.database.entitymanager.impl.EntityManagerImpl;
import android.content.Context;
import android.test.AndroidTestCase;
import android.test.IsolatedContext;

public class DatabaseTest extends AndroidTestCase {
	private EntityManagerImpl db = null;
	private String NAME = "Valfria bullar";
	private String CALORIES = "123";
	private String CARBS = "54";
	private String FAT = "456";
	private String PROTEINS = "758";

	@Override
	protected void setUp() throws Exception {
		final Context context = new IsolatedContext(null, getContext()) {
			@Override
			public Object getSystemService(final String pName) {
				return getContext().getSystemService(pName);
			}
		};

		db = EntityManagerImpl.getInstance();
		db.initTestDB(context);
		super.setUp();
	}

	public void testAGetInstance() {
		assertNotNull(db);
		db.dropTables(false);
	}

	public void testBCreateTables() {
		db.createTables(true);
		db.getAll(Food.class);
		db.getAll(Workout.class);
		db.getAll(EatenFood.class);
	}

	public void testCPersist() {

		List<Food> list = db.getAll(Food.class);
		assertTrue(list.size() == 0);

		Food f = new Food();
		f.setName(NAME);
		f.setCalories(CALORIES);
		f.setCarbs(CARBS);
		f.setFat(FAT);
		f.setProteins(PROTEINS);
		db.persist(f);

		list = db.getAll(Food.class);
		assertTrue(list.size() == 1);
		Food ret = list.get(0);
		assertEquals(f, ret);

	}

	public void testDDelete() {
		List<Food> list = db.getAll(Food.class);
		assertTrue(list.size() == 1);
		Food ret = list.get(0);
		db.delete(ret);

		list = db.getAll(Food.class);
		assertTrue(list.size() == 0);
	}

	public void testEUpdate() {
		String newName = "Kattmat";
		Food f = new Food();
		f.setName(NAME);
		f.setCalories(CALORIES);
		f.setCarbs(CARBS);
		f.setFat(FAT);
		f.setProteins(PROTEINS);
		db.persist(f);
		List<Food> list = db.getAll(Food.class);
		assertTrue(list.size() == 1);
		Food ret = list.get(0);
		ret.setName(newName);
		db.update(ret);
		list = db.getAll(Food.class);
		assertTrue(list.size() == 1);
		Food g = list.get(0);
		assertEquals(ret, g);
	}

	public void testFGetWhere() {
		for (int i = 0; i < 20; i++) {
			Workout w = new Workout();
			w.setName("workout" + i);
			w.setCalories(i % 5);
			db.persist(w);
		}
		List<Workout> list = db.getAll(Workout.class);
		assertTrue(list.size() == 20);
		list = db.getWhere(Workout.class, "energy = '3'");
		assertTrue(list.size()== 4);
		for (Workout w: list){
			assertTrue(w.getCalories()==3);
		}
		list = db.getWhere(Workout.class, "energy = '3'","2");
		assertTrue(list.size()== 2);
		for (Workout w: list){
			assertTrue(w.getCalories()==3);
		}
	}

	@Override
	protected void tearDown() throws Exception {
		db = null;
		super.tearDown();
	}
}
