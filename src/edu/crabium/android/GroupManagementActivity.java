package edu.crabium.android;

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
import android.view.MenuInflater;
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
import edu.crabium.android.R;

public class GroupManagementActivity extends Activity {
	LinearLayout	SetReplyLinearLayout, NewReplyLinearLayout;
	ListView		SetReplyListView;
	private static final String SetReplyColumn1 = "title";
	private static final String SetReplyColumn2 = "content";
	
	private Button BackButton;
	private SimpleAdapter adapter;
	
	SettingProvider sp = SettingProvider.getInstance();
	List<Map<String,String>> SetReplyDisplay;
	public void onCreate(Bundle savedInstanceState) { 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.groups_set_reply);
		
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

		SetReplyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {	
				ListView listView = (ListView)parent;
				@SuppressWarnings("unchecked")
				HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
				Bundle bundle = new Bundle();
				bundle.putString("group_name", map.get(SetReplyColumn1));
				bundle.putString("message_body", map.get(SetReplyColumn2));
				Intent intent = new Intent(GroupManagementActivity.this, EditReplyActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});	

		SetReplyListView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {         
            @Override   
            public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) { 
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.group_management_menu, menu);
            }   
        });     

		NewReplyLinearLayout = (LinearLayout) findViewById(R.id.new_reply_linearlayout);
		NewReplyLinearLayout.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("group_name", "");
				bundle.putString("message_body", "");
				Intent intent = new Intent(GroupManagementActivity.this, EditReplyActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		BackButton = (Button)findViewById(R.id.back_button);
		BackButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(GroupManagementActivity.this, IMissActivity.class);
				startActivity(intent);
			}
		});
	}
	
    public boolean onContextItemSelected(MenuItem item) {
    	AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo)item.getMenuInfo(); 
    	
    	if (item.getItemId() == R.id.gm_view_member) {
			Intent intent = new Intent(GroupManagementActivity.this, GroupMemberManagementActivity.class);
    		@SuppressWarnings("unchecked")
			Map<String,String> map = (Map<String, String>)SetReplyListView.getItemAtPosition(menuInfo.position);
    		Bundle bundle = new Bundle();
			bundle.putString("group_name", map.get("title"));
			intent.putExtras(bundle);
			startActivity(intent);
			
    	} else if (item.getItemId() == R.id.gm_add_member) {
			Intent intent = new Intent(GroupManagementActivity.this, SelectContactsActivity.class);
			Bundle bundle = new Bundle();
			@SuppressWarnings("unchecked")
    		Map<String,String> map = (Map<String, String>)SetReplyListView.getItemAtPosition(menuInfo.position);
			bundle.putString("group_name", map.get("title"));
			intent.putExtras(bundle);
			startActivity(intent);
			
    	} else if (item.getItemId() == R.id.gm_delete_group) {
    		int pos = (int) SetReplyListView.getAdapter().getItemId(menuInfo.position);
			@SuppressWarnings("unchecked")
    		Map<String,String> map = (Map<String, String>)SetReplyListView.getItemAtPosition(pos);
    		sp.deleteGroup(map.get("title"));
    		
    		getGroups(SetReplyDisplay);
    		adapter.notifyDataSetChanged();
    	}
        return super.onContextItemSelected(item);   
    }  

    private void getGroups(List<Map<String,String>> to){
    	to.clear();
    	Map<String, String> map = sp.getGroups();
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
    	
    	sp.addGroup(title, content);
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