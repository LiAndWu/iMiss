package edu.crabium.android;

import java.util.ArrayList;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class IMissPhoneStateListener extends PhoneStateListener {
	
	public void callback() {
		;
	}
	
	public void Callback(int type, String tag, Runnable r) {
		switch (type) {
		case CALLING:
			callingPool.add(r);
			break;
		case CALLED:
			calledPool.add(r);
			break;
		default:
			break;
		}
	}

	public void onCallStateChanged(int state, String incomingNumber) {
		switch (state) {
		case TelephonyManager.CALL_STATE_IDLE:
			Log.d("iMiss V1.0", "Idle, number:" + incomingNumber);
			onIdle(incomingNumber);
			break;
		case TelephonyManager.CALL_STATE_OFFHOOK:
			Log.d("iMiss V1.0", "OffHook, number:" + incomingNumber);
			onOffHook();
			break;
		case TelephonyManager.CALL_STATE_RINGING:
			Log.d("iMiss V1.0", "Ringing, number:" + incomingNumber);
			onRinging(incomingNumber);
			break;
		default:
			break;
		}
		super.onCallStateChanged(state, incomingNumber);
	}

	public void onIdle(String incomingNumber) {
		if (PreviousState == TelephonyManager.CALL_STATE_RINGING) {
			if(incomingNumber != null && !incomingNumber.trim().equals("")){
				Log.d("iMiss V1.0", "OnRinging: Set RingingNumber to " + incomingNumber);
				RingingNumber = incomingNumber;
			}
			long Now = System.currentTimeMillis();
			long RingingTime = Now - TimeMark;
			RingingSec = RingingTime / 1000;

			for (Runnable r : calledPool)
				new Thread(r).start();
		}
	}

	public void onOffHook() {
		PreviousState = TelephonyManager.CALL_STATE_OFFHOOK;
	}

	public void onRinging(String incomingNumber) {
		Log.d("iMiss V1.0", "IncomingNumber: " + incomingNumber);
		PreviousState = TelephonyManager.CALL_STATE_RINGING;
		TimeMark = System.currentTimeMillis();
		if(incomingNumber != null && !incomingNumber.trim().equals("")){
			Log.d("iMiss V1.0", "OnRinging: Set RingingNumber to " + incomingNumber);
			RingingNumber = incomingNumber;
		}

		for (Runnable r : callingPool)
			new Thread(r).start();
	}

	private int PreviousState;
	private long TimeMark;
	private ArrayList<Runnable> callingPool = new ArrayList<Runnable>();
	private ArrayList<Runnable> calledPool = new ArrayList<Runnable>();

	public static String RingingNumber;
	public static long RingingSec;
	public final static int CALLING = 1;
	public final static int CALLED = 2;
}
