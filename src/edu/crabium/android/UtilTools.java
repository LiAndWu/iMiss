package edu.crabium.android;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class UtilTools {

	public static void showList(Activity activity, ArrayList<String[]> my_list)
	{
		// 列表中  名称
		String [] list_name = null;
		// 列表中 
		String [] list_desc = null;
		String [][] my_arr = null;
		// 创建 View 列表
		SimpleAdapter listItemAdapter;
		
		//绑定Layout里面的ListView
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
		  
		 //生成适配器的Item和动态数组对应的元素
	     listItemAdapter = new SimpleAdapter(activity,list,//数据源 
	            R.layout.dispatch_select_user_item,//ListItem的XML实现
	            //动态数组与ImageItem对应的子项        
	            new String[] {"ItemTitle", "ItemText"}, 
	            //ImageIt的XML文件里面的一个ImageView,两个TextView ID
	            new int[] {R.id.dispatch_item_select_user_name, R.id.dispatch_item_select_user_phone}
	        );
		
	    //添加并且显示
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
			 map.put(params[0], "暂时没有数据");
	         map.put(params[1], "");
	         list.add(map);
		}
		 return list;
	}
}
