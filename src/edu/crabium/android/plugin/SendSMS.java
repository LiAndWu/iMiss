package edu.crabium.android.plugin;

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

/**
 * Plug-in class that is called after a phone call was missed.
 * Send message to caller, with previously chosen text
 */
public class SendSMS implements Runnable{
	private SettingProvider sp = SettingProvider.getInstance();
    private Context context = sp.getContext();
	private ContentResolver contentResolver = context.getContentResolver();

	public void run() {
		send();
	}

	private void send() {
		if(!iMissIsOn()) return;
		
		// Retrieve caller number from IMissPhoneStateListener
		String ringingNumber  = IMissPhoneStateListener.RingingNumber;
		if(ringingNumber == null || ringingNumber.trim().equals("")) return;
		
		// Get caller name from contacts by phone number.
		// Set name to blank if number isn't in contacts.
		String callerName = getNameByNumber(ringingNumber);
		
		StringBuilder smsTextBuilder = new StringBuilder();
		StringBuilder notifySummaryBuilder = new StringBuilder();
		notifySummaryBuilder.append("发送了一条短信");
		
		// If caller name is not blank ( caller is in contacts )
		if(!callerName.trim().equals("")){
			// Find group id by caller's number, and use group id to retrieve group message
			int groupId = sp.getGroupIdByPhoneNumber(ringingNumber);
			String message =  sp.getMessageByGroupId(groupId);
			
			// If group message is not blank, use this message as reply text,
			// or use default reply text in "contacts_reply" instead.
			//TODO: change contacts_reply to a more vivid name
			smsTextBuilder.append(message.trim().equals("")? sp.getSetting("contacts_reply") : message);
			notifySummaryBuilder.insert(0,"向" + callerName);
		}
		else{
			// If user choose to reply to stranger, use default reply text in "stranger_reply", or
			// simply return from send()
			if(!replyToStranger()) return;
			smsTextBuilder.append(replyToStranger() ? sp.getSetting("stranger_reply") : " ");
			notifySummaryBuilder.insert(0,"向" + ringingNumber);
		}
		
		// Add [iMiss] to the end of SMS message, inform receiver that this message is sent by application.
		smsTextBuilder.append("[iMiss]");
		
		// Send message to caller
		String smsText = smsTextBuilder.toString();
		SmsManager sm = SmsManager.getDefault();
		sm.sendTextMessage(ringingNumber, null, smsText, null, null);

		// If notification is turned on, notify user in status bar
		if(informOwner()){
			String notifyBodyHeader = "发送内容：";
			String notifyBody = notifyBodyHeader + smsText;
			String notifySummary = notifySummaryBuilder.toString();
			nofity(notifySummary, notifySummary, notifyBody);
		}
		
	}
	
	private boolean informOwner(){
		String InformSwitch = "inform_switch";
		return sp.getSetting(InformSwitch).equals("true") ? true : false;
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
        
        // Clear notification in status bar after user selection
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
			    			
			    			// Remove all hyphens in ringing number
			    			String num = matcher.replaceAll("");
			    			
			    			// If an entry's number is matched with ringing number, return that entry's name column immediately
			    			if(num.equals(RingingNumber))
			    				return name;
			            }
		        }
        }
        finally{
        	// Close all the cursors.
        	// Exception may occur if the cursor hasn't been set when closing.
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
