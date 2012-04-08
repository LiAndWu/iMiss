package edu.crabium.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import edu.crabium.android.R;

public class EditContactActivity extends Activity {
	private Button CancelButton, SaveButton;
	EditText NameEditText, PhoneEditText;
	
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);   
		setContentView(R.layout.edit_linkman);
		
		NameEditText = (EditText) findViewById(R.id.name_editText);
		PhoneEditText = (EditText) findViewById(R.id.phone_editText);	
		NameEditText.setFocusable(true);
		NameEditText.setFocusableInTouchMode(true);
		PhoneEditText.setFocusable(true);
		PhoneEditText.setFocusableInTouchMode(true);  

		NameEditText.setText(GlobalVariable.TargetBlackListName);
		PhoneEditText.setText(GlobalVariable.TargetBlackListNumber);
		
		CancelButton = (Button)findViewById(R.id.cancel_button);
		CancelButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(EditContactActivity.this, BlackListActivity.class);
				startActivity(intent);
			}
		});		
		
		SaveButton = (Button)findViewById(R.id.store_button);
		SaveButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				
				Intent intent = new Intent(EditContactActivity.this, BlackListActivity.class);
				startActivity(intent);
			}
		});
	}
}
