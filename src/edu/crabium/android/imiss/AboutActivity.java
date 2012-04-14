package edu.crabium.android.imiss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class AboutActivity extends Activity {
	private TextView AboutTextView;
	int start, end;
	private LinearLayout IntroduceButton, HelpButton;
	private String aboutDescriptionString;
	private Button backButton;
	
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		  
		IntroduceButton = (LinearLayout)findViewById(R.id.introduce_chevron_button);
		IntroduceButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AboutActivity.this, IntroduceActivity.class);
				startActivity(intent);
			}
		});
        
		HelpButton = (LinearLayout)findViewById(R.id.help_chevron_button);
		HelpButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AboutActivity.this, HelpActivity.class);
				startActivity(intent);
			}
		});
		
		backButton = (Button)findViewById(R.id.back_button);
		backButton.setOnClickListener(new Button.OnClickListener() {
		public void onClick(View v) {
			AboutActivity.this.finish();
			}
		});
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		AboutTextView = (TextView) findViewById(R.id.about_textView);
		/**
		 * if (heightPixels < 600) 
		 * 		low resolution, such as 480 * 320
		 * else if (heightPixels / widthPixels < 16 / 9)
		 * 		high resolution apply to 5:3 or 3:2 or : 4:3
		 * else 
		 * 		high resolution apply to 16:9 and more strange proportion
 		 */
		if (dm.heightPixels < 600) {
			aboutDescriptionString = getResources().getString(R.string.about_description_lower_resolution);
		    AboutTextView.setText(aboutDescriptionString); 
		} else if (9 * dm.heightPixels < 16 * dm.widthPixels ) {
			aboutDescriptionString = getResources().getString(R.string.about_description_960_640);
			AboutTextView.setText(aboutDescriptionString); 
		} else {
			aboutDescriptionString = getResources().getString(R.string.about_description_854_480);
			AboutTextView.setText(aboutDescriptionString); 
		}
	}
}

