package edu.crabium.android.GUI;

import edu.crabium.android.IMissActivity;
import edu.crabium.android.IMissData;
import edu.crabium.android.R;
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

	private  EditText ContactsSetReplyEditText;
	private TextView ContactsSetReplyTextView;
	private Button CancelButton, StoreButton;
	
	private final static String ContactsReply = "contacts_reply";
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_contacts_reply);
		
		ContactsSetReplyEditText = (EditText) findViewById(R.id.contacts_reply_edittext);
		ContactsSetReplyEditText.setFocusable(true);
		ContactsSetReplyEditText.setFocusableInTouchMode(true);
		ContactsSetReplyEditText.setText(IMissData.getValue(ContactsReply));
		
		ContactsSetReplyTextView = (TextView) findViewById(R.id.contacts_reply_textview);
		ContactsSetReplyTextView.setText("使用提示：\n" +
		"设置的是   在通讯录中联系人   的默认回复。");
		
		CancelButton = (Button)findViewById(R.id.cancel_button);
		CancelButton.setOnClickListener(new Button.OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(ContactsSetReplyActivity.this, IMissActivity.class);
			startActivity(intent);
			ContactsSetReplyActivity.this.finish();
			}
		});
		
		StoreButton = (Button)findViewById(R.id.store_button);
		StoreButton.setOnClickListener(new Button.OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(ContactsSetReplyActivity.this, IMissActivity.class);
			
			Log.d("LOG", ContactsSetReplyEditText.getText().toString());
			IMissData.setValue("contacts_reply", ContactsSetReplyEditText.getText().toString());
			startActivity(intent);
			ContactsSetReplyActivity.this.finish();
			}
		});
	}
	

}
