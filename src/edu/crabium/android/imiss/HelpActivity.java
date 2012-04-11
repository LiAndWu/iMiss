package edu.crabium.android.imiss;

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
	   final String helpDescriptionString = getResources().getString(R.string.help_description);
	   HelpTextView.setText(helpDescriptionString);
	 }
}

