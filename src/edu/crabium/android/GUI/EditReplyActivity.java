package edu.crabium.android.GUI;

import edu.crabium.android.GlobalVariable;
import edu.crabium.android.IMissData;
import edu.crabium.android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditReplyActivity extends Activity {
	private Button CancelButton, SaveButton;
	private EditText TitleEditText, ContentEditText;
	private Bundle bundle;
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_reply);
		
		bundle = this.getIntent().getExtras();
		TitleEditText = (EditText) findViewById(R.id.title_edittext);
		TitleEditText.setText(bundle.getString("group_name"));
		
		ContentEditText = (EditText) findViewById(R.id.content_edittext);
		ContentEditText.setText(bundle.getString("message_body"));
		
		ContentEditText.setHint("请输入回复内容");
			
		SaveButton = (Button)findViewById(R.id.store_button);
		SaveButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if(TitleEditText.getText().toString().equals("")){
					displayToast("组名不能为空");
					return;
				}
					
				
				if(!bundle.getString("group_name").equals(TitleEditText.getText().toString()))
					IMissData.delGroup(bundle.getString("group_name"));
				IMissData.addGroup(new String[]{ TitleEditText.getText().toString(), ContentEditText.getText().toString()});
				
				Intent intent = new Intent(EditReplyActivity.this, SetReplyActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
				EditReplyActivity.this.finish();
			}
		});
			
		CancelButton = (Button)findViewById(R.id.cancel_button);
		CancelButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(EditReplyActivity.this, SetReplyActivity.class);
				startActivity(intent);
				EditReplyActivity.this.finish();
			}
		});
	}
	public void displayToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
	
}

