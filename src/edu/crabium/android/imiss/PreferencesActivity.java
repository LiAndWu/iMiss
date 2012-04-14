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
		
		final String servicesOpenString = getResources().getString(R.string.services_open);
		final String servicesCloseString = getResources().getString(R.string.services_close);
		final String notificationOpenString = getResources().getString(R.string.notification_open);
		final String notificationCloseString = getResources().getString(R.string.notification_close);
		final String strangerReplyOpenString = getResources().getString(R.string.stranger_reply_open);
		final String strangerReplyCloseString = getResources().getString(R.string.stranger_reply_close);
		
		
    	if (serviceSwitchToggleButton.isChecked()) {
    		serviceSwitchTextView.setText(servicesOpenString);
    	} else {
    		serviceSwitchTextView.setText(servicesCloseString);
    	}
    	
		serviceSwitchToggleButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
            	if (serviceSwitchToggleButton.isChecked()) {
            		sp.addSetting(serviceSwitch, "true");
            		serviceSwitchTextView.setText(servicesOpenString);
            	} else {
            		sp.addSetting(serviceSwitch, "false");
            		serviceSwitchTextView.setText(servicesCloseString);
            	}
			}
		});
		
		informSwitchTextView = (TextView) findViewById(R.id.inform_switch_textview);
		informSwitchToggleButton = (ToggleButton) findViewById(R.id.inform_switch_togglebutton);
		informSwitchToggleButton.setChecked(sp.getSetting(informSwitch).equals("true"));
		
    	if (informSwitchToggleButton.isChecked()) {
    		informSwitchTextView.setText(notificationOpenString);
    	} else {
    		informSwitchTextView.setText(notificationCloseString);
    	}
    	
		informSwitchToggleButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
            	if (informSwitchToggleButton.isChecked()) {
            		sp.addSetting(informSwitch, "true");
            		informSwitchTextView.setText(notificationOpenString);
            	} else {
            		sp.addSetting(informSwitch, "false");
            		informSwitchTextView.setText(notificationCloseString);
            	}
			}
		});
		
		strangerSwitchTextView = (TextView) findViewById(R.id.stranger_switch_textview);
		strangerSwitchToggleButton = (ToggleButton) findViewById(R.id.stranger_switch_togglebutton);
		strangerSwitchToggleButton.setChecked(sp.getSetting(strangerSwitch).equals("true"));
		
    	if (strangerSwitchToggleButton.isChecked()) {
    		strangerSwitchTextView.setText(strangerReplyOpenString);
    	} else {
    		strangerSwitchTextView.setText(strangerReplyCloseString);
    	}
    	
		strangerSwitchToggleButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
            	if (strangerSwitchToggleButton.isChecked()) {
            		sp.addSetting(strangerSwitch, "true");
            		strangerSwitchTextView.setText(strangerReplyOpenString);
            	} else {
            		sp.addSetting(strangerSwitch, "false");
            		strangerSwitchTextView.setText(strangerReplyCloseString);
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

