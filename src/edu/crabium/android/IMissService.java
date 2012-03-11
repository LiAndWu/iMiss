package edu.crabium.android;

import java.io.IOException;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class IMissService extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		IMissPhoneStateListener myPhoneCallListener = new IMissPhoneStateListener();
		tm.listen(myPhoneCallListener,PhoneStateListener.LISTEN_CALL_STATE); 
		
		//Install functions
		myPhoneCallListener.Callback(IMissPhoneStateListener.CALLED, "tag", new IMissPlugin().new SendSMS());
	}
}
