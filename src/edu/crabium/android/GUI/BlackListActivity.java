package edu.crabium.android.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import edu.crabium.android.GlobalVariable;
import edu.crabium.android.IMissData;
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
	private Map<String,String> BlackListDisplay;
	private SimpleAdapter adapter;
	
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.black_list);
		 
		BlackListLinearLayout = (LinearLayout) findViewById(R.id.blacklist_linearlayout);
		BlackListListView = (ListView) findViewById(R.id.blacklist_listview);
		setContentView(BlackListLinearLayout);
		
		BlackListDisplay = new HashMap<String, String>();
		BlackListDisplay = IMissData.getBlackList();
		List<Map<String,String>> list = new ArrayList<Map<String, String>>();
		list.add(BlackListDisplay);
		final String[] FORM = {BlackListColumn1, BlackListColumn2};
		int[] to = {android.R.id.text1, android.R.id.text2};
		adapter = new SimpleAdapter(this, list,
				android.R.layout.simple_list_item_2, FORM, to);
		BlackListListView.setAdapter(adapter);
		BlackListListView.setItemsCanFocus(true); 
		BlackListListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); 
		
<<<<<<< HEAD
		//����OnClick�¼�
=======
>>>>>>> e3544c3cd0f09459d6f4ff157da8492a7fe70fa7
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
<<<<<<< HEAD
                menu.add(0, Menu.FIRST, 0, "ɾ��");
=======
                menu.add(0, Menu.FIRST, 0, "刪除");
>>>>>>> e3544c3cd0f09459d6f4ff157da8492a7fe70fa7
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
<<<<<<< HEAD
    	if (item.getItemId() == Menu.FIRST) {//ɾ��
=======
    	if (item.getItemId() == Menu.FIRST) {//刪除
>>>>>>> e3544c3cd0f09459d6f4ff157da8492a7fe70fa7
    		int pos = (int) BlackListListView.getAdapter().getItemId(menuInfo.position);
            BlackListDisplay.remove(pos);
    	} 
    	adapter.notifyDataSetChanged();  
        return super.onContextItemSelected(item);   
    }  
<<<<<<< HEAD
    
	//Ĭ�ϻظ�
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
=======
>>>>>>> e3544c3cd0f09459d6f4ff157da8492a7fe70fa7
}

