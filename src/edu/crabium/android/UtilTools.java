package edu.crabium.android;
import java.util.ArrayList;
import java.util.HashMap;

import edu.crabium.android.R;
import android.app.Activity;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class UtilTools {

	public static void showList(Activity activity, ArrayList<String[]> my_list)
	{
		// �б���  ���
		String [] list_name = null;
		// �б��� 
		String [] list_desc = null;
		String [][] my_arr = null;
		// ���� View �б�
		SimpleAdapter listItemAdapter;
		
		//��Layout�����ListView
        ListView listV = (ListView) activity.findViewById(R.id.select_linkman_listView);
		int list_len =0;
		my_arr = my_list.toArray(new String[][]{});
		ArrayList<String> nameList = new ArrayList<String>();
		ArrayList<String> descList = new ArrayList<String>();
		list_len = my_arr == null?0:my_arr.length;
		for (int i = 0; i < list_len; i++)
		{
			if(my_arr[i]!= null){
				nameList.add(my_arr[i][0]);
				descList.add(my_arr[i][1]);
			}
		}
		list_name = nameList.toArray(new String[]{});
		list_desc = descList.toArray(new String[]{});
				
		 ArrayList<HashMap<String, Object>> list = titleAdesc(list_name, list_desc, null);
		  
		 //�����������Item�Ͷ�̬�����Ӧ��Ԫ��
	     listItemAdapter = new SimpleAdapter(activity,list,//���Դ 
	            R.layout.dispatch_select_user_item,//ListItem��XMLʵ��
	            //��̬������ImageItem��Ӧ������        
	            new String[] {"ItemTitle", "ItemText"}, 
	            //ImageIt��XML�ļ������һ��ImageView,����TextView ID
	            new int[] {R.id.dispatch_item_select_user_name, R.id.dispatch_item_select_user_phone}
	        );
		
	    //��Ӳ�����ʾ
	    listV.setAdapter(listItemAdapter);
	}
	
	public static ArrayList<HashMap<String, Object>> titleAdesc(String [] str,String []desc,String [] params)
	{
		if(params == null)
		{
			params = new String[]{"ItemTitle","ItemText"};
		}
		 ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>(); 
		 
		 if(str !=null && desc!=null && str.length == desc.length)
		 {
			 for(int i=0;i<str.length;i++)
	        {
			    HashMap<String, Object> map = new HashMap<String, Object>();
	        	map.put(params[0], str[i]);
	        	map.put(params[1], desc[i]);
	        	list.add(map);
	        }
		 }
		 else {
	         HashMap<String, Object> map = new HashMap<String, Object>();
			 map.put(params[0], "��ʱû�����");
	         map.put(params[1], "");
	         list.add(map);
		}
		 return list;
	}
}
