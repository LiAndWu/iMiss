package edu.crabium.android;

import java.lang.reflect.Array;
import java.util.List;

import android.telephony.SmsManager;
import android.util.Log;

public class IMissPlugin {
	public class SendSMS implements Runnable{

		public void Send(String text, String RingingNumber) {
			SmsManager sm = SmsManager.getDefault();
			sm.sendTextMessage(RingingNumber, null, text, null, null);
		}

		public void Send() {
			String text = "";
			SmsManager sm = SmsManager.getDefault();
			IMissData.nofity("Sent a message", "IMiss", "New incoming");
			if(!iMissOn()) return;
			if(inContacts(IMissPhoneStateListener.RingingNumber)){
				int group_id = IMissData.getGroupInfoByNumber(IMissPhoneStateListener.RingingNumber);
				String message =  IMissData.getGroupMessage(group_id);
				Log.d("IMISS", "KNOWN");
				if(message.trim().equals("")){
					//return default
					Log.d("IMISS", "BLANK MESSAGE");
					text = "我知道你是谁，但是我很忙";
					text = IMissData.getValue("contacts_reply");
				}
				else{
					Log.d("IMISS", "HAVE MESSAGE");
					text = message;
				}
				
			}
			else{
				if(OpenToStranger()){
					Log.d("IMISS","NOT KNOWN");
					text = "我不认识你啊！";
					text = IMissData.getValue("stranger_reply");
				}
				else
					text = " ";
			}
			
			sm.sendTextMessage(IMissPhoneStateListener.RingingNumber, null, text, null, null);
		}

		public void run() {
			Send();
		}
		
		public boolean OpenToStranger(){
			String StrangerSwitch = "stranger_switch";
			if(IMissData.getValue(StrangerSwitch).equals("true")){
				return true;
			}
			else
				return false;
		}
		
		public boolean inContacts(String RingingNumber){
			List<String[]> array = IMissData.getContacts();
			for(String[] pair : array){
				Log.d("GREETING", "COMPARING: " + pair[1] + "-" + RingingNumber);
				if(pair[1].equals(RingingNumber)){
					Log.d("GREETING", "HIT");
					return true;
				}
			}
			return false;
		}
		
		public boolean iMissOn(){
			String ServiceSwitch = "service_switch";
			if(IMissData.getValue(ServiceSwitch).equals("true")){
				return true;
			}
			else
				return false;
		}
	}
}
