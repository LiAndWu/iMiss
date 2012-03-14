package edu.crabium.android.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.crabium.android.GlobalVariable;
import edu.crabium.android.IMissActivity;
import edu.crabium.android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class BlackListActivity extends Activity {
	private LinearLayout BlackListLinearLayout, NewBlackListItemLinearLayout;
	private ListView BlackListListView;
	private Button BackButton;
	private final static String BlackListColumn1 = "name";
	private final static String BlackListColumn2 = "number";
	private final static int MENU_DELETE = Menu.FIRST;
	
	private List<Map<String,String>> BlackListDisplay;
	private SimpleAdapter adapter;
	
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.black_list);
		 
		BlackListLinearLayout = (LinearLayout) findViewById(R.id.blacklist_linearlayout);
		BlackListListView = (ListView) findViewById(R.id.blacklist_listview);
		setContentView(BlackListLinearLayout);
		

	    BlackListDisplay = new ArrayList<Map<String,String>>();
	     final String[] from = {BlackListColumn1, BlackListColumn2};
		int[] to = {android.R.id.text1, android.R.id.text2};
		adapter = new SimpleAdapter(this, BlackListDisplay,
				android.R.layout.simple_list_item_2, from, to);

		BlackListListView.setAdapter(adapter);
		BlackListListView.setItemsCanFocus(true); 
		BlackListListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); 
		
		BlackListListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {	
				Log.d("TAG","Position:" + String.valueOf(position));
				ListView listView = (ListView)parent;
				@SuppressWarnings("unchecked")
				HashMap<String, String> map = 
					(HashMap<String, String>) listView.getItemAtPosition(position);
				GlobalVariable.TargetBlackListName = map.get(BlackListColumn1);
				GlobalVariable.TargetBlackListNumber = map.get(BlackListColumn2);
				
				Intent intent = new Intent(BlackListActivity.this, EditLinkManActivity.class);
				startActivity(intent);
				BlackListActivity.this.finish();	
			}
		});	
		
		BlackListListView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {         
            @Override   
            public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) { 
            	menu.setHeaderTitle("  "); 
                menu.add(0, MENU_DELETE, 0, "删除");
            }   
        });  
		
		BackButton = (Button)findViewById(R.id.back_button);
		BackButton.setOnClickListener(new Button.OnClickListener() {
		public void onClick(View v) {
				Intent intent = new Intent(BlackListActivity.this, IMissActivity.class);
				startActivity(intent);
				BlackListActivity.this.finish();
			}
		});
		
		NewBlackListItemLinearLayout = (LinearLayout) findViewById(R.id.new_blacklist_item_button);
		NewBlackListItemLinearLayout.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//添加最近通话联系人，…………
				GlobalVariable.TargetBlackListName = null;
				GlobalVariable.TargetBlackListNumber = null;
				
				Intent intent = new Intent(BlackListActivity.this, RecentCallActivity.class);
				startActivity(intent);
				BlackListActivity.this.finish();
			}
		});	
	}
	
	/**
	 * Menu.FIRST:刪除
	 */
	public boolean onContextItemSelected(MenuItem item) {  
    	AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo)item.getMenuInfo(); 
    	if (item.getItemId() == MENU_DELETE) {
    		int pos = (int) BlackListListView.getAdapter().getItemId(menuInfo.position);
            BlackListDisplay.remove(pos);
    	}
    	adapter.notifyDataSetChanged();  
        return super.onContextItemSelected(item);   
    }  

}

