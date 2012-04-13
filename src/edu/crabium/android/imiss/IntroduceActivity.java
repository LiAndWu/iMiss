package edu.crabium.android.imiss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public class IntroduceActivity extends Activity {
	private TextView IntroduceTextView;
	
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.introduce);
		IntroduceTextView = (TextView) findViewById(R.id.introduce_textView);
		String introduceString = getResources().getString(R.string.introduce);
		IntroduceTextView.setText(introduceString);
	}
}

