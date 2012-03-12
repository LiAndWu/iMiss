package edu.crabium.android;

import android.telephony.SmsManager;

public class IMissPlugin {
	public class SendSMS implements Runnable{

		public void Send(String text, String RingingNumber) {
			SmsManager sm = SmsManager.getDefault();
			sm.sendTextMessage(RingingNumber, null, text, null, null);
		}

		public void Send() {
			SmsManager sm = SmsManager.getDefault();
			String text = "hh";
			sm.sendTextMessage(IMissPhoneStateListener.RingingNumber, null, text, null, null);
		}

		public void run() {
			Send();
		}
	}
}
