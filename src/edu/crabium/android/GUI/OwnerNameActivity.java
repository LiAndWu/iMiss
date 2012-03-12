package edu.crabium.android.GUI;

import edu.crabium.android.IMissData;
import edu.crabium.android.IMissActivity;
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
import android.widget.Toast;
import android.widget.ToggleButton;


public class OwnerNameActivity extends Activity {
	private Button BackButton, StoreButton;
	private ToggleButton OwnerNameToggleButton;
	private EditText OwnerEditText;
	private TextView ToggleButtonTextView;
	
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.owner_name);
		
		Log.d("TAG", "O1");
		OwnerEditText = (EditText) findViewById(R.id.ownername_editview);
		OwnerEditText.setFocusable(true);
		OwnerEditText.setFocusableInTouchMode(true);
		OwnerEditText.setHint("输入机主名");
		OwnerEditText.setText(IMissData.getValue("Owner"));
		Log.d("TAG", "O2");
		DisplayToast("现在的机主名是" + IMissData.getValue("Owner"));
		Log.d("TAG", "O3");
		ToggleButtonTextView = (TextView) findViewById(R.id.togglebutton_textview);
		
		OwnerNameToggleButton = (ToggleButton) findViewById(R.id.ownername_togglebutton);
		OwnerNameToggleButton.setChecked(IMissData.getValue("ShowOwnerNameToStranger").equals("true"));
		Log.d("TAG", "O4");
		OwnerNameToggleButton.setOnClickListener(new Button.OnClickListener(){ 
            public void onClick(View v) { 
            	if (OwnerNameToggleButton.isChecked()) {
            		IMissData.setValue("ShowOwnerNameToStranger", "true");
            		ToggleButtonTextView.setText("对陌生人开启");
            		Log.d("TAG", "O5");
            	} else {
            		IMissData.setValue("ShowOwnerNameToStranger", "false");
            		ToggleButtonTextView.setText("对陌生人关闭");
            	}
            } 
		});
		
		BackButton = (Button)findViewById(R.id.back_button);
		BackButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(OwnerNameActivity.this, IMissActivity.class);
				startActivity(intent);
				OwnerNameActivity.this.finish();
			}
		});
		
		StoreButton = (Button)findViewById(R.id.store_button);
		StoreButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				OwnerEditText.setFocusable(false);
				OwnerEditText.setFocusableInTouchMode(false);
				IMissData.setValue("Owner",OwnerEditText.getText().toString());

				DisplayToast("现在的机主名是" + IMissData.getValue("Owner") + "\n" +
						(IMissData.getValue("ShowOwnerNameToStranger").equals("true") ? "\t对陌生人开启" : "\t对陌生人关闭"));	
				
				Intent intent = new Intent(OwnerNameActivity.this, IMissActivity.class);
				startActivity(intent);
				OwnerNameActivity.this.finish();
			}
		});
	}
	
	public void DisplayToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
}

