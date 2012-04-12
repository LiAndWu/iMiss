package edu.crabium.android.imiss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import edu.crabium.android.imiss.IMissListViewAdapter.ViewHolder;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class SelectContactsActivity extends Activity {
	private ListView selectContactsListView;
    private Button BackButton, SaveButton;

	Bundle bundle;
    ArrayList<String[]> name;
    IMissListViewAdapter adapter;
	HashMap<Integer,Boolean> map ;
	SettingProvider sp = SettingProvider.getInstance();
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_linkman);
        bundle = this.getIntent().getExtras();
        name = addValue();
        map = new HashMap<Integer, Boolean>();
        for (int i = 0; i < name.size(); i++) {
			map.put(i, false); 
		}
        selectContactsListView = (ListView) findViewById(R.id.select_linkman_listView); 
        selectContactsListView.setOnItemClickListener( lis);
        
        adapter = new IMissListViewAdapter(name, this);
        selectContactsListView.setAdapter(adapter);
        BackButton = (Button)findViewById(R.id.back_button);
        
        BackButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SelectContactsActivity.this, GroupManagementActivity.class);
                startActivity(intent);
            }
        });	
        
        SaveButton = (Button)findViewById(R.id.store_button);
        SaveButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	
                Intent intent = new Intent(SelectContactsActivity.this, GroupManagementActivity.class);
                String[] pair;
                String group_name = bundle.getString("group_name");
                String person_name;
                String person_phone;
                for(int i = 0; i < adapter.getCount(); i++){
                	if(adapter.getCheck(i) == true){
                		pair = (String[]) adapter.getItem(i);
                		person_name = pair[0];
                		person_phone = pair[1];

                		sp.addPersonToGroup(person_name, person_phone, group_name);
                	}
               }
                startActivity(intent);
            }
        });	
    }

    private ArrayList<String[]> addValue(){
    	ArrayList<String[]> value = new ArrayList<String[]>();
    	
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

       while(cursor.moveToNext()) {
            int nameFieldColumnIndex = cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME);
            String contact = cursor.getString(nameFieldColumnIndex);

            String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);

            while(phone.moveToNext()) {
                String phoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));     
                value.add(new String[]{contact,phoneNumber});
            }
        }
        cursor.close();
        return new ArrayList<String[]>(value);
    }
    
    
    private OnItemClickListener lis = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, 
				long arg3) {
			ViewHolder holder = (ViewHolder) arg1.getTag();
			holder.checkBox.toggle();
			adapter.isSelected.put(arg2, holder.checkBox.isChecked());
		}
	};
	
}

