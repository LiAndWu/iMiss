
package edu.crabium.android.GUI;

import edu.crabium.android.IMissActivity;
import edu.crabium.android.SettingProvider;
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

public class StrangerSetReplyActivity extends Activity {

	private  EditText StrangerSetReplyEditText;
	private TextView StrangerSetReplyTextView;
	private Button CancelButton, StoreButton;
	
	private final static String StrangerReply = "stranger_reply";
	
	SettingProvider sp = SettingProvider.getInstance();
	
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_stranger_reply);
		
		StrangerSetReplyEditText = (EditText) findViewById(R.id.stranger_reply_edittext);
		StrangerSetReplyEditText.setFocusable(true);
		StrangerSetReplyEditText.setFocusableInTouchMode(true);
		
		StrangerSetReplyEditText.setHint("输入陌生人回复.");
		StrangerSetReplyEditText.setText(sp.getSetting(StrangerReply));
		
		
		StrangerSetReplyTextView = (TextView) findViewById(R.id.stranger_reply_textview);
		StrangerSetReplyTextView.setText("使用提示：\n" +
		"回复对象是：不在通讯录中的陌生人。");
		
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
			Log.d("HELLO", "STORE " + StrangerSetReplyEditText.getText().toString());
			sp.addSetting(StrangerReply, StrangerSetReplyEditText.getText().toString());
			Log.d("HELLO", "GET" + sp.getSetting(StrangerReply));
			startActivity(intent);
			StrangerSetReplyActivity.this.finish();
			}
		});
	}
	

}
