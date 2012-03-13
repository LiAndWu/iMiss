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
	  "1） 机主名：你可以设置回复显示的机主名，同时也可以选择是否对陌生人显示。\n" +
	   "2） 设置回复：你可以自定义设置回复内容，当然，包括选择用于回复的联系人。\n" +			   
	   "3） 黑名单： 拦截不想被骚扰的电话。\n" +
	   "4） 拒接时间段：有些时间段不想接电话？不用关机，设置拒接时间段吧，我们帮你拦截所有电话。\n" +
	   "5） 操作：回复内容，黑名单，拒接时间段等都可以进行，新建，删除，修改等操作。\n");	
	 }
}

