package edu.crabium.android.imiss;

import java.util.HashMap;
import java.util.Map;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/** 
 * A singleton class that provides all the necessary methods to interact with iMiss' database.
 * 
 */
public class SettingProvider{
	
	private final static String GROUPS_TABLE_NAME = "groups";
	private final static String MESSAGES_TABLE_NAME = "messages";
	private final static String PERSONS_TABLE_NAME = "persons";
	private final static String BLACKLIST_TABLE_NAME = "black_list";
	private final static String IGNORELIST_TABLE_NAME = "ignore_list";
	private final static String RESTTIME_TABLE_NAME = "rest_time";
	private final static String MISC_TABLE_NAME = "misc";
	
	private Context context;
	private static final SettingProvider INSTANCE  = new SettingProvider();
	
	private SettingProvider(){
		createTables();
	};
	
	private SQLiteDatabase openDatabase(){
		final String DATABASE_NAME = "/data/data/edu.crabium.android/iMiss.sqlite3";
		return SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
	}
	
	private void createTables()
	{
		final String CREATE_TABLE_TEXT = "CREATE TABLE IF NOT EXISTS ";
		final String BLACKLIST_TABLE_SPEC = "(number TEXT, name TEXT)";
		final String IGNORELIST_TABLE_SPEC = "(number TEXT, name TEXT)";
		final String RESTTIME_TABLE_SPEC = "(id INTEGER, start_millis INTEGER, end_millis INTEGER)";
		final String MISC_TABLE_SPEC = "(key STRING, value STRING)";
		final String PERSONS_TABLE_SPEC = "(person_id  INTEGER PRIMARY KEY, group_id INTEGER, name STRING, number STRING)";
		final String MESSAGES_TABLE_SPEC = "(message_id  INTEGER PRIMARY KEY, group_id INTEGER, message_body STRING)";
		final String GROUPS_TABLE_SPEC = "(group_id  INTEGER PRIMARY KEY, group_name STRING)";

		final String ContactsReply = "contacts_reply";
		final String StrangerReply = "stranger_reply";
		
		final String []switches = new String[]{"service_switch","inform_switch","stranger_switch"};
		
		final int VERSION = 1;
		
		SQLiteDatabase DB = openDatabase();
		
		DB.execSQL( CREATE_TABLE_TEXT + BLACKLIST_TABLE_NAME	+ BLACKLIST_TABLE_SPEC);
		DB.execSQL( CREATE_TABLE_TEXT + IGNORELIST_TABLE_NAME	+ IGNORELIST_TABLE_SPEC);
		DB.execSQL( CREATE_TABLE_TEXT + RESTTIME_TABLE_NAME		+ RESTTIME_TABLE_SPEC);
		DB.execSQL( CREATE_TABLE_TEXT + MISC_TABLE_NAME			+ MISC_TABLE_SPEC);
		DB.execSQL( CREATE_TABLE_TEXT + MESSAGES_TABLE_NAME		+ MESSAGES_TABLE_SPEC);
		DB.execSQL( CREATE_TABLE_TEXT + PERSONS_TABLE_NAME		+ PERSONS_TABLE_SPEC);
		DB.execSQL( CREATE_TABLE_TEXT + GROUPS_TABLE_NAME		+ GROUPS_TABLE_SPEC);
		DB.setVersion(VERSION);
		DB.close();
		
		// Set default replies for strangers and friends
		if(getSetting(ContactsReply).trim().equals(""))
			addSetting(ContactsReply, "我的主人暂时不能接听电话，不过我知道你是他的朋友，Have a nice day！！");
		if(getSetting(StrangerReply).trim().equals(""))
			addSetting(StrangerReply, "我的主人好像不认识你哦，难道你是，骗子？");

		// Set default groups and messages
		if(getGroups().size() == 0){
			addGroup("朋友", "你爹在忙");
			addGroup("家人", "爹我在忙");
		}
		
		// Create setting and set to true if setting is not in database
		for(String swc : switches){
			if(getSetting(swc).trim().equals("")){
				addSetting(swc, "true");
			}
		}
	}
	
