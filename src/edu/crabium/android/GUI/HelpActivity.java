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
		"1�� ��������������ûظ���ʾ�Ļ�����ͬʱҲ����ѡ���Ƿ��İ������ʾ��\n" +
	    "2�� ���ûظ���������Զ������ûظ����ݣ���Ȼ������ѡ�����ڻظ�����ϵ�ˡ�\n" +
		"3�� ���� ���ز��뱻ɧ�ŵĵ绰��\n" +
	    "4�� �ܽ�ʱ��Σ���Щʱ��β���ӵ绰�����ùػ����þܽ�ʱ��ΰɣ����ǰ����������е绰��\n" +
		"5�� �������ظ����ݣ������ܽ�ʱ��εȶ����Խ��У��½���ɾ���޸ĵȲ�����\n");
	}
	
}

