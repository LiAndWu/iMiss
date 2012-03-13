package edu.crabium.android.GUI;

import java.util.ArrayList;
import java.util.HashMap;

import edu.crabium.android.MyListAdapter.ViewHolder;

import edu.crabium.android.MyListAdapter;
import edu.crabium.android.R;
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


public class SelectLinkManActivity extends Activity {
    private ListView SelectLinkManListView;
    private Button BackButton, SaveButton;
    
	HashMap<Integer,Boolean> map ;
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_linkman);

        ArrayList<String[]> name = new ArrayList<String[]>();
        name = addValue();
        map = new HashMap<Integer, Boolean>();
        for (int i = 0; i < name.size(); i++) {
			map.put(i, false);
		}
        SelectLinkManListView = (ListView) findViewById(R.id.select_linkman_listView); 
        SelectLinkManListView.setOnItemClickListener(lis);
        
        final MyListAdapter mla = new MyListAdapter(SelectLinkManActivity.this, name.size(),
        		name.toArray(new String[][]{}));
        SelectLinkManListView.setAdapter(mla);

        BackButton = (Button)findViewById(R.id.back_button);
        BackButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SelectLinkManActivity.this, SetReplyActivity.class);
                startActivity(intent);
                SelectLinkManActivity.this.finish();
            }
        });	
        
        SaveButton = (Button)findViewById(R.id.store_button);
        SaveButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SelectLinkManActivity.this, SetReplyActivity.class);
                startActivity(intent);
                SelectLinkManActivity.this.finish();
            }
        });	
    }

    public ArrayList<String[]> addValue(){
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
                Log.d("TAG", "Name: "+ contact + "and Number: " + phoneNumber);
            }
        }
        cursor.close();
        return value;
    }
    
    private OnItemClickListener lis = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, 
				long arg3) {
			ViewHolder holder = (ViewHolder) arg1.getTag();
			holder.cBox.toggle();
			MyListAdapter.isSelected.put(arg2, holder.cBox.isChecked());
			Log.d("TAG", "123" + holder.cBox.isChecked());
		}
	};
}

