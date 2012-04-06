package edu.crabium.android;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.telephony.SmsManager;
import android.util.Log;

public class IMissPlugin {
	private IMissSettingProvider sp = IMissSettingProvider.getInstance();
    Context context = sp.getContext();
	ContentResolver contentResolver = context.getContentResolver();
	
	public class SendSMS implements Runnable{
		public void Send(String text, String RingingNumber) {
			SmsManager sm = SmsManager.getDefault();
			sm.sendTextMessage(RingingNumber, null, text, null, null);
		}

		public void Send() {
			String text = "";
			String notify_summary = "发送了一条短信";
			String notify_title = "iMiss";
			String notify_body = "";
			String notify_body_header = "发送内容：";
			String ServiceSwitch = "service_switch";
			
			SmsManager sm = SmsManager.getDefault();
			if(!iMissOn()) return;
			
			
			String destination_name = inContacts(IMissPhoneStateListener.RingingNumber);
			if(!destination_name.trim().equals("")){
				int group_id = sp.getGroupIdByPhoneNumber(IMissPhoneStateListener.RingingNumber);
				String message =  sp.getMessageByGroupId(group_id);
				if(message.trim().equals("")){
					text = sp.getSetting("contacts_reply");
				}
				else{
					text = message;
				}
				notify_summary = "向" + destination_name + notify_summary;
			}
			else{
				if(OpenToStranger()){
					text = sp.getSetting("stranger_reply");
				}
				else
					text = " ";
				notify_summary = "向" + IMissPhoneStateListener.RingingNumber + notify_summary;
			}
			
			text = text + "[iMiss]";
			
			Log.d("iMiss V1.0", "Sending SMS, number:" +IMissPhoneStateListener.RingingNumber + ", text:" + text); 
			
			if(IMissPhoneStateListener.RingingNumber != null && !IMissPhoneStateListener.RingingNumber.trim().equals(""))
			sm.sendTextMessage(IMissPhoneStateListener.RingingNumber, null, text, null, null);
			notify_body = notify_body_header + text;
			
			if(sp.getSetting(ServiceSwitch).trim().equals("true"))
				nofity(notify_summary, notify_summary, notify_body);
		}

		public void run() {
			Send();
		}
		
		public boolean OpenToStranger(){
			String StrangerSwitch = "stranger_switch";
			if(sp.getSetting(StrangerSwitch).equals("true")){
				return true;
			}
			else
				return false;
		}
		
		public String inContacts(String RingingNumber){
			List<String[]> array = getContacts();
			Pattern pattern = Pattern.compile("-");
			Matcher matcher;
			
			System.out.println("array.size()=" + array.size());
			for(String[] pair : array){
				Log.d("iMiss V1.0", "Pair[1]: " + pair[1]);
				matcher = pattern.matcher(pair[1]);
				String num = matcher.replaceAll("");
				Log.d("iMiss V1.0", "Num: " + num);
				if(num.equals(RingingNumber)){
					return pair[0];
				}
			}
			return "";
		}
		
		public boolean iMissOn(){
			String ServiceSwitch = "service_switch";
			if(sp.getSetting(ServiceSwitch).equals("true")){
				return true;
			}
			else
				return false;
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
		
		public ArrayList<String[]> getContacts(){   	
	        ContentResolver cr = contentResolver;
	        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
	    	ArrayList<String[]> contactsArrayList = null;
	    	
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
	}
}