	private Map<String, String> getTableValues(String tableName)
	{
		SQLiteDatabase DB = openDatabase();
		
		String query = "SELECT * FROM " + tableName;
		Cursor cursor = DB.rawQuery(query, null);
		
		Map<String, String> list = new HashMap<String, String>();
		if(cursor.getCount() > 0){
			while(cursor.moveToNext()){
				list.put(cursor.getString(0), cursor.getString(1));
			}
		}
		cursor.close();
		DB.close();
		return new HashMap<String, String>(list);
		
	}
	
	private void addEntryToTable(String tableName, String key, String value){
		SQLiteDatabase DB = openDatabase();

		String sql_text = "INSERT INTO " + tableName + " VALUES (\"" + key + "\", \"" + value + "\")";
		DB.execSQL(sql_text);
		DB.close();
	}
	
	/**
	 * 
	 * @return the previously stored context
	 */
	public Context getContext(){
		return context;
	}
	
	/**
	 * store application runtime environment to this provider
	 * @param context Context of a specific Activity or Service
	 */
	public void setContext(Context context){
		this.context = context;
	}
	
	/**
	 * 
	 * @return a iMissSettingProvider singleton
	 */
	public static SettingProvider getInstance(){
		return INSTANCE;
	}
	
	/**
	 * 
	 * @return a Map that stores <group name, message> entries
	 */
	public Map<String, String> getGroups(){
		SQLiteDatabase DB = openDatabase();

		Map<String, String> map = new HashMap<String, String>();
		Cursor cursor = DB.rawQuery("SELECT group_name, message_body FROM " + GROUPS_TABLE_NAME + " INNER JOIN " + MESSAGES_TABLE_NAME + " USING (group_id)", null);
		while(cursor.moveToNext()){
			map.put(cursor.getString(0), cursor.getString(1));
		}
		cursor.close();
		DB.close();
		return map;
	}
	
	/**
	 * Return setting of the specified key
	 * @param key setting
	 * @return setting value
	 */
	public String getSetting(String key) {
		final String MISC_GET_VALUE = "SELECT key, value FROM " + MISC_TABLE_NAME + " WHERE key =";
		
		SQLiteDatabase DB = openDatabase();
		Cursor cursor = DB.rawQuery(MISC_GET_VALUE + "\"" + key + "\"", null);
		
		String result;
		if(cursor.getCount() < 1)
			result = " ";
		else{
			cursor.moveToNext();
			result = cursor.getString(cursor.getColumnIndex("value"));
		}

		cursor.close();
		DB.close();
		return result;
	}

	/**
	 * Delete a setting that specified by the key
	 * @param key setting
	 */
	public void deleteSetting(String key) {
		String sql_text = "DELETE FROM " + MISC_TABLE_NAME + " WHERE key=\"" + key + "\"";
		SQLiteDatabase DB = openDatabase();
		DB.execSQL(sql_text);
		DB.close();
	}
	
	
	/**
	 * Add a new setting, delete the old setting if key already exists.
	 * 
	 * @param key name of the new setting
	 * @param value
	 */
	public void addSetting(String key, String value) {
		final String MISC_INSERT_VALUE = "INSERT INTO " + MISC_TABLE_NAME + " (key, value) VALUES ";
		
		deleteSetting(key);
		SQLiteDatabase DB = openDatabase();
		DB.execSQL(MISC_INSERT_VALUE +"(\"" + key + "\", \"" + value + "\")");
		DB.close();
	}
	
	/** 
	 * Return black list information
	 * @return  a Map with <number, name> entries
	 */
	public Map<String, String> getBlackList()
	{
		return getTableValues(BLACKLIST_TABLE_NAME);
	}
	
	/**
	 * Return ignore list information
	 * @return a Map with <number, name> entries
	 */
	public Map<String, String> getIgnoreList()
	{
		return getTableValues(IGNORELIST_TABLE_NAME);
	}
	
