package edu.crabium.android.imiss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ContactsSetReplyActivity extends Activity {

	private  EditText contactsSetReplyEditText;
	private TextView contactsSetReplyTextView;
	private Button storeButton;
	private Button cancelButton;
	private final static String ContactsReply = "contacts_reply";
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_contacts_reply);
		SettingProvider sp = SettingProvider.getInstance();
		
		contactsSetReplyEditText = (EditText) findViewById(R.id.contacts_reply_edittext);
		contactsSetReplyEditText.setFocusable(true);
		contactsSetReplyEditText.setFocusableInTouchMode(true);
		
		String contactsReplyEditHintString = getResources().getString(R.string.contacts_reply_edit_hint);
		contactsSetReplyEditText.setHint(contactsReplyEditHintString);
		contactsSetReplyEditText.setText(sp.getSetting(ContactsReply));
		
		
		contactsSetReplyTextView = (TextView) findViewById(R.id.contacts_reply_textview);
		String contactsReplyUseHintString = getResources().getString(R.string.contacts_reply_use_hint);
		contactsSetReplyTextView.setText(contactsReplyUseHintString);

		cancelButton = (Button)findViewById(R.id.cancel_button);
		cancelButton.setOnClickListener(new Button.OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(ContactsSetReplyActivity.this, IMissActivity.class);
			startActivity(intent);
			}
		});
		
		storeButton = (Button)findViewById(R.id.store_button);
		storeButton.setOnClickListener(new Button.OnClickListener() {
		public void onClick(View v) {
			SettingProvider sp = SettingProvider.getInstance();
			Log.d("LOG", contactsSetReplyEditText.getText().toString());
			sp.addSetting("contacts_reply", contactsSetReplyEditText.getText().toString());
			ContactsSetReplyActivity.this.finish();
			}
		});
	}
	

}
