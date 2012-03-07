package edu.crabium.android.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.crabium.android.GlobalVariable;
import edu.crabium.android.IMissData;
import edu.crabium.android.MainActivity;
import edu.crabium.android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.view.ContextMenu;   
import android.view.Menu;
import android.view.MenuItem;   
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;   
import android.view.View.OnCreateContextMenuListener;   

public class SetReplyActivity extends Activity {
	LinearLayout	SetReplyLinearLayout, NewReplyLinearLayout;
	ListView		SetReplyListView;
	private final static String SetReplyColumn1 = "title";
	private final static String SetReplyColumn2 = "content";
	List<Map<String,String>> SetReplyDisplay;
	private Button BackButton;
	SimpleAdapter adapter;
	
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_reply);
		
		SetReplyLinearLayout = (LinearLayout) findViewById(R.id.set_reply_linearlayout);
		SetReplyListView = (ListView) findViewById(R.id.set_reply_list_view);
		setContentView(SetReplyLinearLayout);

		
		SetReplyDisplay = new ArrayList<Map<String,String>>();
		SetReplyDisplay = addValue();
		final String[] from = {SetReplyColumn1,SetReplyColumn2};
		int[] to = {android.R.id.text1, android.R.id.text2};
		adapter = new SimpleAdapter(this, SetReplyDisplay,android.R.layout.simple_list_item_2, from,to);
		SetReplyListView.setAdapter(adapter);
		SetReplyListView.setItemsCanFocus(true); 
		SetReplyListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); 

		//监听OnClick事件
		SetReplyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {	
				ListView listView = (ListView)parent;
				@SuppressWarnings("unchecked")
				HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
				GlobalVariable.TargetReplyTitle = map.get(SetReplyColumn1);
				GlobalVariable.TargetReplyContent = map.get(SetReplyColumn2);
				
				Intent intent = new Intent(SetReplyActivity.this, EditReplyActivity.class);
				startActivity(intent);
				SetReplyActivity.this.finish();
			}
		});	
		
		SetReplyListView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {         
            @Override   
            public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) { 
            	menu.setHeaderTitle("  "); 
                menu.add(0, Menu.FIRST, 0, "选择联系人");   
                menu.add(0, Menu.FIRST + 1, 0, "删除");
            }   
        });     
		 
		NewReplyLinearLayout = (LinearLayout) findViewById(R.id.new_reply_linearlayout);
		NewReplyLinearLayout.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				GlobalVariable.TargetReplyTitle = null;
				GlobalVariable.TargetReplyContent = null;
				Intent intent = new Intent(SetReplyActivity.this, EditReplyActivity.class);
				startActivity(intent);
				SetReplyActivity.this.finish();
			}
		});
		
		BackButton = (Button)findViewById(R.id.back_button);
		BackButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(SetReplyActivity.this, MainActivity.class);
				startActivity(intent);
				SetReplyActivity.this.finish();
			}
		});
	}
	
    public boolean onContextItemSelected(MenuItem item) {   
    	if (item.getItemId() == Menu.FIRST) {//选择联系人
			Intent intent = new Intent(SetReplyActivity.this, SelectLinkManActivity.class);
			startActivity(intent);
			SetReplyActivity.this.finish();
    	} else if (item.getItemId() == Menu.FIRST + 1) {//删除

    	}
        return super.onContextItemSelected(item);   
    }  
    
	//显示Toast  
	public void DisplayToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	} 
	
	//设置回复前部分
	public String ReplyForePart() {
		String ReplyForePart;
		if (IMissData.ReadNode("ShowOwnerNameToStranger").equals("true")) {
			ReplyForePart = "嗨，我是" + IMissData.ReadNode("Owner");
		} else {
			ReplyForePart = "你好，机主";
		}
		return ReplyForePart;
	}
	
	//默认回复
    public List<Map<String, String>> addValue(){
    	List<Map<String, String>> value = new ArrayList<Map<String, String>>();
    	
    	Map<String, String> item1 = new HashMap<String, String>();
    	item1.put(SetReplyColumn1, "工作");
    	item1.put(SetReplyColumn2, ReplyForePart() + ",现在有事不能接电话，稍后回复。");
    	value.add(item1);
    	
    	Map<String, String> item2 = new HashMap<String, String>();
    	item2.put(SetReplyColumn1, "家人");
    	item2.put(SetReplyColumn2, ReplyForePart() + ",现在有点事，等下我打回去。");
    	value.add(item2);
    	
    	Map<String, String> item3 = new HashMap<String, String>();
    	item3.put(SetReplyColumn1, "朋友");
    	item3.put(SetReplyColumn2, ReplyForePart() + ",现在正在忙乱七八糟一堆事，等下我会给你打过去。");
    	value.add(item3);  	
    	return value;
    }
    
    //用来添加新的回复项
    public List<Map<String,String>> addNewValue(String title, String content){
    	List<Map<String,String>> value = new ArrayList<Map<String,String>>();  	
    	Map<String,String> item1 = new HashMap<String,String>();
    	item1.put(SetReplyColumn1, title);
    	item1.put(SetReplyColumn2, content);
    	value.add(item1);	
    	return value;
    }
    
    //修改回复项
    public List<Map<String,String>> MotifyNewValue(String title, String content){
    	List<Map<String,String>> value = new ArrayList<Map<String,String>>();  	
    	Map<String,String> item1 = new HashMap<String,String>();
    	item1.put(SetReplyColumn1, title);
    	item1.put(SetReplyColumn2, content);
    	value.add(item1);	
    	return value;
    }
    
    //删除回复项
    public List<Map<String,String>> DeleteValue(String title, String content){
    	List<Map<String,String>> value = new ArrayList<Map<String,String>>();  	
    	Map<String,String> item1 = new HashMap<String,String>();
    	item1.remove(GlobalVariable.TargetReplyPosition);
    	return value;
    }
}
