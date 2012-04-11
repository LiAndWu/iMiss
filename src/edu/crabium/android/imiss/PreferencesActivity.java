
package edu.crabium.android.imiss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class PreferencesActivity extends Activity {
	private Button BackButton;
	private ToggleButton serviceSwitchToggleButton, informSwitchToggleButton, strangerSwitchToggleButton;
	private TextView serviceSwitchTextView, informSwitchTextView, strangerSwitchTextView;
	private static String serviceSwitch = "service_switch";
	private static String informSwitch = "inform_switch";
	private static String strangerSwitch = "stranger_switch";
	
	SettingProvider sp = SettingProvider.getInstance();
	
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.software_set);
		
		serviceSwitchTextView = (TextView) findViewById(R.id.service_switch_textview);
		serviceSwitchToggleButton = (ToggleButton) findViewById(R.id.service_switch_togglebutton);
		serviceSwitchToggleButton.setChecked(sp.getSetting(serviceSwitch).equals("true"));
		
    	if (serviceSwitchToggleButton.isChecked()) {
    		serviceSwitchTextView.setText("服务开启");
    	} else {
    		serviceSwitchTextView.setText("服务关闭");
    	}
    	
		serviceSwitchToggleButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
            	if (serviceSwitchToggleButton.isChecked()) {
            		sp.addSetting(serviceSwitch, "true");
            		serviceSwitchTextView.setText("服务开启");
            	} else {
            		sp.addSetting(serviceSwitch, "false");
            		serviceSwitchTextView.setText("服务关闭");
            	}
			}
		});
		
		informSwitchTextView = (TextView) findViewById(R.id.inform_switch_textview);
		informSwitchToggleButton = (ToggleButton) findViewById(R.id.inform_switch_togglebutton);
		informSwitchToggleButton.setChecked(sp.getSetting(informSwitch).equals("true"));
		
    	if (informSwitchToggleButton.isChecked()) {
    		informSwitchTextView.setText("通知开启");
    	} else {
    		informSwitchTextView.setText("通知关闭");
    	}
    	
		informSwitchToggleButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
            	if (informSwitchToggleButton.isChecked()) {
            		sp.addSetting(informSwitch, "true");
            		informSwitchTextView.setText("通知开启");
            	} else {
            		sp.addSetting(informSwitch, "false");
            		informSwitchTextView.setText("通知关闭");
            	}
			}
		});
		
		strangerSwitchTextView = (TextView) findViewById(R.id.stranger_switch_textview);
		strangerSwitchToggleButton = (ToggleButton) findViewById(R.id.stranger_switch_togglebutton);
		strangerSwitchToggleButton.setChecked(sp.getSetting(strangerSwitch).equals("true"));
		
    	if (strangerSwitchToggleButton.isChecked()) {
    		strangerSwitchTextView.setText("陌生人回复开启");
    	} else {
    		strangerSwitchTextView.setText("陌生人回复关闭");
    	}
    	
		strangerSwitchToggleButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
            	if (strangerSwitchToggleButton.isChecked()) {
            		sp.addSetting(strangerSwitch, "true");
            		strangerSwitchTextView.setText("陌生人回复开启");
            	} else {
            		sp.addSetting(strangerSwitch, "false");
            		strangerSwitchTextView.setText("陌生人回复关闭");
            	}
			}
		});
		
		BackButton = (Button)findViewById(R.id.back_button);
		BackButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(PreferencesActivity.this, IMissActivity.class);
				startActivity(intent);
			}
		});
		

	}
	
	public void DisplayToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
}

