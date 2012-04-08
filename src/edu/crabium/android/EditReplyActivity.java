package edu.crabium.android;

import edu.crabium.android.R;
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
	private Button CancelButton, SaveButton;
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
		TitleEditText.setHint("请输入分组名称");
		
		ContentEditText = (EditText) findViewById(R.id.content_edittext);
		ContentEditText.setText(message_body);
		
		ContentEditText.setHint("请输入回复内容");
			
		SaveButton = (Button)findViewById(R.id.store_button);
		SaveButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if(TitleEditText.getText().toString().equals("")){
					displayToast("组名不能为空");
					return;
				}
					
				sp.deleteMessage(group_name);
				if(!(group_name.equals(TitleEditText.getText().toString()))){
					sp.deleteGroup(group_name);
				}
				
				sp.addGroup(TitleEditText.getText().toString(), ContentEditText.getText().toString());
				
				Intent intent = new Intent(EditReplyActivity.this, GroupManagementActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
			
		CancelButton = (Button)findViewById(R.id.cancel_button);
		CancelButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(EditReplyActivity.this, GroupManagementActivity.class);
				startActivity(intent);
			}
		});
	}
	public void displayToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
}