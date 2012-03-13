package edu.crabium.android.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.crabium.android.GlobalVariable;
import edu.crabium.android.IMissData;
import edu.crabium.android.IMissActivity;
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
	private static final String SetReplyColumn1 = "title";
	private static final String SetReplyColumn2 = "content";
	;
	private Button BackButton;
	SimpleAdapter adapter;

	//TODO 
	//String hello;
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_reply);

		SetReplyLinearLayout = (LinearLayout) findViewById(R.id.set_reply_linearlayout);
		SetReplyListView = (ListView) findViewById(R.id.set_reply_list_view);
		setContentView(SetReplyLinearLayout);
		
		List<Map<String,String>> SetReplyDisplay = new ArrayList<Map<String, String>>();
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
                menu.add(0, Menu.FIRST, 0, "选择联系人");   
                menu.add(0, Menu.FIRST + 1, 0, "删除");
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

    public boolean onContextItemSelected(MenuItem item) {   
    	if (item.getItemId() == Menu.FIRST) {//选择联系人
			Intent intent = new Intent(SetReplyActivity.this, SelectLinkManActivity.class);
			startActivity(intent);
			SetReplyActivity.this.finish();
    	} else if (item.getItemId() == Menu.FIRST + 1) {//删除
    	
    	}
        return super.onContextItemSelected(item);   
    }  

	/** get group information
	 * 
	 * @param to the destination to put group information
	 */
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
    
    //删除回复项
    public void DeleteValue(String title, String content,List<Map<String,String>> to){
    	to.remove(title);
    }
    
	//显示Toast  
	public void DisplayToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	} 
}