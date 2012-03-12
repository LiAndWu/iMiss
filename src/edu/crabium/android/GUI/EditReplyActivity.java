package edu.crabium.android.GUI;

import edu.crabium.android.GlobalVariable;
import edu.crabium.android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class EditReplyActivity extends Activity {
	private Button CancelButton, SaveButton;
	private EditText TitleEditText, ContentEditText;
	
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_reply);
		
		TitleEditText = (EditText) findViewById(R.id.title_edittext);
		TitleEditText.setText(GlobalVariable.TargetReplyTitle);
		
		ContentEditText = (EditText) findViewById(R.id.content_edittext);
		ContentEditText.setText(GlobalVariable.TargetReplyContent);
		ContentEditText.setHint("请输入回复内容");
			
		SaveButton = (Button)findViewById(R.id.store_button);
		SaveButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				GlobalVariable.TargetReplyTitle = TitleEditText.getText().toString();
				GlobalVariable.TargetReplyContent = ContentEditText.getText().toString();
				
				Intent intent = new Intent(EditReplyActivity.this, SetReplyActivity.class);
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
	
}

