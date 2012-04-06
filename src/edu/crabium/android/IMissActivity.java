
package edu.crabium.android;

import edu.crabium.android.GUI.AboutActivity;
import edu.crabium.android.GUI.BlackListActivity;
import edu.crabium.android.GUI.ContactsSetReplyActivity;
import edu.crabium.android.GUI.DetailsSetActivity;
import edu.crabium.android.GUI.EditReplyActivity;
import edu.crabium.android.GUI.OwnerNameActivity;
import edu.crabium.android.GUI.RefuseSlotActivity;
import edu.crabium.android.GUI.GroupsSetReplyActivity;
import edu.crabium.android.GUI.StrangerSetReplyActivity;
import edu.crabium.android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class IMissActivity extends Activity {
	private LinearLayout OwnerNameButton, DetatisSetButton, SetReplyButton,
    BlackListButton, RefuseReplyButton, AboutButton;
	TextView OwnerNameTextView;
	private LinearLayout StrangerReplyButton, ContactsReplyButton;
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);    
        IMissData.init(this);
        startService(new Intent(this, IMissService.class));
        
        DisplayToast("Morning, Wei！！");
        /*
         * 
        OwnerNameTextView = (TextView) findViewById(R.id.tv);
        OwnerNameTextView.setText(IMissData.getValue("owner"));
        
        OwnerNameButton = (LinearLayout)findViewById(R.id.software_set_chevron_button);
        OwnerNameButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IMissActivity.this, OwnerNameActivity.class);
				startActivity(intent);
				IMissActivity.this.finish();
			}
		});
        */
        
        DetatisSetButton = (LinearLayout) findViewById(R.id.software_set_chevron_button);
        DetatisSetButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IMissActivity.this, DetailsSetActivity.class);
				startActivity(intent);
				IMissActivity.this.finish();
			}
		});
        
        SetReplyButton = (LinearLayout)findViewById(R.id.set_reply_chevron_button);
        SetReplyButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IMissActivity.this, GroupsSetReplyActivity.class);
				startActivity(intent);
				IMissActivity.this.finish();
			}
		});
        
        
        /*
         *黑名单，暂缓开发
        BlackListButton = (LinearLayout)findViewById(R.id.blacklist_chevron_button);
        BlackListButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IMissActivity.this, BlackListActivity.class);
				startActivity(intent);
				IMissActivity.this.finish();
			}
		});
        
                  拒接名单，暂缓开发
        RefuseReplyButton = (LinearLayout)findViewById(R.id.refuse_reply_chevron_button);
        RefuseReplyButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IMissActivity.this, RefuseSlotActivity.class);
				startActivity(intent);
				IMissActivity.this.finish();
			}
		});
        
        */
        AboutButton = (LinearLayout)findViewById(R.id.about_chevron_button);
        AboutButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IMissActivity.this, AboutActivity.class);
				startActivity(intent);
				IMissActivity.this.finish();
			}
		});
        
		StrangerReplyButton = (LinearLayout) findViewById(R.id.stranger_reply_chevron_button);
		StrangerReplyButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IMissActivity.this, StrangerSetReplyActivity.class);
				startActivity(intent);
				IMissActivity.this.finish();
			}
		});
		
		ContactsReplyButton = (LinearLayout) findViewById(R.id.contacts_reply_chevron_button);
		ContactsReplyButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IMissActivity.this, ContactsSetReplyActivity.class);
				startActivity(intent);
				IMissActivity.this.finish();
			}
		});
    }
    
	public void DisplayToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
}