	/**
	 * Add a person to black list.
	 * @param number Person's number
	 * @param name Person's name
	 */
	public void addPersonToBlackList(String number, String name){
		addEntryToTable(BLACKLIST_TABLE_NAME, number, name);
	}
	
	/**
	 * Add something to ignore list
	 * Ignore list is not black list, it stores some numbers that you 
	 * never what to reply, such as 10086, 10010.
	 * 
	 * @param number it's number
	 * @param name it's name
	 */
	public void addPersonToIgnoreList(String number, String name){
		addEntryToTable(IGNORELIST_TABLE_NAME, number, name);
	}
	
	/**
	 * Delete entries from black list, if number is matched
	 * @param number the number that you want to remove from black list
	 */
	public void deleteFromBlackList(String number){
		SQLiteDatabase DB = openDatabase();

		String sql_text = "DELETE FROM " + BLACKLIST_TABLE_NAME + " WHERE number=\"" + number + "\"";
		DB.execSQL(sql_text);
		DB.close();
	}
	
	/**
	 * Delete entries from ignore list, if number is matched
	 * 
	 * @param number the number that you want to remove from ignore list
	 */
	public void deleteFromIgnoreList(String number){
		SQLiteDatabase DB = openDatabase();

		String sql_text = "DELETE FROM " + IGNORELIST_TABLE_NAME + " WHERE number=\"" + number + "\"";
		DB.execSQL(sql_text);
		DB.close();
	}

	/**
	 * Delete a group with the specified group name
	 * @param group_name group that you want to delete
	 */
	public void deleteGroup(String group_name){
		SQLiteDatabase DB = openDatabase();
		String sql_text = "DELETE FROM " + GROUPS_TABLE_NAME + " WHERE group_name=\"" + group_name + "\"";
		DB.execSQL(sql_text);
		DB.close();
	}
	
	/**
	 * Add a group with specified name and message
	 * @param name group name
	 * @param message message for this group
	 */
	public void addGroup(String name, String message) {
		SQLiteDatabase DB = openDatabase();
		String sql_text = "INSERT INTO " + GROUPS_TABLE_NAME + " VALUES (null, \"" + name + "\")";
		DB.execSQL(sql_text);
		Cursor cursor = DB.rawQuery("SELECT group_id FROM " + GROUPS_TABLE_NAME + " WHERE group_name=?", new String[]{name});
		cursor.moveToNext();
		int group_id = cursor.getInt(0);
		sql_text = "INSERT INTO " + MESSAGES_TABLE_NAME + " VALUES (null, " + group_id + ", \"" + message + "\")";
		DB.execSQL(sql_text);
		cursor.close();
		DB.close();
	}

	//TODO Need Refactor
	/**
	 * Add/Move person to a new group
	 * @param person_name
	 * @param person_phone
	 * @param group_name
	 */
	public void addPersonToGroup(String person_name, String person_phone, String group_name) {
		SQLiteDatabase DB = openDatabase();
		
		String sql_text;
		Cursor cursor = DB.rawQuery("SELECT group_id FROM " + GROUPS_TABLE_NAME + " WHERE group_name=\"" + group_name + "\"", null);
		cursor.moveToNext();
		int group_id = cursor.getInt(0);
		cursor.close();
		
		cursor = DB.rawQuery("SELECT person_id FROM " + PERSONS_TABLE_NAME + " WHERE name=\"" + person_name + "\" AND number=\"" + person_phone + "\"", null);
		if(cursor.getCount() < 1){
			sql_text = "INSERT INTO " + PERSONS_TABLE_NAME + " VALUES (null, " + group_id  + ",\"" + person_name + "\",\"" + person_phone + "\")"; 
			DB.execSQL(sql_text);
		}
		else{
			sql_text = "UPDATE " + PERSONS_TABLE_NAME + " SET group_id = " + group_id + " WHERE name=\"" + person_name + "\" AND number=\"" + person_phone + "\"";
			DB.execSQL(sql_text);
		}
		cursor.close();
		DB.close();
	}

