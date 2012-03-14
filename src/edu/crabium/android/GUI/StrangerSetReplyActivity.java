
package edu.crabium.android.GUI;

import edu.crabium.android.IMissActivity;
import edu.crabium.android.IMissData;
import edu.crabium.android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StrangerSetReplyActivity extends Activity {

	private  EditText StrangerSetReplyEditText;
	private TextView StrangerSetReplyTextView;
	private Button CancelButton, StoreButton;
	
	private final static String StrangerReply = "stranger_reply";
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_stranger_reply);
		
		StrangerSetReplyEditText = (EditText) findViewById(R.id.stranger_reply_edittext);
		StrangerSetReplyEditText.setFocusable(true);
		StrangerSetReplyEditText.setFocusableInTouchMode(true);
	//	StrangerSetReplyEditText.setText(IMissData.getValue(ContactsReply));
		
		StrangerSetReplyTextView = (TextView) findViewById(R.id.stranger_reply_textview);
		StrangerSetReplyTextView.setText("使用提示：\n" +
		"设置的是   不在通讯录中陌生人   的默认回复。");
		
		CancelButton = (Button)findViewById(R.id.cancel_button);
		CancelButton.setOnClickListener(new Button.OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(StrangerSetReplyActivity.this, IMissActivity.class);
			startActivity(intent);
			StrangerSetReplyActivity.this.finish();
			}
		});
		
		StoreButton = (Button)findViewById(R.id.store_button);
		StoreButton.setOnClickListener(new Button.OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(StrangerSetReplyActivity.this, IMissActivity.class);
			startActivity(intent);
			StrangerSetReplyActivity.this.finish();
			}
		});
	}
	

}
