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
		IntroduceTextView.setText("\n\n\tiMiss��һ�����ĵ����縨����" +
				"�����������Ϊ��Ҫ����������ͬ����Ҫ��һ���绰����ôiMiss���԰�������������⡣" +
		"\n\t�����޷��������ߴ�������ʱ��iMiss���Զ���������һ����Ϣ�������ߵ��ֻ��ϣ�һ�о�ŷ����~");
	}
	
}

