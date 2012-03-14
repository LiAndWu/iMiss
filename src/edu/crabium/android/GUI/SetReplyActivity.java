package edu.crabium.android.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import edu.crabium.android.IMissActivity;
import edu.crabium.android.IMissData;
import edu.crabium.android.R;

public class SetReplyActivity extends Activity {
	LinearLayout	SetReplyLinearLayout, NewReplyLinearLayout;
	ListView		SetReplyListView;
	private static final String SetReplyColumn1 = "title";
	private static final String SetReplyColumn2 = "content";
	private static final int MENU_MEMBER = Menu.FIRST;
	private static final int MENU_ADD = Menu.FIRST + 1;
	private static final int MENU_DELETE = Menu.FIRST + 2;
	
	private Button BackButton;
	private SimpleAdapter adapter;
	List<Map<String,String>> SetReplyDisplay;
	public void onCreate(Bundle savedInstanceState) { 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_reply);
		
		SetReplyDisplay = new ArrayList<Map<String, String>>();
		SetReplyLinearLayout = (LinearLayout) findViewById(R.id.set_reply_linearlayout);
		SetReplyListView = (ListView) findViewById(R.id.set_reply_list_view);
		setContentView(SetReplyLinearLayout);
		
		getGroups(SetReplyDisplay);
		
		final String[] from = {SetReplyColumn1, SetReplyColumn2};
		int[] to = {android.R.id.text1, android.R.id.text2};
		adapter = new SimpleAdapter(this, SetReplyDisplay,android.R.layout.simple_list_item_2, from, to);
		SetReplyListView.setAdapter(adapter);
		SetReplyListView.setItemsCanFocus(true); 
		SetReplyListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); 

		//监听OnClick事件
		SetReplyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {	
				ListView listView = (ListView)parent;
				@SuppressWarnings("unchecked")
				HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
				Bundle bundle = new Bundle();
				bundle.putString("group_name", map.get(SetReplyColumn1));
				bundle.putString("message_body", map.get(SetReplyColumn2));
				Intent intent = new Intent(SetReplyActivity.this, EditReplyActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
				SetReplyActivity.this.finish();
			}
		});	

		SetReplyListView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {         
            @Override   
            public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) { 
            	menu.setHeaderTitle("  "); 
                menu.add(0, MENU_MEMBER, 0, "小组成员");  
                menu.add(0, MENU_ADD, 0, "添加组员");    
                menu.add(0, MENU_DELETE, 0, "删除小组");
            }   
        });     

		NewReplyLinearLayout = (LinearLayout) findViewById(R.id.new_reply_linearlayout);
		NewReplyLinearLayout.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("group_name", "");
				bundle.putString("message_body", "");
				Intent intent = new Intent(SetReplyActivity.this, EditReplyActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
				SetReplyActivity.this.finish();
			}
		});

		BackButton = (Button)findViewById(R.id.back_button);
		BackButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(SetReplyActivity.this, IMissActivity.class);
				startActivity(intent);
				SetReplyActivity.this.finish();
			}
		});
	}
	/**
	 * Define operations for menu of SetReplyListView;
	 * @param item ,the sub item of menu of SetReplyListView;
	 * Menu.First:小组成员
	 * Menu.First + 1：添加组员
	 * Menu.First + 2: 删除组员
	 */
    public boolean onContextItemSelected(MenuItem item) {
    	AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo)item.getMenuInfo(); 
    	
    	if (item.getItemId() == MENU_MEMBER) {
			Intent intent = new Intent(SetReplyActivity.this, SelectedGroupMemberActivity.class);
    		Map<String,String> map = (Map<String, String>)SetReplyListView.getItemAtPosition(menuInfo.position);
    		Bundle bundle = new Bundle();
			bundle.putString("group_name", map.get("title"));
			intent.putExtras(bundle);
			startActivity(intent);
			SetReplyActivity.this.finish();
			
    	} else if (item.getItemId() == MENU_ADD) {
			Intent intent = new Intent(SetReplyActivity.this, SelectLinkManActivity.class);
			Bundle bundle = new Bundle();
			@SuppressWarnings("unchecked")
    		Map<String,String> map = (Map<String, String>)SetReplyListView.getItemAtPosition(menuInfo.position);
			bundle.putString("group_name", map.get("title"));
			intent.putExtras(bundle);
			startActivity(intent);
			SetReplyActivity.this.finish();
			
    	} else if (item.getItemId() == MENU_DELETE) {
    		int pos = (int) SetReplyListView.getAdapter().getItemId(menuInfo.position);
			@SuppressWarnings("unchecked")
    		Map<String,String> map = (Map<String, String>)SetReplyListView.getItemAtPosition(pos);
    		IMissData.delGroup(map.get("title"));
    		
    		SetReplyActivity.this.finish();
			Intent intent = new Intent(SetReplyActivity.this, SetReplyActivity.class);
			startActivity(intent);
    	}
        return super.onContextItemSelected(item);   
    }  

    private void getGroups(List<Map<String,String>> to){
    	Map<String, String> map = IMissData.getGroups();
    	Set<String> keys = map.keySet();
    	
    	for(String key : keys){
        	Map<String, String> item = new HashMap<String, String>();
        	item.put(SetReplyColumn1, key);
        	item.put(SetReplyColumn2, map.get(key));
        	to.add(item);
    	}	
    }
    
    /** add a new group with specific message
     * 
     * @param title Group name
     * @param content The message you want to send to this group
     * @param to Destination to put the new entry
     */
    public void addGroup(String title, String content,List<Map<String,String>> to){
    	Map<String,String> item = new HashMap<String,String>();
    	item.put(SetReplyColumn1, title);
    	item.put(SetReplyColumn2, content);
    	to.add(item);
    	
    	String[] group = new String[]{title, content};
    	IMissData.addGroup(group);
    }
    
    //修改回复项
    public void MotifyNewValue(String title, String content, List<Map<String,String>> to){
    	Map<String,String> item1 = new HashMap<String,String>();
    	item1.put(SetReplyColumn1, title);
    	item1.put(SetReplyColumn2, content);
    	to.add(item1);	
    }
    
	//显示Toast  
	public void DisplayToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	} 
}