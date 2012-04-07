package edu.crabium.android.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.crabium.android.SettingProvider;
import edu.crabium.android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.AdapterContextMenuInfo;
;

public class GroupMemberManagementActivity extends Activity {
	LinearLayout	SelectedGroupMemberLinearLayout;
    private ListView SelectedGroupMemberListView;
    private Button BackButton;
    
    private static String group_name;
	private static final String SelectedGroupMemberColumn1 = "title";
	private static final String SelectedGroupMemberColumn2 = "content";
	private SimpleAdapter adapter;
	List<Map<String,String>> SelectedGroupMemberDisplay = new ArrayList<Map<String, String>>();
	
	SettingProvider sp = SettingProvider.getInstance();
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_group_member);
        
        Bundle bundle = this.getIntent().getExtras();
        group_name = bundle.getString("group_name");
        SelectedGroupMemberLinearLayout = (LinearLayout) findViewById(R.id.selected_group_member_linearlayout);
        SelectedGroupMemberListView = (ListView) findViewById(R.id.selected_group_member_listView);
		setContentView(SelectedGroupMemberLinearLayout);
        
		getGroups(SelectedGroupMemberDisplay);
		
		final String[] from = {SelectedGroupMemberColumn1, SelectedGroupMemberColumn2};
		int[] to = {android.R.id.text1, android.R.id.text2};
		adapter = new SimpleAdapter(this, SelectedGroupMemberDisplay,android.R.layout.simple_list_item_2, from, to);
		SelectedGroupMemberListView.setAdapter(adapter);
		SelectedGroupMemberListView.setItemsCanFocus(true); 
		SelectedGroupMemberListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); 

		
		SelectedGroupMemberListView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {         
            @Override   
            public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) { 
            	MenuInflater inflater = getMenuInflater();
            	inflater.inflate(R.menu.group_member_management_menu, menu);
            }   
        });  
		
        BackButton = (Button)findViewById(R.id.back_button);
        BackButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(GroupMemberManagementActivity.this, GroupManagementActivity.class);
                startActivity(intent);
            }
        });	
    }

    public boolean onContextItemSelected(MenuItem item) {
    	AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo)item.getMenuInfo(); 
    
    	if (item.getItemId() == R.id.gmm_delete_member) {
    		Map<String, String> pair = SelectedGroupMemberDisplay.get(menuInfo.position);
    		sp.deletePersonFromGroup(pair.get(SelectedGroupMemberColumn1),pair.get(SelectedGroupMemberColumn2), group_name);
    		getGroups(SelectedGroupMemberDisplay);
    		adapter.notifyDataSetChanged();
    	} 
        return super.onContextItemSelected(item);   
    } 
    
    private void getGroups(List<Map<String,String>> to){
    	String[][] tuple = sp.getPersonsFromGroup(group_name);
    	to.clear();
    	for(String[] key : tuple){
        	Map<String, String> item = new HashMap<String, String>();
        	item.put(SelectedGroupMemberColumn1, key[0]);
        	item.put(SelectedGroupMemberColumn2, key[1]);
        	to.add(item);
    	}	
    }
}

