package edu.crabium.android;

import java.io.FileInputStream;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import android.telephony.SmsManager;
import android.util.Log;

public class IMissSendSMS
{
    public final static int IN_REST_TIME = 1;
    public final static int NORMAL = 0;
    
    public static void Send(String text, String RingingNumber)
    {
    	SmsManager sm = SmsManager.getDefault();
		sm.sendTextMessage(RingingNumber, null,text, null, null);
    }
    
    
    public static void Send(int status, String RingingNumber, String CallerName)
    {
		switch(status)
		{
			case IN_REST_TIME:
			{
			    	;
			}
			break;
			
			case NORMAL: 
			{
				String Owner;
				
				Owner = IMissData.ReadNode("Owner");
						
				Log.d("TAG", "Before comparion");
				if(CallerName == null)
				{
					Log.d("TAG","NO NAME");
					SmsManager sm = SmsManager.getDefault();
					sm.sendTextMessage(RingingNumber, null,"�ƺ���ղŸ�����"+ Owner + "����绰������û�б����������Բ�������һ������ɱ���Ժ��ٲ���"
							+"[����iMiss]", null, null);
				}
				else
				{
					Log.d("TAG","HAVE NAME");
					Log.d("TAG", CallerName);
					String text = CallerName + "����á��ƺ���ղŸ�"+ Owner + "����绰������û�б����������Բ�������һ������ɱ���Ժ��ٲ���"
							+"[����iMiss]";
					SmsManager sm = SmsManager.getDefault();
					sm.sendTextMessage(RingingNumber, null,text, null, null);
				}
				break;
			}
		}
    }
}
