package edu.crabium.android;

import java.lang.reflect.Array;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			String ServiceSwitch = "service_switch";
			
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
			
			Log.d("iMiss V1.0", "Sending SMS, number:" +IMissPhoneStateListener.RingingNumber + ", text:" + text); 
			
			if(IMissPhoneStateListener.RingingNumber != null && !IMissPhoneStateListener.RingingNumber.trim().equals(""))
			sm.sendTextMessage(IMissPhoneStateListener.RingingNumber, null, text, null, null);
			notify_body = notify_body_header + text;
			
			if(IMissData.getValue(ServiceSwitch).trim().equals("true"))
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
			if(IMissData.getValue(ServiceSwitch).equals("true")){
				return true;
			}
			else
				return false;
		}
	}
}
