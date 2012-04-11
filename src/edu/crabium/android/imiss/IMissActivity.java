package edu.crabium.android.imiss;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class IMissActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        SettingProvider sp = SettingProvider.getInstance();
        sp.setContext(this);
        startService(new Intent(this, IMissService.class));
   
        LinearLayout preferencesButton = (LinearLayout) findViewById(R.id.software_set_chevron_button);
        preferencesButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IMissActivity.this, PreferencesActivity.class);
				startActivity(intent);
			}
		});
        
        LinearLayout SetReplyButton = (LinearLayout)findViewById(R.id.set_reply_chevron_button);
        SetReplyButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IMissActivity.this, GroupManagementActivity.class);
				startActivity(intent);
			}
		});
        
        LinearLayout aboutButton = (LinearLayout)findViewById(R.id.about_chevron_button);
        aboutButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IMissActivity.this, AboutActivity.class);
				startActivity(intent);
			}
		});
        
        LinearLayout StrangerReplyButton = (LinearLayout) findViewById(R.id.stranger_reply_chevron_button);
		StrangerReplyButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IMissActivity.this, StrangerSetReplyActivity.class);
				startActivity(intent);
			}
		});

		LinearLayout ContactsReplyButton = (LinearLayout) findViewById(R.id.contacts_reply_chevron_button);
		ContactsReplyButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IMissActivity.this, ContactsSetReplyActivity.class);
				startActivity(intent);
			}
		});


        /*
        拒接名单，暂缓开发
        LinearLayout RefuseReplyButton = (LinearLayout)findViewById(R.id.refuse_reply_chevron_button);
        RefuseReplyButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IMissActivity.this, RefuseSlotActivity.class);
				startActivity(intent);
				IMissActivity.this.finish();
			}
		});
        
        TextView OwnerNameTextView = (TextView) findViewById(R.id.tv);
        OwnerNameTextView.setText(IMissData.getValue("owner"));
        
        LinearLayout OwnerNameButton = (LinearLayout)findViewById(R.id.software_set_chevron_button);
        OwnerNameButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IMissActivity.this, OwnerNameActivity.class);
				startActivity(intent);
				IMissActivity.this.finish();
			}
		});
        */
    }
}