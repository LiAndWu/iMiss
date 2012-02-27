package edu.crabium.android;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class IMissService extends Service {
	private final static int CleanerMessage = 1;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		IMissPhoneStateListener myPhoneCallListener = new IMissPhoneStateListener(this);  
		tm.listen(myPhoneCallListener,PhoneStateListener.LISTEN_CALL_STATE); 
		
		new CallInfoCleanerThread().start();
	}

	@Override
	public void onDestroy() {
		//Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
		//Log.d(TAG, "onDestroy");
	}
	
	@Override
	public void onStart(Intent intent, int startid) {
	}
	
	public class CallInfoCleanerThread extends Thread {
	    	@Override
		public void run() {
	    	while (true) {
		    	try {
				Message msg = new Message();
				msg.what = CleanerMessage;
				CallInfoCleanerHandler.sendMessage(msg);
				Thread.sleep(1000*60*15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    	}//while
	    }//run
	}//Thread
	    
	private Handler CallInfoCleanerHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) { 
	    	super.handleMessage(msg); 
	    	switch(msg.what) {
	    	case CleanerMessage:
	    	{
	    		int cooldown = 10;
	    		cooldown = Integer.valueOf(IMissData.ReadNode("CoolDown"));
	    		
	    		Date date = new Date();
	    		long TimeNow = date.getTime();
	    		if(IMissPhoneStateListener.RecentCalled.size() > 0)
	    	    {
	    	    	Iterator<IMissPhoneStateListener.Caller> it = IMissPhoneStateListener.RecentCalled.iterator();
	    	    	while(it.hasNext())
	    	    	{
	    	    		IMissPhoneStateListener.Caller caller = it.next();
	    	    		if((TimeNow - caller.Time) >= 1000*60*cooldown)
	    	    			it.remove();
	    	    	}
	    	    }
	    	}
	    		break;
	    	default:
	    		break;
	    	}
	    }
	};
}