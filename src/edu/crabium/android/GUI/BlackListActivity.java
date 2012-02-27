package edu.crabium.android.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import edu.crabium.android.GlobalVariable;
import edu.crabium.android.IMissData;
import edu.crabium.android.MainActivity;
import edu.crabium.android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
	private List<Map<String,String>> BlackListDisplay;
	private SimpleAdapter adapter;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.black_list);
		 
		BlackListLinearLayout = (LinearLayout) findViewById(R.id.blacklist_linearlayout);
		BlackListListView = (ListView) findViewById(R.id.blacklist_listview);
		setContentView(BlackListLinearLayout);
		
		BlackListDisplay = new ArrayList<Map<String,String>>();
		BlackListDisplay = addValue();
		final String[] from = {BlackListColumn1, BlackListColumn2};
		int[] to = {android.R.id.text1, android.R.id.text2};
		adapter = new SimpleAdapter(this, BlackListDisplay,
				android.R.layout.simple_list_item_2, from,to);
		BlackListListView.setAdapter(adapter);
		BlackListListView.setItemsCanFocus(true); 
		BlackListListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); 
		
		//¼àÌýOnClickÊÂ¼þ
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
                menu.add(0, Menu.FIRST, 0, "É¾³ý");
            }   
        });  
		
		BackButton = (Button)findViewById(R.id.back_button);
		BackButton.setOnClickListener(new Button.OnClickListener() {
		public void onClick(View v) {
				Intent intent = new Intent(BlackListActivity.this, MainActivity.class);
				startActivity(intent);
				BlackListActivity.this.finish();
			}
		});
		
		NewBlackListItemLinearLayout = (LinearLayout) findViewById(R.id.new_blacklist_item_linearlayout);
		NewBlackListItemLinearLayout.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				GlobalVariable.TargetBlackListName = null;
				GlobalVariable.TargetBlackListNumber = null;
				Intent intent = new Intent(BlackListActivity.this, EditLinkManActivity.class);
				startActivity(intent);
				BlackListActivity.this.finish();
			}
		});	
	}
	
	public boolean onContextItemSelected(MenuItem item) {  
    	AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo)item.getMenuInfo();  
    	if (item.getItemId() == Menu.FIRST) {//É¾³ý
    		int pos = (int) BlackListListView.getAdapter().getItemId(menuInfo.position);
            BlackListDisplay.remove(pos);
            SaveListToDataBase();
    	} 
    	adapter.notifyDataSetChanged();  
        return super.onContextItemSelected(item);   
    }  
    
	//Ä¬ÈÏ»Ø¸´
    public List<Map<String, String>> addValue(){
    	List<Map<String, String>> value = new ArrayList<Map<String, String>>();
    	
    	List<Element> list = IMissData.ReadNodes("BlackList");
    	Iterator<Element> it = list.iterator();
    	
    	while(it.hasNext())
    	{
    		Element e = it.next();
    		
        	Map<String, String> item = new HashMap<String, String>();
        	item.put(BlackListColumn1, e.getChild("Name").getValue());
        	item.put(BlackListColumn2, e.getChild("Number").getValue());
        	value.add(item);
    	}
    	return value;
    }
    
    private boolean SaveListToDataBase()
    {
    	Iterator<Map<String,String>> it = BlackListDisplay.iterator();
    	List<Element> list = new ArrayList<Element>();
    	while(it.hasNext())
    	{
    		Map<String, String> item = it.next();
    		Element e = new Element("Item");
    		Element name = new Element("Name");
    		Element num  = new Element("Number");
    		name.setText(item.get(BlackListColumn1));
    		num.setText(item.get(BlackListColumn2));
    		e.addContent(name);
    		e.addContent(num);
    		list.add(e);
    	}
    	
    	return IMissData.WriteNodes("BlackList", list);
    }
}

