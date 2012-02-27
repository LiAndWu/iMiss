
package edu.crabium.android;

import edu.crabium.android.R;
import edu.crabium.android.GUI.AboutActivity;
import edu.crabium.android.GUI.BlackListActivity;
import edu.crabium.android.GUI.OwnerNameActivity;
import edu.crabium.android.GUI.RefuseSlotActivity;
import edu.crabium.android.GUI.SetReplyActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	LinearLayout OwnerNameButton, SetReplyButton,
    BlackListButton, RefuseReplyButton, AboutButton;
	TextView OwnerNameTextView;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);    
        IMissData.Initiate(this);
        IMissData.CheckXMLExistence();
        
        startService(new Intent(this, IMissService.class));
        
        OwnerNameTextView = (TextView) findViewById(R.id.tv);
        OwnerNameTextView.setText(IMissData.ReadNode("Owner"));
        
        OwnerNameButton = (LinearLayout)findViewById(R.id.ownername_chevron_button);
        OwnerNameButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, OwnerNameActivity.class);
				startActivity(intent);
				MainActivity.this.finish();
			}
		});
        
        SetReplyButton = (LinearLayout)findViewById(R.id.set_reply_chevron_button);
        SetReplyButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, SetReplyActivity.class);
				startActivity(intent);
				MainActivity.this.finish();
			}
		});
        
        BlackListButton = (LinearLayout)findViewById(R.id.blacklist_chevron_button);
        BlackListButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, BlackListActivity.class);
				startActivity(intent);
				MainActivity.this.finish();
			}
		});
        
        RefuseReplyButton = (LinearLayout)findViewById(R.id.refuse_reply_chevron_button);
        RefuseReplyButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, RefuseSlotActivity.class);
				startActivity(intent);
				MainActivity.this.finish();
			}
		});
        
        AboutButton = (LinearLayout)findViewById(R.id.about_chevron_button);
        AboutButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, AboutActivity.class);
				startActivity(intent);
				MainActivity.this.finish();
			}
		});
    }
}