	/**
	 * Return message of the group with specified group id
	 * @param group_id
	 * @return group message, black string is returned if specific group is not found
	 */
	public String getMessageByGroupId(int group_id){
		SQLiteDatabase DB = openDatabase();
		
		String sql_text = "SELECT message_body FROM " + MESSAGES_TABLE_NAME + " WHERE group_id = " + group_id;
		Cursor cursor = DB.rawQuery(sql_text, null);
		
		if(cursor.getCount() < 1)
			return "";
		cursor.moveToNext();
		String str = cursor.getString(0);
		cursor.close();
		DB.close();
		
		return str;
	}
	
	/**
	 * Delete message of spcified group
	 * @param group_name
	 */
	public void deleteMessage(String group_name) {
		SQLiteDatabase DB = openDatabase();
		String sql_text = "SELECT group_id FROM " + GROUPS_TABLE_NAME + " WHERE group_name = \"" + group_name + "\"";
		Cursor cursor = DB.rawQuery(sql_text, null);
		
		Log.d("GREETING",""+group_name);
		if(cursor.getCount() >= 1){
			cursor.moveToNext();
			int id = cursor.getInt(0);
			Log.d("GREETING", "id: " + id);
			sql_text = "DELETE FROM " + MESSAGES_TABLE_NAME + " WHERE group_id=" + id;
			DB.execSQL(sql_text);
		}
		cursor.close();
		DB.close();
	}
	
	/**
	 * Return a person's group id by telephone number
	 * @param person_phone
	 * @return group id, 0 is returned if number doesn't match
	 */
	public int getGroupIdByPhoneNumber(String person_phone){
		SQLiteDatabase DB = openDatabase();
		
		String sql_text = "SELECT group_id FROM " + PERSONS_TABLE_NAME + " WHERE number = \"" + person_phone + "\"";
		Cursor cursor = DB.rawQuery(sql_text, null);
		
		if(cursor.getCount() < 1){
			cursor.close();
			DB.close();
			return 0;
		}
		cursor.moveToNext();
		int group_id = cursor.getInt(0);
		cursor.close();
		DB.close();
		return group_id;
	}
	
	/**
	 * Return all the persons in the group
	 * @param group_name
	 * @return A 2D array, with {name, number, group id} entries
	 */
	public String[][] getPersonsFromGroup(String group_name){
		SQLiteDatabase DB = openDatabase();
		String[][] tuple;
		String sql_text = "SELECT name, number, group_id FROM " + GROUPS_TABLE_NAME + " INNER JOIN " + PERSONS_TABLE_NAME + " USING (group_id) WHERE group_name=\"" + group_name + "\"";
		Cursor cursor = DB.rawQuery(sql_text,null);
		tuple = new String[cursor.getCount()][3];
		int i = 0;
		while(cursor.moveToNext()){
			tuple[i++] = new String[]{cursor.getString(0), cursor.getString(1), "" + cursor.getInt(2)};
		}
		cursor.close();
		DB.close();
		return tuple;
	}

	/**
	 * Delete a person from a specific group
	 * @param person_name
	 * @param person_phone
	 * @param group_name
	 */
	public void deletePersonFromGroup(String person_name, String person_phone,String group_name) {
		SQLiteDatabase DB = openDatabase();
		
		String sql_text = "SELECT group_id FROM " + GROUPS_TABLE_NAME + " WHERE group_name = \"" + group_name + "\"";
		Cursor cursor = DB.rawQuery(sql_text, null);
		cursor.moveToNext();
		int group_id = cursor.getInt(0);
		
		sql_text = "UPDATE " + PERSONS_TABLE_NAME + " SET group_id = 0 WHERE group_id=" + group_id + " AND name = \"" + person_name + "\" AND number =\"" + person_phone + "\"";
		DB.execSQL(sql_text);
		cursor.close();
		DB.close();
	}
}
