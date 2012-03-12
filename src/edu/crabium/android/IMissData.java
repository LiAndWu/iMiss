package edu.crabium.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class IMissData{
	private final static String DATABASE_NAME = "/data/data/edu.crabium.android/files/iMiss.sqlite3";
	
	private final static String BLACKLIST_TABLE_NAME = "black_list";
	private final static String BLACKLIST_TABLE_SPEC = "(name TEXT, number TEXT)";
	private final static String BLACKLIST_GET = "SELECT * FROM " + BLACKLIST_TABLE_NAME;
	private final static String BLACKLIST_SET = "INSERT INTO " + BLACKLIST_TABLE_NAME + " VALUES ";
	
	private final static String IGNORELIST_TABLE_NAME = "ignore_list";
	private final static String IGNORELIST_TABLE_SPEC = "(name TEXT, number TEXT)";
	
	private final static String RESTTIME_TABLE_NAME = "rest_time";
	private final static String RESTTIME_TABLE_SPEC = "(id INTEGER, start_millis INTEGER, end_millis INTEGER)";
	
	private final static String MISC_TABLE_NAME = "misc";
	private final static String MISC_TABLE_SPEC = "(key STRING, value STRING)";
	private final static String MISC_GET_VALUE = "SELECT key, value FROM " + MISC_TABLE_NAME + " WHERE key =";
	private final static String MISC_DEL_VALUE = "DELETE FROM " + MISC_TABLE_NAME + " WHERE key =";
	private final static String MISC_INSERT_VALUE = "INSERT INTO " + MISC_TABLE_NAME + " (key, value) VALUES ";
	private final static String CREATE_TABLE_TEXT = "CREATE TABLE IF NOT EXISTS ";
	private final static String VERSION = "1";
	
	private static Activity activity;
	private static SQLiteDatabase DB;
	
	private static boolean initiated = false;
	
	
	/** get value for specified key
	 * 
	 * @param  key
	 * @return  value
	 */
	public static String getValue(String key) {
		if(!initiated) createTables();
		DB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
		Cursor cursor = DB.rawQuery(MISC_GET_VALUE + "\"" + key + "\"", null);
		
		String result;
		if(cursor.getCount() < 1)
			result = " ";
		else{
			cursor.moveToNext();
			result = cursor.getString(cursor.getColumnIndex("value"));
		}
		
		DB.close();
		return result;
	}

	/** delete value if key is matched
	 * 
	 * @param key
	 */
	public static void delValue(String key) {
		if(!initiated) createTables();
		DB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
		DB.execSQL(MISC_DEL_VALUE + "\"" + key + "\"");
		DB.close();
	}
	
	/** set value if key is matched
	 * 
	 * @param key
	 * @param value
	 */
	public static void setValue(String key, String value) {
		if(!initiated) createTables();
		
		if(!getValue(key).trim().equals(""))
			delValue(key);

		DB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
		DB.execSQL(MISC_INSERT_VALUE +"(\"" + key + "\", \"" + value + "\")");
		DB.close();
	}
	
	/** return black list information, packed in a Map
	 * 
	 * @return Map<String name, String number> 
	 */
	public static Map<String, String> getBlackList()
	{
		if(!initiated) createTables();
		DB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
		Cursor cursor = DB.rawQuery(BLACKLIST_GET, null);
		
		Map<String, String> blacklist = new HashMap<String, String>();
		if(cursor.getCount() > 0){
			while(cursor.moveToNext()){
				blacklist.put(cursor.getString(0), cursor.getString(1));
			}
		}
		DB.close();
		return new HashMap<String, String>(blacklist);
	}
	
	/** insert a column into blacklist.
	 * 
	 */
	public static void setBlackList(String key, String value){
		if(!initiated) createTables();
		DB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);

		String[] pair = new String[2];
		pair[0] = key;
		pair[1] = value;
		DB.execSQL(BLACKLIST_SET + "(\"" + key + "\", \"" + value + "\")");
		DB.close();
	}
	/** get activity and create necessary tables
	 * 
	 * @param activity
	 */
	public static void init(Activity activity){
		IMissData.activity = activity;
		createTables();
		initiated = true;
	}
	
	/** create necessary tables
	 *  
	 */
	private static void createTables()
	{
		DB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME,null);
		DB.execSQL( CREATE_TABLE_TEXT + BLACKLIST_TABLE_NAME	+BLACKLIST_TABLE_SPEC);
		DB.execSQL( CREATE_TABLE_TEXT + IGNORELIST_TABLE_NAME	+ IGNORELIST_TABLE_SPEC);
		DB.execSQL( CREATE_TABLE_TEXT + RESTTIME_TABLE_NAME	+ RESTTIME_TABLE_SPEC);
		DB.execSQL( CREATE_TABLE_TEXT + MISC_TABLE_NAME	+ MISC_TABLE_SPEC);
		DB.close();
	}
}
