package edu.crabium.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.util.Log;

public class IMissData{
	private final static String DATABASE_NAME = "/data/data/edu.crabium.android/files/iMiss.sqlite3";
	
	private final static String GROUPS_TABLE_NAME = "groups";
	private final static String GROUPS_TABLE_SPEC = "(group_id  INTEGER PRIMARY KEY, group_name STRING)";
	
	private final static String MESSAGES_TABLE_NAME = "messages";
	private final static String MESSAGES_TABLE_SPEC = "(message_id  INTEGER PRIMARY KEY, group_id INTEGER, message_body STRING)";
	
	private final static String PERSONS_TABLE_NAME = "persons";
	private final static String PERSONS_TABLE_SPEC = "(person_id  INTEGER PRIMARY KEY, group_id INTEGER, name STRING, number STRING)";
	
	private final static String BLACKLIST_TABLE_NAME = "black_list";
	private final static String BLACKLIST_TABLE_SPEC = "(number TEXT, name TEXT)";
	
	private final static String IGNORELIST_TABLE_NAME = "ignore_list";
	private final static String IGNORELIST_TABLE_SPEC = "(number TEXT, name TEXT)";
	
	private final static String RESTTIME_TABLE_NAME = "rest_time";
	private final static String RESTTIME_TABLE_SPEC = "(id INTEGER, start_millis INTEGER, end_millis INTEGER)";
	
	private final static String MISC_TABLE_NAME = "misc";
	private final static String MISC_TABLE_SPEC = "(key STRING, value STRING)";
	private final static String MISC_GET_VALUE = "SELECT key, value FROM " + MISC_TABLE_NAME + " WHERE key =";
	private final static String MISC_INSERT_VALUE = "INSERT INTO " + MISC_TABLE_NAME + " (key, value) VALUES ";
	
	private final static String CREATE_TABLE_TEXT = "CREATE TABLE IF NOT EXISTS ";
	private final static int VERSION = 1;
	
	private static SQLiteDatabase DB;
	private static ContentResolver contentResolver;
	private static Context context;
	private static boolean initiated = false;
	
	public static Map<String, String> getGroups(){
		if(!initiated) createTables();
		DB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);

