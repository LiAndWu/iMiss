package edu.crabium.android.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.crabium.android.IMissActivity;
import edu.crabium.android.IMissPhoneStateListener;
import edu.crabium.android.SettingProvider;
import edu.crabium.android.R;

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

public class SendSMS implements Runnable{
	private SettingProvider sp = SettingProvider.getInstance();
    Context context = sp.getContext();
	ContentResolver contentResolver = context.getContentResolver();

	private void send() {
		if(!iMissIsOn()) return;

		String ringingNumber;
		ringingNumber  = IMissPhoneStateListener.RingingNumber;
		String callerName = inContacts(ringingNumber);
		StringBuilder smsTextBuilder = new StringBuilder();
		StringBuilder notifySummaryBuilder = new StringBuilder();
		notifySummaryBuilder.append("发送了一条短信");
		
		if(!callerName.trim().equals("")){
			int groupId = sp.getGroupIdByPhoneNumber(ringingNumber);
			String message =  sp.getMessageByGroupId(groupId);
			
			smsTextBuilder.append(message.trim().equals("")? sp.getSetting("contacts_reply") : message);
			notifySummaryBuilder.insert(0,"向" + callerName);
		}
		else{
			smsTextBuilder.append(replyToStranger() ? sp.getSetting("stranger_reply") : " ");
			notifySummaryBuilder.insert(0,"向" + ringingNumber);
		}
		
		smsTextBuilder.append("[iMiss]");
		
		Log.d("iMiss V1.0", "Sending SMS, number:" +ringingNumber + ", text:" + smsTextBuilder.toString()); 
		
		if(ringingNumber != null && !ringingNumber.trim().equals("")){
			String smsText = smsTextBuilder.toString();
			SmsManager sm = SmsManager.getDefault();
			sm.sendTextMessage(ringingNumber, null, smsText, null, null);

			String notifyBodyHeader = "发送内容：";
			String notifyBody = notifyBodyHeader + smsText;
			String notifySummary = notifySummaryBuilder.toString();
			nofity(notifySummary, notifySummary, notifyBody);
		}
	}

	public void run() {
		send();
	}
	
	private boolean replyToStranger(){
		String StrangerSwitch = "stranger_switch";
		return sp.getSetting(StrangerSwitch).equals("true") ? true : false;
	}
	
	private String inContacts(String RingingNumber){
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
	
	private boolean iMissIsOn(){
		String ServiceSwitch = "service_switch";
		return sp.getSetting(ServiceSwitch).equals("true") ?  true : false;
	}
	
	private void nofity(String notify_summary,String notify_title, String notify_body){     //定义NotificationManager
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
	
	private ArrayList<String[]> getContacts(){   	
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
