package edu.crabium.android.GUI;

import edu.crabium.android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class HelpActivity extends Activity {
	private Button BackButton;
	private TextView HelpTextView;
	
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		  
		BackButton = (Button)findViewById(R.id.back_button);
		BackButton.setOnClickListener(new Button.OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(HelpActivity.this, AboutActivity.class);
			startActivity(intent);
			HelpActivity.this.finish();
			}
		});
		
	   HelpTextView = (TextView) findViewById(R.id.help_textView);
	   HelpTextView.setText(
	   "1）软件设置：\n" +
	   "\t服务关闭/开启选项：设置对未接来电是否回复。\n" +
	   "\t通知关闭/开启选项：设置回复短信后是否通知\n" +
	   "\t陌生人回复关闭/开启选项：设置对陌生人的未接来电回复是否开启。\n" +
	   "2）联系人回复：设置对通讯录中联系人的回复, 但是如果联系人已经添加到小组中，则以小组中回复设置内容。\n" +
	   "3）分组回复：设置对不同小组的不同回复，每个小组可添加一定联系人。\n" +
	   "4）陌生人回复：设置对陌生人的回复。\n");
	 }
}

