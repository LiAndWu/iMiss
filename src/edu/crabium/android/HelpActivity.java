package edu.crabium.android;

import edu.crabium.android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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
			}
		});
		
	   HelpTextView = (TextView) findViewById(R.id.help_textView);
	   HelpTextView.setMovementMethod(ScrollingMovementMethod.getInstance()); 
	   HelpTextView.setText(
	   "1) 软件设置：\n" +
	   "    服务关闭/开启选项：设置对未接来电是否回复。\n" +
	   "    通知关闭/开启选项：设置回复短信后是否通知\n" +
	   "    陌生人回复关闭/开启选项：设置对陌生人的未接来电回复是否开启。\n" +
	   "\n2) 联系人回复：\n"  +
	   	"    设置对通讯录中联系人的回复, 但是如果联系人已经添加到小组中，则回复小组中设置的内容。\n" +
	   "\n3) 分组回复：\n" + 
	   	"    设置对不同小组的不同回复，每个小组可添加一定联系人。比如对家人，朋友等设置不同回复内容。\n" +
	   "\n4) 陌生人回复：\n"  +
	   	"    设置对不在通讯录中陌生人的回复。\n");
	 }
}