		Map<String, String> map = new HashMap<String, String>();
		Cursor cursor = DB.rawQuery("SELECT group_name, message_body FROM " + GROUPS_TABLE_NAME + " INNER JOIN " + MESSAGES_TABLE_NAME + " USING (group_id)", null);
		while(cursor.moveToNext()){
			map.put(cursor.getString(0), cursor.getString(1));
		}
		cursor.close();
		DB.close();
		return map;
	}
	
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

		cursor.close();
		DB.close();
		return result;
	}

	/** delete value if key is matched
	 * 
	 * @param key
	 */
	public static void delValue(String key) {
		delTableValue(MISC_TABLE_NAME, key);
	}
	
	/** set value if key is matched
	 * 
	 * @param key
	 * @param value
	 */
	public static void setValue(String key, String value) {
		if(!initiated) createTables();
		delValue(key);

		DB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
		DB.execSQL(MISC_INSERT_VALUE +"(\"" + key + "\", \"" + value + "\")");
		DB.close();
	}
	
	/** get values from the specified table 
	 * 
	 * @param tableName
	 * @return 
	 */
	private static Map<String, String> getTableValues(String tableName)
	{
		if(!initiated) createTables();
		DB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
		
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
	
	/** add a new column in specified table, with key-value pair
	 * 
	 * @param tableName
	 * @param key
	 * @param value
	 */
	public static void setTableValues(String tableName, String key, String value){
		if(!initiated) createTables();
		DB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);

		String sql_text = "INSERT INTO " + tableName + " VALUES (\"" + key + "\", \"" + value + "\")";
		DB.execSQL(sql_text);
		DB.close();
	}
	
	public static void delTableValue(String tableName, String key){
		if(!initiated) createTables();
		DB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);

		String sql_text = "DELETE FROM " + tableName + " WHERE key=\"" + key + "\"";
		DB.execSQL(sql_text);
		DB.close();
	}
	
	/** return black list information, packed in a Map
	 * 
	 * @return Map<String name, String number> 
	 */
	public static Map<String, String> getBlackList()
	{
		return getTableValues(BLACKLIST_TABLE_NAME);
	}
	
	/** return ignore list information, packed in a Map
	 * 
	 * @return
	 */
	public static Map<String, String> getIgnoreList()
	{
		return getTableValues(IGNORELIST_TABLE_NAME);
	}
	
	/** insert a column into blacklist.
	 * 
	 */
	public static void setBlackList(String key, String value){
		setTableValues(BLACKLIST_TABLE_NAME, key, value);
	}
	
	/** insert key-value pairs
	 * 
	 * @param pairs
	 */
	public static void setBlackList(String[][] pairs){
		for(String[] pair : pairs){
			setTableValues(BLACKLIST_TABLE_NAME, pair[0],pair[1]);
		}
	}
	
	/** insert a column into ignore list
	 * 
	 * @param key
	 * @param value
	 */
	public static void setIgnoreList(String number, String name){
		setTableValues(IGNORELIST_TABLE_NAME, number, name);
	}
	
	/** delete row in black list if key is matched
	 * 
	 * @param key
	 */
	public static void delBlackList(String number){
		if(!initiated) createTables();
		DB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);

		String sql_text = "DELETE FROM " + BLACKLIST_TABLE_NAME + " WHERE number=\"" + number + "\"";
		DB.execSQL(sql_text);
		DB.close();
	}
	
	/** delete row in ignore list if key is matched
	 * 
	 * @param key
	 */
	public static void delIgnoreList(String number){
		if(!initiated) createTables();
		DB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);

		String sql_text = "DELETE FROM " + IGNORELIST_TABLE_NAME + " WHERE number=\"" + number + "\"";
		DB.execSQL(sql_text);
		DB.close();
	}
	/** get activity and create necessary tables
	 * 
	 * @param activity
	 */
	
	public static void init(Activity activity){
		createTables();
		initiated = true;
		context = activity;
		IMissData.contentResolver = activity.getContentResolver();
	}
	
	/** create necessary tables
	 *  
	 */
	private static void createTables()
	{
		if(!initiated){
			DB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME,null);
			DB.execSQL( CREATE_TABLE_TEXT + BLACKLIST_TABLE_NAME	+ BLACKLIST_TABLE_SPEC);
			DB.execSQL( CREATE_TABLE_TEXT + IGNORELIST_TABLE_NAME	+ IGNORELIST_TABLE_SPEC);
			DB.execSQL( CREATE_TABLE_TEXT + RESTTIME_TABLE_NAME		+ RESTTIME_TABLE_SPEC);
			DB.execSQL( CREATE_TABLE_TEXT + MISC_TABLE_NAME			+ MISC_TABLE_SPEC);
			DB.execSQL( CREATE_TABLE_TEXT + MESSAGES_TABLE_NAME		+ MESSAGES_TABLE_SPEC);
			DB.execSQL( CREATE_TABLE_TEXT + PERSONS_TABLE_NAME		+ PERSONS_TABLE_SPEC);
			DB.execSQL( CREATE_TABLE_TEXT + GROUPS_TABLE_NAME		+ GROUPS_TABLE_SPEC);
			initiated = true;
			DB.setVersion(VERSION);
			DB.close();
		}
	}

	public static void delGroup(String group_name){
		if(!initiated) createTables();
		DB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
		String sql_text = "DELETE FROM " + GROUPS_TABLE_NAME + " WHERE group_name=\"" + group_name + "\"";
		DB.execSQL(sql_text);
		DB.close();
	}
	
	public static void addGroup(String[] group) {
		if(!initiated) createTables();
		DB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
		String sql_text = "INSERT INTO " + GROUPS_TABLE_NAME + " VALUES (null, \"" + group[0] + "\")";
		DB.execSQL(sql_text);
		Cursor cursor = DB.rawQuery("SELECT group_id FROM " + GROUPS_TABLE_NAME + " WHERE group_name=?", new String[]{group[0]});
		cursor.moveToNext();
		int group_id = cursor.getInt(0);
		sql_text = "INSERT INTO " + MESSAGES_TABLE_NAME + " VALUES (null, " + group_id + ", \"" + group[1] + "\")";
		DB.execSQL(sql_text);
		cursor.close();
		DB.close();
	}

	public static void setPersonToGroup(String person_name, String person_phone, String group_name) {
		if(!initiated) createTables();
		DB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
		
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

	public static String getGroupMessage(int group_id){
		if(!initiated) createTables();
		DB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
		
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
	public static int getGroupInfoByNumber(String person_phone){
		if(!initiated) createTables();
		DB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
		
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
	public static String[][] getPersonsFromGroup(String group_name){
		if(!initiated) createTables();
		DB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
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

	public static void delPersonInGroup(String person_name, String person_phone,String group_name) {
		if(!initiated) createTables();
		DB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
		
		String sql_text = "SELECT group_id FROM " + GROUPS_TABLE_NAME + " WHERE group_name = \"" + group_name + "\"";
		Cursor cursor = DB.rawQuery(sql_text, null);
		cursor.moveToNext();
		int group_id = cursor.getInt(0);
		
		sql_text = "UPDATE " + PERSONS_TABLE_NAME + " SET group_id = 0 WHERE group_id=" + group_id + " AND name = \"" + person_name + "\" AND number =\"" + person_phone + "\"";
		DB.execSQL(sql_text);
		cursor.close();
		DB.close();
	}
	
    public static ArrayList<String[]> getContacts(){
    	ArrayList<String[]> value = new ArrayList<String[]>();
    	
        ContentResolver cr = contentResolver;
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

       while(cursor.moveToNext()) {
            int nameFieldColumnIndex = cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME);
            String contact = cursor.getString(nameFieldColumnIndex);

            String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);

            while(phone.moveToNext()) {
                String phoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));     
                value.add(new String[]{contact,phoneNumber});
                Log.d("GREETING", "PHONE LOOKUP: " + contact + " " +  phoneNumber);
            }
        }
        cursor.close();
        return new ArrayList<String[]>(value);
    }

    
	public static void delMessage(String group_name) {
		if(!initiated) createTables();
		DB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
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
	
	public static void nofity(String notify_summary,String notify_title, String notify_body){     //定义NotificationManager
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);
        //定义通知栏展现的内容信息
        long time = System.currentTimeMillis();
        Notification notification = new Notification(R.drawable.add, notify_summary, time);
         
        //定义下拉通知栏时要展现的内容信息
        Intent notificationIntent = new Intent(context, IMissActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, 0);
        notification.setLatestEventInfo(context, notify_title, notify_body,
                contentIntent);
         
        //用mNotificationManager的notify方法通知用户生成标题栏消息通知
        mNotificationManager.notify(1, notification);
	}
}
