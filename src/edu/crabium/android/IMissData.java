package edu.crabium.android;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;


import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

public class IMissData
{
	static Activity activity;
	
	public static void Initiate(Activity activity)
	{
		IMissData.activity = activity;
	}

	public static List<Element> ReadNodes(String NodeName)
	{
        try 
        {
			SAXBuilder saxBuilder = new SAXBuilder();
        	FileInputStream fileInputStream  = new FileInputStream("/data/data/edu.crabium.android/files/iMiss.xml");
        	Document document = saxBuilder.build(fileInputStream);
        	fileInputStream.close();
        	Element iMiss = document.getRootElement();
				
        	Element Node = iMiss.getChild(NodeName);
        	return Node.getChildren();
        } 
        catch (IOException e1)
        {
        	e1.printStackTrace();
		} 
        catch (JDOMException e)
		{
			e.printStackTrace();
		}
        
        return null;
	}
	
	public static List<Element> GetRestTime()
	{
		return ReadNodes("RestTime");
	}
	
	public static boolean WriteNodes(String NodeName, List<Element> Children)
	{
        try 
        {
        	SAXBuilder saxBuilder = new SAXBuilder();
        	FileInputStream fileInputStream  = new FileInputStream("/data/data/edu.crabium.android/files/iMiss.xml");
			Document document = saxBuilder.build(fileInputStream);
			fileInputStream.close();

        	Element iMiss = document.getRootElement();
        	iMiss.removeChild(NodeName);
        	
        	Element Node = new Element(NodeName);
        	Iterator<Element> it = Children.iterator();
        	while(it.hasNext())
        	{
        		Node.addContent((Element)it.next());
        	}
        	iMiss.addContent(Node);
			
			XMLOutputter out = new XMLOutputter();
			FileOutputStream fileOutputStream = new FileOutputStream("/data/data/edu.crabium.android/files/iMiss.xml");
			out.output(document,fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			
			return true;
				
		} catch(IOException e)
		{
			e.printStackTrace();
			return false;
		} catch (JDOMException e) {
			e.printStackTrace();
			return false;
		}
        
	}
	
	public static boolean WriteNode(String NodeName, String NodeValue)
	{
        try 
        {
        	SAXBuilder saxBuilder = new SAXBuilder();
        	FileInputStream fileInputStream  = new FileInputStream("/data/data/edu.crabium.android/files/iMiss.xml");
			Document document = saxBuilder.build(fileInputStream);
			fileInputStream.close();

        	Element iMiss = document.getRootElement();
        	Element Node = iMiss.getChild(NodeName);
			Node.setText(NodeValue);
			
			XMLOutputter out = new XMLOutputter();
			FileOutputStream fileOutputStream = new FileOutputStream("/data/data/edu.crabium.android/files/iMiss.xml");
			out.output(document,fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			
			return true;
				
		} catch(IOException e)
		{
			e.printStackTrace();
			return false;
		} catch (JDOMException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean InsertChild(String NodeName, Element Child)
	{
		 try 
	        {
	        	SAXBuilder saxBuilder = new SAXBuilder();
	        	FileInputStream fileInputStream  = new FileInputStream("/data/data/edu.crabium.android/files/iMiss.xml");
				Document document = saxBuilder.build(fileInputStream);
				fileInputStream.close();

	        	Element iMiss = document.getRootElement();
	        	Element Node = iMiss.getChild(NodeName);
				Node.addContent(Child);
				XMLOutputter out = new XMLOutputter();
				FileOutputStream fileOutputStream = new FileOutputStream("/data/data/edu.crabium.android/files/iMiss.xml");
				out.output(document,fileOutputStream);
				fileOutputStream.flush();
				fileOutputStream.close();
				
				return true;
					
			} catch(IOException e)
			{
				e.printStackTrace();
				return false;
			} catch (JDOMException e) {
				e.printStackTrace();
				return false;
			}
	}
	public static String ReadNode(String NodeName)
	{
		Log.d("TAG","Reading: " +  NodeName);
        try 
        {
			SAXBuilder saxBuilder = new SAXBuilder();
        	FileInputStream fileInputStream  = new FileInputStream("/data/data/edu.crabium.android/files/iMiss.xml");
        	Document document = saxBuilder.build(fileInputStream);
        	fileInputStream.close();
        	
        	Element iMiss = document.getRootElement();
        	Element Node = iMiss.getChild(NodeName);
        	Log.d("TAG","Got "+NodeName+": " +Node.getValue());
        	return Node.getValue();
        } 
        catch (IOException e1)
        {
        	e1.printStackTrace();
		} 
        catch (JDOMException e)
		{
			e.printStackTrace();
		}
        
        return "0";
	}
	
	public static boolean ChangeGroupInformation(String GroupIdentifier, String GroupName, String GroupText)
	{
		try 
        {
        	SAXBuilder saxBuilder = new SAXBuilder();
        	FileInputStream fileInputStream  = new FileInputStream("/data/data/edu.crabium.android/files/iMiss.xml");
			Document document = saxBuilder.build(fileInputStream);
			fileInputStream.close();

        	Element iMiss = document.getRootElement();
        	Element Node = iMiss.getChild("Groups");
        	Element Group = Node.getChild(GroupIdentifier);
        	
			Group.getChild("Name").setText(GroupName);
			Group.getChild("Text").setText(GroupText);
			
			XMLOutputter out = new XMLOutputter();
			FileOutputStream fileOutputStream = new FileOutputStream("/data/data/edu.crabium.android/files/iMiss.xml");
			out.output(document,fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			
			return true;
		} catch(IOException e)
		{
			e.printStackTrace();
			return false;
		} catch (JDOMException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static List<Element> GetGroups()
	{
		List<Element> list = new ArrayList<Element>();
		List<Element> list_tmp = new ArrayList<Element>();
		
		list_tmp = ReadNodes("Groups");
		
		Iterator<Element> it = list_tmp.iterator();
		while(it.hasNext())
		{
			Element e = it.next();
			if(!e.getName().equals("Contacts"))
				list.add((Element)it.next());
		}
		
		return list;
	}
	
	public static String GetOwnerName()
	{
		return ReadNode("Owner");
	}
	
	public static boolean SetOwnerName(String Name)
	{
		return WriteNode("Owner", Name);
	}
	
	public static void CheckXMLExistence()
	{
        try 
        {
			FileInputStream fileInputStream = new FileInputStream("/data/data/edu.crabium.android/files/iMiss.xml");
			fileInputStream.close();
		} 
        catch (FileNotFoundException e) 
		{
        	AssetManager assets = activity.getAssets();
        	try 
        	{
				FileOutputStream fileOutputStream =activity.openFileOutput("iMiss.xml",Context.MODE_PRIVATE);
				InputStream fileInputStream = assets.open("iMiss.xml");

				byte[] buffer = new byte[1024];
				int length = -1;
				while((length = fileInputStream.read(buffer)) != -1 )
				{
				    fileOutputStream.write(buffer, 0, length);
				}

				fileOutputStream.flush();
				fileOutputStream.close();
			} 
        	catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}    			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
