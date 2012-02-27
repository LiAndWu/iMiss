package edu.crabium.android;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;

import com.android.internal.telephony.ITelephony;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class IMissPhoneStateListener extends PhoneStateListener{ 
	
	class Caller
	{
	    String Number;
	    long Time;
	    public Caller(String n, long t)
	    {
	    	Number = n;
	    	Time = t;
	    }
	}
	
	public static List<Caller> RecentCalled  = new ArrayList<Caller>();
	private static int PreviousState = TelephonyManager.CALL_STATE_IDLE;
	private static long TimeMark = 0;
	private static String RingingNumber;
	private static long WaitingTime = 5;
	private static Context context;
	int TimeNow;
	
	public IMissPhoneStateListener(IMissService iMissService)
	{
		context = iMissService.getApplicationContext();// TODO Auto-generated constructor stub
	}

	public void onIdle()
	{
		if(PreviousState == TelephonyManager.CALL_STATE_RINGING)
		{
        	WaitingTime = Integer.valueOf(IMissData.ReadNode("WaitingTime"));
	       
			long Now = System.currentTimeMillis();
			long RingingTime = Now - TimeMark;
			
			if(RingingTime/1000 >= WaitingTime)
			{
				Boolean AlreadyCalled = false;
				Iterator<Caller> it = RecentCalled.iterator();
				
				if(RecentCalled.size() > 0)
				{
					while(it.hasNext())
					{
						Caller caller = it.next();
						if(caller.Number.equals(RingingNumber))
						{
							AlreadyCalled = true;
							break;
						}
					}
				}
				
				if(AlreadyCalled == false)
				{
		    		Date date = new Date();
					RecentCalled.add(new Caller(RingingNumber,date.getTime()));
					
					Boolean Sent = false;
					Boolean InExcludeList = false;
					
					if(IMissData.ReadNode("UseExcludeList").equals("true"))
					{
						List<Element> Items = IMissData.ReadNodes("ExcludeList");
						Iterator<Element> item_it = Items.iterator();
						if(Items.size()>0)
						{
							while(item_it.hasNext())
							{
								Element item = item_it.next();
								if(item.getChild("Number").getValue().equals(RingingNumber))
								{
									InExcludeList = true;
									break;
								}
							}
						}
					}
					
					if(InExcludeList == false)
					{
						List<Element> Persons = IMissData.ReadNodes("SpecificPersons");
						Iterator<Element> person_it = Persons.iterator();
						if(Persons.size()>0)
						{
							while(person_it.hasNext())
							{
								Element person = person_it.next();
								if(person.getChild("Number").getValue().equals(RingingNumber))
								{
									IMissSendSMS.Send(person.getChild("Text").getValue(), RingingNumber);
									Sent = true;
									break;
								}
							}
						}
					}
					else
					{
						Sent = false;
					}
					
					Sent = false;
					if(Sent == false)
					{
						String CallerName = null;
						
						//Thank you, Steven Van Bael
						//http://www.vbsteven.be/blog/android-getting-the-contact-name-of-a-phone-number/#
						String[] projection = new String[] {Phone.DISPLAY_NAME, Phone.NUMBER };
						Uri contactUri = Uri.withAppendedPath(Phone.CONTENT_FILTER_URI, Uri.encode(RingingNumber));
						Cursor c = context.getContentResolver().query(contactUri, projection, null,null, null);
						if (c.moveToFirst()) 
						{
							CallerName = c.getString(c.getColumnIndex(Contacts.DISPLAY_NAME));
						}
						//Log.d("TAG",CallerName);
						IMissSendSMS.Send(IMissSendSMS.NORMAL, RingingNumber, CallerName);
					}
				}
			}
		}
	}
	public void onRinging(String incomingNumber)
	{
		PreviousState = TelephonyManager.CALL_STATE_RINGING;
		TimeMark = System.currentTimeMillis();
		RingingNumber = incomingNumber;
		
		int size = IMissData.ReadNodes("RestTime").size();
		int[][] intervals = new int[size][2];
		
		int count =0;
		Iterator<Element> it = IMissData.ReadNodes("RestTime").iterator();
		while(it.hasNext())
		{
		    Element element = (Element) it.next();
		    intervals[count][0] = Integer.valueOf(element.getChild("Start").getValue());
		    intervals[count][1] = Integer.valueOf(element.getChild("End").getValue());
		    
		    count++;
		}

		Boolean InRestTime = false;
		Calendar c = Calendar.getInstance();
		TimeNow = (c.get(Calendar.HOUR_OF_DAY))*60 + c.get(Calendar.MINUTE);
		
		for(count = 0; count < size; count++)
		{
		    if(TimeNow >= intervals[count][0] && TimeNow <= intervals[count][1])
			InRestTime = true;
		}
		
		if(InRestTime)
		{
				Method method;
				try 
				{
					method = Class.forName("android.os.ServiceManager").getMethod("getService",String.class);
					IBinder binder = (IBinder)method.invoke(null, new Object[]{Context.TELEPHONY_SERVICE});
					ITelephony telephony = ITelephony.Stub.asInterface(binder);
					telephony.endCall();
					
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
		}
	}
	
	public void onOffHook()
	{
		PreviousState = TelephonyManager.CALL_STATE_OFFHOOK;
	}
	
	public void onCallStateChanged(int state, String incomingNumber) 
	{
		switch (state) 
		{   
			case TelephonyManager.CALL_STATE_IDLE :
			{
				onIdle();
			}
				break;   
			
			case TelephonyManager.CALL_STATE_OFFHOOK :
			{
				onOffHook();
			}
				break;  
			
			case TelephonyManager.CALL_STATE_RINGING :
			{
				onRinging(incomingNumber);
			}
				break;   
			default :   
				break;   
		}   
		super.onCallStateChanged(state, incomingNumber);   
	}  
}   
