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
		String callerName = getNameByNumber(ringingNumber);
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
	
	private boolean iMissIsOn(){
		String ServiceSwitch = "service_switch";
		return sp.getSetting(ServiceSwitch).equals("true") ?  true : false;
	}
	
	private void nofity(String notifySummary,String notifyTitle, String notifyBody){
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(ns);
        
        long time = System.currentTimeMillis();
        Notification notification = new Notification(R.drawable.icon_small, notifySummary, time);
         
        Intent notificationIntent = new Intent(context, IMissActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, notifyTitle, notifyBody, contentIntent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        
        notificationManager.notify(1, notification);
	}
	
	private String getNameByNumber(String RingingNumber){
		Pattern pattern = Pattern.compile("-");
        ContentResolver cr = contentResolver;
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        Cursor phone = null;
        
        try{
	        if(cursor != null && cursor.getCount() > 0)
		       while(cursor.moveToNext()) {
		            int nameFieldColumnIndex = cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME);
		            String name = cursor.getString(nameFieldColumnIndex);
		
		            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
		            phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, 
		            		ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
	
		            if(phone != null && phone.getCount() != 0)
			            while(phone.moveToNext()) {
			                String phoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			    			Matcher matcher = pattern.matcher(phoneNumber);
			    			String num = matcher.replaceAll("");
			    			if(num.equals(RingingNumber))
			    				return name;
			            }
		        }
        }
        finally{
        	if (phone != null)
        		try{
        			phone.close();
        		}catch(Exception e){}
        	
        	if( cursor != null)
        		try{
        			cursor.close();
        		}catch(Exception e){}
        }
        
        return " ";
	}
}
