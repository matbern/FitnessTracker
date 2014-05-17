package se.chalmers.fitnesstracker.database.entitymanager.impl;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import se.chalmers.fitnesstracker.database.annotations.GetColumn;
import se.chalmers.fitnesstracker.database.annotations.SetColumn;
import se.chalmers.fitnesstracker.database.annotations.Table;
import se.chalmers.fitnesstracker.database.entities.Food;
import se.chalmers.fitnesstracker.database.entitymanager.Entity;
import se.chalmers.fitnesstracker.database.entitymanager.EntityManager;
import dalvik.system.DexFile;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class EntityManagerImpl implements EntityManager {

	private SQLiteDatabase mDB;
	private static EntityManagerImpl sInstance = new EntityManagerImpl();
	private static Context sContext = null;
	private static List<Class<?>> sEntities = null;

	// Privat konstruktor som skapar en entitymanager, skapar databasen och
	// hittar alla entiteter (lägger i listan) i paketet "entiteter"
	private EntityManagerImpl() {
	}
	public void init(Context context) {
		Log.d("EntityManagerImpl", "Creating database if necessary");
		sContext = context;
		Database db = new Database(context);
		mDB = db.getWritableDatabase();
		sEntities = getEntities();
	}

	// singleton, ger en instans av klassen
	public static EntityManagerImpl getInstance() {
		return sInstance;
	}

	// Skapar alla tabeller utifrån entitetklasserna i listan
	public void createTables() {
		Log.d("EntityManagerImpl", "Creating tables: ");
		for (Class<?> c : sEntities) {
			if (c.equals(Food.class)) {
				continue;
			}
			StringBuffer sb = new StringBuffer(); // lägger ihop strängar
			String table = c.getConstructors()[0].getAnnotation(Table.class) // hämtar
																				// namnet
																				// på
																				// entitet
					.name();
			sb.append("CREATE TABLE IF NOT EXISTS " + table + " ("); // gör
																		// sqlsträng
			Method[] m = c.getMethods(); // hämtar alla metoder i c
			for (int i = 0; i < m.length; i++) {
				GetColumn an = m[i].getAnnotation(GetColumn.class); // finns
																	// getColumn
																	// -> spara
																	// rad i
																	// databas
				if (an != null) {
					sb.append(an.name() + " ");
					switch (an.type()) { // lägger till typer i databas strängen
					case TEXT:
						sb.append(" TEXT ");
						break;
					case INT:
						sb.append(" INTEGER ");
						break;
					case DATE:
						sb.append(" INTEGER ");
						break;
					}
					if (an.key()) {
						sb.append(" PRIMARY KEY ");
					}
					sb.append(",");
				}
			}
			sb.replace(sb.length() - 1, sb.length(), ")");
			String sql = sb.toString(); // ger hela sql strängen
			Log.d("EntityManagerImpl", "Running: " + sql);
			mDB.execSQL(sql); // kör strängen i databasen
			Log.d("EntityManagerImpl", "Created: " + table);
		}
	}

	public void dropTables(boolean save) { // metod som tar bort alla tabeller
		Log.d("EntityManagerImpl", "Dropping tables");
		for (Class<?> c : sEntities) {
			if (c.equals(Food.class) && save) {
				continue;
			}
			String table = c.getConstructors()[0].getAnnotation(Table.class)
					.name();
			mDB.execSQL("DROP TABLE IF EXISTS " + table);
			Log.d("EntityManagerImpl", "Dropped table: " + table);
		}
	}

	private List<Class<?>> getEntities() { // letar reda på alla entiteter i
											// entitetpaketet
		Log.d("EntityManagerImpl", "Getting entities");
		List<Class<?>> list = new LinkedList<Class<?>>();
		try {
			DexFile df;
			df = new DexFile(sContext.getPackageCodePath());

			for (Enumeration<String> iter = df.entries(); iter
					.hasMoreElements();) {
				String s = iter.nextElement();
				if (s.startsWith("se.chalmers.fitnesstracker.database.entities")) { // Kollar
																					// igenom
																					// filerna,
																					// om
																					// dess
																					// namn
																					// börjar
																					// med
																					// bla..,
																					// lägger
																					// till
					Log.d("EntityManagerImpl", "Found: " + s);
					list.add(Class.forName(s));
				}
			}
		} catch (IOException e) { // ska sen kasta exception
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public <T> void persist(Entity entity) { // sparar ett entitetsobjekt i
												// databasen ( lägger till en
												// rad)
		try { // Kollar vilken entitetsklass objektet är

			Class<?> clazz = null;
			for (Class<?> c : sEntities) {
				if (entity.getClass().equals(c)) {
					clazz = c;
					break;
				}
			}
			@SuppressWarnings("unchecked")
			T obj = (T) clazz.cast(entity); // den ger objektet dess typ
			String table = clazz.getConstructors()[0]
					.getAnnotation(Table.class).name(); // hittar dess tabell

			ContentValues values = new ContentValues();
			for (Method f : obj.getClass().getMethods()) { // Hittar alla
															// get-metoder i
															// objektets klass
															// och lägger till
															// kol.namn + värdet
															// i contentvalues
				GetColumn an = f.getAnnotation(GetColumn.class);
				if (an != null && !an.key()) {
					String val = "";
					switch (an.type()) {
					case TEXT:
						val = (String) f.invoke(obj);
						break;
					case INT:
						val = ((Integer) f.invoke(obj)).toString();
						break;
					case DATE:
						val = Long.toString(((Date) f.invoke(obj)).getTime());
						break;
					}
					values.put(an.name(), val);
				}
			}
			mDB.insert(table, null, values); // stoppar in värdena i databasen
		} catch (Exception e) {
		}
	}

	@Override
	public <T> void delete(Entity entity) { // tar bort en rad i tabellen
											// (objekt)
		try { // kollar typ

			Class<?> clazz = null;
			for (Class<?> c : sEntities) {
				if (entity.getClass().equals(c)) {
					clazz = c;
					break;
				}
			}

			@SuppressWarnings("unchecked")
			T obj = (T) clazz.cast(entity);
			String table = clazz.getConstructors()[0]
					.getAnnotation(Table.class).name();

			ContentValues values = new ContentValues();
			String key = null;
			String[] keyvalue = null;
			for (Method f : obj.getClass().getMethods()) {
				GetColumn an = f.getAnnotation(GetColumn.class);
				if (an != null) {
					String val = "";
					switch (an.type()) {
					case TEXT:
						val = (String) f.invoke(obj);
						break;
					case INT:
						val = ((Integer) f.invoke(obj)).toString();
						break;
					case DATE:
						val = Long.toString(((Date) f.invoke(obj)).getTime());
						break;
					}
					if (an.key()) {
						key = an.name();
						keyvalue = new String[] { val };
					}
					values.put(an.name(), val);
				}
			}
			mDB.delete(table, key + " = ?", keyvalue);
		} catch (Exception e) { // ska kasta exception sen
			e.printStackTrace();
		}

	}

	public <T> void update(Entity entity) { // uppdaterar en rad i tabell (obj)
		try { // kollar typ

			Class<?> clazz = null;
			for (Class<?> c : sEntities) {
				if (entity.getClass().equals(c)) {
					clazz = c;
					break;
				}
			}

			@SuppressWarnings("unchecked")
			T obj = (T) clazz.cast(entity);
			String table = clazz.getConstructors()[0]
					.getAnnotation(Table.class).name();

			ContentValues values = new ContentValues();
			String key = null;
			String[] keyvalue = null;
			for (Method f : obj.getClass().getMethods()) {
				GetColumn an = f.getAnnotation(GetColumn.class);
				if (an != null) {
					String val = "";
					switch (an.type()) {
					case TEXT:
						val = (String) f.invoke(obj);
						break;
					case INT:
						val = ((Integer) f.invoke(obj)).toString();
						break;
					case DATE:
						val = Long.toString(((Date) f.invoke(obj)).getTime());
						break;
					}
					if (an.key()) {
						key = an.name();
						keyvalue = new String[] { val };
					}
					values.put(an.name(), val);
				}
			}

			mDB.update(table, values, key + " = ?", keyvalue); // uppdaterar i
																// databasen

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// limit bestämmer hur många i listan
	public <T> List<T> getWhere(Class<T> clazz, String where, String limit) { // anropar
																				// get,
																				// med
																				// where
																				// +
																				// limit
		return get(clazz, where, limit);
	}

	public <T> List<T> getWhere(Class<T> clazz, String where) { // anropar get,
																// med where
		return get(clazz, where, null);
	}

	public <T> List<T> getAll(Class<T> clazz) {// hämtar alla
		return get(clazz, null, null);
	}

	private <T> List<T> get(Class<T> clazz, String where, String limit) { // hämtar
																			// objekt
																			// från
																			// databasen
																			// o
																			// gör
																			// dem
																			// till
																			// en
																			// lista
		String table = clazz.getConstructors()[0].getAnnotation(Table.class)
				.name();

		List<String> columns = new LinkedList<String>();
		for (Method f : clazz.getMethods()) {
			GetColumn an = f.getAnnotation(GetColumn.class); // hittar alla
																// get-metoder =
																// alla kolumner
			if (an != null) {
				columns.add(an.name()); // sparar alla kolumnnamn i lista
			}
		}
		List<T> ret = new ArrayList<T>();
		String[] all = new String[columns.size()];
		try {

			Cursor res = mDB.query(table, columns.toArray(all), where, null,
					null, null, null, limit); // gör en query
			if (res != null) { // ifall inte null
				while (res.moveToNext()) {
					@SuppressWarnings("unchecked")
					T obj = (T) clazz.getConstructors()[0]
							.newInstance(new Object[] {}); // gör data till
															// objekt
					for (Method f : clazz.getMethods()) {
						SetColumn an = f.getAnnotation(SetColumn.class);
						if (an != null) {
							int index = res.getColumnIndex(an.name());
							switch (an.type()) {
							case TEXT:
								f.invoke(obj, res.getString(index));
								break;
							case INT:
								f.invoke(obj, res.getInt(index));
								break;
							case DATE:
								f.invoke(obj, new Date(res.getLong(index)));
								break;
							}
						}

					}
					ret.add(obj); // lägger till objekten i listan
				}
			} else {
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return ret;
	}

}
