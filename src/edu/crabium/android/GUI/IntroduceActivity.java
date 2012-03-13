package edu.crabium.android.GUI;

import edu.crabium.android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public class IntroduceActivity extends Activity {
	private Button BackButton;
	private TextView IntroduceTextView;
	
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.introduce);
		  
		BackButton = (Button)findViewById(R.id.back_button);
		BackButton.setOnClickListener(new Button.OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(IntroduceActivity.this, AboutActivity.class);
			startActivity(intent);
			IntroduceActivity.this.finish();
			}
		});
		
		IntroduceTextView = (TextView) findViewById(R.id.introduce_textView);
		  IntroduceTextView.setText("\n\n\tiMiss是一款贴心的来电辅助软" +
				 "件。如果你因为重要的事情错过了同样重要的一个电话，那么iMiss可以帮助你解决这个难题。" +
				"\n\t在您无法接听或者错过来电的时候，iMiss会自动帮您发送一条信息到来电者的手机上，一切就欧凯啦~");
	}
	
}

