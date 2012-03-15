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
			String notify_summary = "发送了一条短信";
			String notify_title = "iMiss";
			String notify_body = "";
			String notify_body_header = "发送内容：";
			
			SmsManager sm = SmsManager.getDefault();
			if(!iMissOn()) return;
			
			String destination_name = inContacts(IMissPhoneStateListener.RingingNumber);
			if(!destination_name.trim().equals("")){
				int group_id = IMissData.getGroupInfoByNumber(IMissPhoneStateListener.RingingNumber);
				String message =  IMissData.getGroupMessage(group_id);
				if(message.trim().equals("")){
					text = IMissData.getValue("contacts_reply");
				}
				else{
					text = message;
				}
				notify_summary = "向" + destination_name + notify_summary;
			}
			else{
				if(OpenToStranger()){
					text = IMissData.getValue("stranger_reply");
				}
				else
					text = " ";
				notify_summary = "向" + IMissPhoneStateListener.RingingNumber + notify_summary;
			}
			
			text = text + "[iMiss]";
			sm.sendTextMessage(IMissPhoneStateListener.RingingNumber, null, text, null, null);
			notify_body = notify_body_header + text;
			IMissData.nofity(notify_summary, notify_summary, notify_body);
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
		
		public String inContacts(String RingingNumber){
			List<String[]> array = IMissData.getContacts();
			for(String[] pair : array){
				if(pair[1].equals(RingingNumber)){
					return pair[0];
				}
			}
			return "";
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
