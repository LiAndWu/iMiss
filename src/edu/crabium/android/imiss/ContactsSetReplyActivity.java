package edu.crabium.android.imiss;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ContactsSetReplyActivity extends Activity {

	private  EditText ContactsSetReplyEditText;
	private TextView ContactsSetReplyTextView;
	private Button CancelButton, StoreButton;
	
	private final static String ContactsReply = "contacts_reply";
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_contacts_reply);
		SettingProvider sp = SettingProvider.getInstance();
		
		ContactsSetReplyEditText = (EditText) findViewById(R.id.contacts_reply_edittext);
		ContactsSetReplyEditText.setFocusable(true);
		ContactsSetReplyEditText.setFocusableInTouchMode(true);
		
		String contactsReplyEditHintString = getResources().getString(R.string.contacts_reply_edit_hint);
		ContactsSetReplyEditText.setHint(contactsReplyEditHintString);
		ContactsSetReplyEditText.setText(sp.getSetting(ContactsReply));
		
		
		ContactsSetReplyTextView = (TextView) findViewById(R.id.contacts_reply_textview);
		String contactsReplyUseHintString = getResources().getString(R.string.contacts_reply_use_hint);
		ContactsSetReplyTextView.setText(contactsReplyUseHintString);
		
		CancelButton = (Button)findViewById(R.id.cancel_button);
		CancelButton.setOnClickListener(new Button.OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(ContactsSetReplyActivity.this, IMissActivity.class);
			startActivity(intent);
			}
		});
		
		StoreButton = (Button)findViewById(R.id.store_button);
		StoreButton.setOnClickListener(new Button.OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(ContactsSetReplyActivity.this, IMissActivity.class);
			SettingProvider sp = SettingProvider.getInstance();
			Log.d("LOG", ContactsSetReplyEditText.getText().toString());
			sp.addSetting("contacts_reply", ContactsSetReplyEditText.getText().toString());
			startActivity(intent);
			}
		});
	}
	

}
