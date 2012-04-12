
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

public class StrangerSetReplyActivity extends Activity {

	private  EditText strangerSetReplyEditText;
	private TextView strangerSetReplyTextView;
	private Button CancelButton, StoreButton;
	
	private final static String StrangerReply = "stranger_reply";
	SettingProvider sp = SettingProvider.getInstance();
	
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_stranger_reply);
		
		strangerSetReplyEditText = (EditText) findViewById(R.id.stranger_reply_edittext);
		strangerSetReplyEditText.setFocusable(true);
		strangerSetReplyEditText.setFocusableInTouchMode(true);
		
		String strangerReplyEditHintString = getResources().getString(R.string.stranger_reply_edit_hint);
		strangerSetReplyEditText.setHint(strangerReplyEditHintString);
		strangerSetReplyEditText.setText(sp.getSetting(StrangerReply));
		
		strangerSetReplyTextView = (TextView) findViewById(R.id.stranger_reply_textview);
		String strangerReplyUseHintString = getResources().getString(R.string.stranger_reply_use_hint);
		strangerSetReplyTextView.setText(strangerReplyUseHintString);
		
		CancelButton = (Button)findViewById(R.id.cancel_button);
		CancelButton.setOnClickListener(new Button.OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(StrangerSetReplyActivity.this, IMissActivity.class);
			startActivity(intent);
			}
		});
		
		StoreButton = (Button)findViewById(R.id.store_button);
		StoreButton.setOnClickListener(new Button.OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(StrangerSetReplyActivity.this, IMissActivity.class);
			Log.d("HELLO", "STORE " + strangerSetReplyEditText.getText().toString());
			sp.addSetting(StrangerReply, strangerSetReplyEditText.getText().toString());
			Log.d("HELLO", "GET" + sp.getSetting(StrangerReply));
			startActivity(intent);
			}
		});
	}
	

}
