
package edu.crabium.android;

import edu.crabium.android.GUI.AboutActivity;
import edu.crabium.android.GUI.BlackListActivity;
import edu.crabium.android.GUI.OwnerNameActivity;
import edu.crabium.android.GUI.RefuseSlotActivity;
import edu.crabium.android.GUI.SetReplyActivity;
import edu.crabium.android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IMissActivity extends Activity {
	LinearLayout OwnerNameButton, SetReplyButton,
    BlackListButton, RefuseReplyButton, AboutButton;
	TextView OwnerNameTextView;
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);    
        
        startService(new Intent(this, IMissService.class));
        
        OwnerNameTextView = (TextView) findViewById(R.id.tv);
        OwnerNameTextView.setText(IMissData.getValue("owner"));
        
        OwnerNameButton = (LinearLayout)findViewById(R.id.ownername_chevron_button);
        OwnerNameButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IMissActivity.this, OwnerNameActivity.class);
				startActivity(intent);
				IMissActivity.this.finish();
			}
		});
        
        SetReplyButton = (LinearLayout)findViewById(R.id.set_reply_chevron_button);
        SetReplyButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IMissActivity.this, SetReplyActivity.class);
				startActivity(intent);
				IMissActivity.this.finish();
			}
		});
        
        BlackListButton = (LinearLayout)findViewById(R.id.blacklist_chevron_button);
        BlackListButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IMissActivity.this, BlackListActivity.class);
				startActivity(intent);
				IMissActivity.this.finish();
			}
		});
        
        RefuseReplyButton = (LinearLayout)findViewById(R.id.refuse_reply_chevron_button);
        RefuseReplyButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IMissActivity.this, RefuseSlotActivity.class);
				startActivity(intent);
				IMissActivity.this.finish();
			}
		});
        
        AboutButton = (LinearLayout)findViewById(R.id.about_chevron_button);
        AboutButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IMissActivity.this, AboutActivity.class);
				startActivity(intent);
				IMissActivity.this.finish();
			}
		});
    }
}