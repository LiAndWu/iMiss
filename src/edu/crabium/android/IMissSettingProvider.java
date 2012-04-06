package edu.crabium.android;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class IMissSettingProvider{
	private final static String DATABASE_NAME = "/data/data/edu.crabium.android/iMiss.sqlite3";
	
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
	
	//private final static String CREATE_TABLE_TEXT = "CREATE TABLE IF NOT EXISTS ";
	private final static int VERSION = 1;
	
	private static SQLiteDatabase DB;
	private static ContentResolver contentResolver;
	private static Context context;
	private static ArrayList<String[]> contactsArrayList;
	
	private static final IMissSettingProvider INSTANCE  = new IMissSettingProvider();
	
	private IMissSettingProvider(){
		createTables();
	};
	
	public static void setContext(Context context){
		IMissSettingProvider.context = context;
		IMissSettingProvider.contentResolver = IMissSettingProvider.context.getContentResolver();
	}
	
	public static IMissSettingProvider getInstance(){
		return INSTANCE;
	}
	
	public Map<String, String> getGroups(){
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
	public String getSetting(String key) {
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
	public void deleteSetting(String key) {
		delTableValue(MISC_TABLE_NAME, key);
	}
	
	/** set value if key is matched
	 * 
	 * @param key
	 * @param value
	 */
	public void addSetting(String key, String value) {
		deleteSetting(key);

		DB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
		DB.execSQL(MISC_INSERT_VALUE +"(\"" + key + "\", \"" + value + "\")");
		DB.close();
	}
	
	/** get values from the specified table 
	 * 
	 * @param tableName
	 * @return 
	 */
	private Map<String, String> getTableValues(String tableName)
	{
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
	public void setTableValues(String tableName, String key, String value){
		DB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);

		String sql_text = "INSERT INTO " + tableName + " VALUES (\"" + key + "\", \"" + value + "\")";
		DB.execSQL(sql_text);
		DB.close();
	}
	
	public void delTableValue(String tableName, String key){
		DB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);

		String sql_text = "DELETE FROM " + tableName + " WHERE key=\"" + key + "\"";
		DB.execSQL(sql_text);
		DB.close();
	}
	
	/** return black list information, packed in a Map
	 * 
	 * @return Map<String name, String number> 
	 */
	public Map<String, String> getBlackList()
	{
		return getTableValues(BLACKLIST_TABLE_NAME);
	}
	
	/** return ignore list information, packed in a Map
	 * 
	 * @return
	 */
	public Map<String, String> getIgnoreList()
	{
		return getTableValues(IGNORELIST_TABLE_NAME);
	}
	
	/** insert a column into blacklist.
	 * 
	 */
	public void setBlackList(String key, String value){
		setTableValues(BLACKLIST_TABLE_NAME, key, value);
	}
	
	/** insert key-value pairs
	 * 
	 * @param pairs
	 */
	public void setBlackList(String[][] pairs){
		for(String[] pair : pairs){
			setTableValues(BLACKLIST_TABLE_NAME, pair[0],pair[1]);
		}
	}
	
	/** insert a column into ignore list
	 * 
	 * @param key
	 * @param value
	 */
	public void setIgnoreList(String number, String name){
		setTableValues(IGNORELIST_TABLE_NAME, number, name);
	}
	
	/** delete row in black list if key is matched
	 * 
	 * @param key
	 */
	public void delBlackList(String number){
		DB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);

		String sql_text = "DELETE FROM " + BLACKLIST_TABLE_NAME + " WHERE number=\"" + number + "\"";
		DB.execSQL(sql_text);
		DB.close();
	}
	
	/** delete row in ignore list if key is matched
	 * 
	 * @param key
	 */
	public void delIgnoreList(String number){
		DB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);

		String sql_text = "DELETE FROM " + IGNORELIST_TABLE_NAME + " WHERE number=\"" + number + "\"";
		DB.execSQL(sql_text);
		DB.close();
	}
	
	/** create necessary tables
	 *  
	 */
	private void createTables()
	{
		final String CREATE_TABLE_TEXT = "CREATE TABLE IF NOT EXISTS ";

		String ContactsReply = "contacts_reply";
		String StrangerReply = "stranger_reply";
		String []switches = new String[]{"service_switch","inform_switch","stranger_switch"};
		
		DB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME,null);
		DB.execSQL( CREATE_TABLE_TEXT + BLACKLIST_TABLE_NAME	+ BLACKLIST_TABLE_SPEC);
		DB.execSQL( CREATE_TABLE_TEXT + IGNORELIST_TABLE_NAME	+ IGNORELIST_TABLE_SPEC);
		DB.execSQL( CREATE_TABLE_TEXT + RESTTIME_TABLE_NAME		+ RESTTIME_TABLE_SPEC);
		DB.execSQL( CREATE_TABLE_TEXT + MISC_TABLE_NAME			+ MISC_TABLE_SPEC);
		DB.execSQL( CREATE_TABLE_TEXT + MESSAGES_TABLE_NAME		+ MESSAGES_TABLE_SPEC);
		DB.execSQL( CREATE_TABLE_TEXT + PERSONS_TABLE_NAME		+ PERSONS_TABLE_SPEC);
		DB.execSQL( CREATE_TABLE_TEXT + GROUPS_TABLE_NAME		+ GROUPS_TABLE_SPEC);
		DB.setVersion(VERSION);
		DB.close();
		
		if(getSetting(ContactsReply).trim().equals(""))
			addSetting(ContactsReply, "我的主人暂时不能接听电话，不过我知道你是他的朋友，Have a nice day！！");
		if(getSetting(StrangerReply).trim().equals(""))
			addSetting(StrangerReply, "我的主人好像不认识你哦，难道你是，骗子？");
		
		if(getGroups().size() == 0){
			addGroup(new String[]{"朋友", "你爹在忙"});
			addGroup(new String[]{"家人", "爹我在忙"});
		}
		
		for(String swc : switches){
			if(getSetting(swc).trim().equals("")){
				addSetting(swc, "true");
			}
		}
	}

	public void delGroup(String group_name){
		DB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
		String sql_text = "DELETE FROM " + GROUPS_TABLE_NAME + " WHERE group_name=\"" + group_name + "\"";
		DB.execSQL(sql_text);
		DB.close();
	}
	
	/** add a new group with specific message
	 * 
	 * @param group[] group[0]: group name; group[1]: message
	 */
	public void addGroup(String[] group) {
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

	public void setPersonToGroup(String person_name, String person_phone, String group_name) {
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

	public String getGroupMessage(int group_id){
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
	public int getGroupInfoByNumber(String person_phone){
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
	public String[][] getPersonsFromGroup(String group_name){
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

	public void delPersonInGroup(String person_name, String person_phone,String group_name) {
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
	
	public boolean inContacts(String RingingNumber){
        ContentResolver cr = contentResolver;
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        Pattern pattern = Pattern.compile("-");
		Matcher matcher;
        
        Log.d("iMiss V1.0", "Getting Contacts");
        if(cursor != null && cursor.getCount() > 0){
        	Log.d("iMiss V1.0", "Got cursor.");
	       while(cursor.moveToNext()) {
	            int nameFieldColumnIndex = cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME);
	            String contact = cursor.getString(nameFieldColumnIndex);
	
	            String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
	            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);
	            
	            Log.d("iMiss V1.0", "Getting Phone");
	            if(phone != null && phone.getCount() != 0){
		            while(phone.moveToNext()) {
		                String phoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	    				matcher = pattern.matcher(phoneNumber);
	    				String num = matcher.replaceAll("");
	    				Log.d("iMiss V1.0", "Num: " + num);
	    				if(num.equals(RingingNumber)){
	    					cursor.close();
	    					phone.close();
	    					return true;
	    				}
		                Log.d("GREETING", "PHONE LOOKUP: " + contact + " " +  phoneNumber);
		            }
	            }
	            else{

	                Log.d("iMiss V1.0", "Get phone failed.");
	            }
	            phone.close();
	        }
        }
        else{

            Log.d("iMiss V1.0", "Get cursor failed.");
        }
	    cursor.close();
		return false;
	}
	
    public ArrayList<String[]> getContacts(){   	
        ContentResolver cr = contentResolver;
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        Boolean cleared = false;
        Log.d("iMiss V1.0", "Getting Contacts");
        if(cursor != null && cursor.getCount() > 0){
        	Log.d("iMiss V1.0", "Got cursor.");
	       while(cursor.moveToNext()) {
	            int nameFieldColumnIndex = cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME);
	            String contact = cursor.getString(nameFieldColumnIndex);
	
	            String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
	            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);
	            

	            Log.d("iMiss V1.0", "Getting Phone");
	            if(phone != null && phone.getCount() != 0){
	                Log.d("iMiss V1.0", "Got phone");
	            		
		            while(phone.moveToNext()) {
		                String phoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
		                if(!cleared){
			            	if(contactsArrayList != null)
			            		contactsArrayList.clear();
			            	else
			            		contactsArrayList = new ArrayList<String[]>();
			            	cleared = true;
		                }
		                contactsArrayList.add(new String[]{contact,phoneNumber});
		                Log.d("GREETING", "PHONE LOOKUP:  " + contact + " " +  phoneNumber);
		            }
	            }
	            else{

	                Log.d("iMiss V1.0", "Get phone failed.");
	            }
	            phone.close();
	        }
        }
        else{

            Log.d("iMiss V1.0", "Get cursor failed.");
        }
	    cursor.close();
        
        if(contactsArrayList != null){
        	Log.d("IMiss V1.0", "Contacts not null, size: " + contactsArrayList.size());
        	return new ArrayList<String[]>(contactsArrayList);
        }
        else
        	return new ArrayList<String[]>();
    }

    
	public void delMessage(String group_name) {
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
	
	public void nofity(String notify_summary,String notify_title, String notify_body){     //定义NotificationManager
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);
        //定义通知栏展现的内容信息
        long time = System.currentTimeMillis();
        Notification notification = new Notification(R.drawable.icon_small, notify_summary, time);
         
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
