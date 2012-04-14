package edu.crabium.android.imiss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditReplyActivity extends Activity {
	private Button saveButton, cancelButton;
	private EditText TitleEditText, ContentEditText;
	private Bundle bundle;
	SettingProvider sp = SettingProvider.getInstance();
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_reply);

		bundle = this.getIntent().getExtras();
		final String group_name = bundle.getString("group_name");
		String message_body = bundle.getString("message_body");
		TitleEditText = (EditText) findViewById(R.id.title_edittext);
		TitleEditText.setText(group_name);
		
		String editReplyTitleString = getResources().getString(R.string.edit_reply_title_hint);
		TitleEditText.setHint(editReplyTitleString);
		
		ContentEditText = (EditText) findViewById(R.id.content_edittext);
		ContentEditText.setText(message_body);
		
		String editReplyContentString = getResources().getString(R.string.edit_reply_content_hint);
		ContentEditText.setHint(editReplyContentString);
			
		cancelButton = (Button)findViewById(R.id.cancel_button);
		cancelButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				EditReplyActivity.this.finish();
				}
		});
		
		saveButton = (Button)findViewById(R.id.store_button);
		saveButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if(TitleEditText.getText().toString().equals("")){
					String editReplyNotEmptyString = getResources().getString(R.string.edit_reply_not_empty_hint);
					DisplayToast(editReplyNotEmptyString);
					return ;
				}
					
				sp.deleteMessage(group_name);
				if(!(group_name.equals(TitleEditText.getText().toString()))){
					sp.deleteGroup(group_name);
				}
				
				sp.addGroup(TitleEditText.getText().toString(), ContentEditText.getText().toString());
				EditReplyActivity.this.finish();
			}
		});
	}
	

	
	
	public void DisplayToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
}