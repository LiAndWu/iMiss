package edu.crabium.android;


import edu.crabium.android.plugin.SendSMS;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class IMissService extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		IMissPhoneStateListener myPhoneCallListener = new IMissPhoneStateListener();
		tm.listen(myPhoneCallListener,PhoneStateListener.LISTEN_CALL_STATE); 
		
		//Install functions
		myPhoneCallListener.Callback(IMissPhoneStateListener.CALLED, "SendSMS", new SendSMS());
	}
}
