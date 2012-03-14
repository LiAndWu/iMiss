
package edu.crabium.android.GUI;

import edu.crabium.android.IMissData;
import edu.crabium.android.IMissActivity;
import edu.crabium.android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class DetailsSetActivity extends Activity {
	private Button BackButton;

	private ToggleButton ServiceSwitchToggleButton, InformSwitchToggleButton, StrangerSwitchToggleButton;
	private TextView ServiceSwitchTextView, InformSwitchTextView, StrangerSwitchTextView;
	private static String ServiceSwitch = "service_switch";
	private static String InformSwitch = "inform_switch";
	private static String StrangerSwitch = "stranger_switch";
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.software_set);
		
		ServiceSwitchTextView = (TextView) findViewById(R.id.service_switch_textview);
		ServiceSwitchToggleButton = (ToggleButton) findViewById(R.id.service_switch_togglebutton);
		ServiceSwitchToggleButton.setChecked(IMissData.getValue(ServiceSwitch).equals("true"));
		ServiceSwitchToggleButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
            	if (ServiceSwitchToggleButton.isChecked()) {
            		IMissData.setValue(ServiceSwitch, "true");
            		ServiceSwitchTextView.setText("服务开启");
            	} else {
            		IMissData.setValue(ServiceSwitch, "false");
            		ServiceSwitchTextView.setText("服务关闭");
            	}
			}
		});
		
		InformSwitchTextView = (TextView) findViewById(R.id.inform_switch_textview);
		InformSwitchToggleButton = (ToggleButton) findViewById(R.id.inform_switch_togglebutton);
		InformSwitchToggleButton.setChecked(IMissData.getValue(InformSwitch).equals("true"));
		InformSwitchToggleButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
            	if (InformSwitchToggleButton.isChecked()) {
            		IMissData.setValue(InformSwitch, "true");
            		InformSwitchTextView.setText("通知开启");
            	} else {
            		IMissData.setValue(InformSwitch, "false");
            		InformSwitchTextView.setText("通知关闭");
            	}
			}
		});
		
		StrangerSwitchTextView = (TextView) findViewById(R.id.stranger_switch_textview);
		StrangerSwitchToggleButton = (ToggleButton) findViewById(R.id.stranger_switch_togglebutton);
		StrangerSwitchToggleButton.setChecked(IMissData.getValue(StrangerSwitch).equals("true"));
		StrangerSwitchToggleButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
            	if (StrangerSwitchToggleButton.isChecked()) {

            		IMissData.setValue(StrangerSwitch, "true");
            		StrangerSwitchTextView.setText("陌生人回复开启");
            	} else {
            		IMissData.setValue(StrangerSwitch, "false");
            		StrangerSwitchTextView.setText("陌生人回复关闭");
            	}
			}
		});
		
		BackButton = (Button)findViewById(R.id.back_button);
		BackButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(DetailsSetActivity.this, IMissActivity.class);
				startActivity(intent);
				DetailsSetActivity.this.finish();
			}
		});
		

	}
	
	public void DisplayToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
}

