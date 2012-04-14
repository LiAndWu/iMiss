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
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class RefuseSlotActivity extends Activity {
	private LinearLayout	RefuseSlotLinearLayout, NewRefuseSlotLinearLayout;
	private ListView		RefuseSlotListView;
	private String Column_1 = "title";
	private String Column_2 = "content";
	private List<Map<String,String>> display;
	private Button BackButton;
	public String TargetRefuseSlot = GlobalVariable.TargetStartTimeHour + " : " + GlobalVariable.TargetStartTimeMins + " ~ "
			+ GlobalVariable.TargetEndTimeHour + " : " + GlobalVariable.TargetEndTimeMins;
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.refuse_slot);
		
		RefuseSlotLinearLayout = (LinearLayout) findViewById(R.id.refuse_slot_linearlayout);
		RefuseSlotListView = (ListView) findViewById(R.id.refuse_slot_listview);
		setContentView(RefuseSlotLinearLayout);
	
		display = new ArrayList<Map<String,String>>();
		display = addValue();
		final String[] from = {Column_1,Column_2};
		int[] to = {android.R.id.text1, android.R.id.text2};
		SimpleAdapter adapter = new SimpleAdapter(this, display,android.R.layout.simple_list_item_2, from,to);
		RefuseSlotListView.setAdapter(adapter);
		RefuseSlotListView.setItemsCanFocus(true); 
		RefuseSlotListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); 

		RefuseSlotListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {	
				ListView listView = (ListView)parent;
				@SuppressWarnings("unchecked")
				HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
				
				Intent intent = new Intent(RefuseSlotActivity.this, SetRefuseSlotTimeActivity.class);
				startActivity(intent);
				RefuseSlotActivity.this.finish();
			}
		});	
		 
		NewRefuseSlotLinearLayout = (LinearLayout) findViewById(R.id.new_refuse_slot_linearlayout);
		NewRefuseSlotLinearLayout.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				GlobalVariable.TargetStartTimeHour  = 0;
				GlobalVariable.TargetStartTimeMins = 0;
				GlobalVariable.TargetEndTimeHour  = 0;
				GlobalVariable.TargetEndTimeMins = 0;
				Intent intent = new Intent(RefuseSlotActivity.this, SetRefuseSlotTimeActivity.class);
				startActivity(intent);
				RefuseSlotActivity.this.finish();
			}
		});
		
		BackButton = (Button)findViewById(R.id.back_button);
		BackButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(RefuseSlotActivity.this, IMissActivity.class);
				startActivity(intent);
				RefuseSlotActivity.this.finish();
			}
		});
	}
	
	public void DisplayToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	} 
	
    public List<Map<String, String>> addValue(){
    	List<Map<String, String>> value = new ArrayList<Map<String, String>>();
    	
    	Map<String, String> item1 = new HashMap<String, String>();
    	item1.put(Column_1, "�ܽ�ʱ���1");
    	item1.put(Column_2, TargetRefuseSlot);
    	value.add(item1);
    	
    	Map<String, String> item2 = new HashMap<String, String>();
    	item2.put(Column_1, "�ܽ�ʱ���2");
    	item2.put(Column_2, TargetRefuseSlot);
    	value.add(item2);
    	
    	Map<String, String> item3 = new HashMap<String, String>();
    	item3.put(Column_1, "�ܽ�ʱ���3");
    	item3.put(Column_2, TargetRefuseSlot);
    	value.add(item3);  	
    	return value;
    }
    
    public List<Map<String,String>> addNewValue(String title, String content){
    	List<Map<String,String>> value = new ArrayList<Map<String,String>>();  	
    	Map<String,String> item1 = new HashMap<String,String>();
    	item1.put(Column_1, title);
    	item1.put(Column_2, content);
    	value.add(item1);	
    	return value;
    }
}
