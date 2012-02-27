package edu.crabium.android.GUI;

import edu.crabium.android.MainActivity;
import edu.crabium.android.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.text.SpannableStringBuilder;  
import android.text.style.BackgroundColorSpan;  
import android.text.style.ForegroundColorSpan;  
import android.text.style.QuoteSpan;  
import android.text.style.RelativeSizeSpan;  
import android.text.style.ScaleXSpan;  
import android.text.style.StrikethroughSpan;  
import android.text.style.TypefaceSpan;  
import android.text.style.URLSpan; 


public class AboutActivity extends Activity {
	private Button BackButton;
	private TextView AboutTextView;
	int start, end;
	private LinearLayout IntroduceButton, HelpButton;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		  
		BackButton = (Button)findViewById(R.id.back_button);
		BackButton.setOnClickListener(new Button.OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(AboutActivity.this, MainActivity.class);
			startActivity(intent);
			AboutActivity.this.finish();
			}
		});
		
		IntroduceButton = (LinearLayout)findViewById(R.id.introduce_chevron_button);
		IntroduceButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AboutActivity.this, IntroduceActivity.class);
				startActivity(intent);
				AboutActivity.this.finish();
			}
		});
        
		HelpButton = (LinearLayout)findViewById(R.id.help_chevron_button);
		HelpButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AboutActivity.this, HelpActivity.class);
				startActivity(intent);
				AboutActivity.this.finish();
			}
		});
		
		AboutTextView = (TextView) findViewById(R.id.about_textView);
		  

	        SpannableStringBuilder word = new SpannableStringBuilder();  
	        
	        final String zero = "       \n";
	        final String one = "\tiMiss\n";  
	        final String two = "\tVersion 1.0\n";  
	        final String three =
	      	  "\t吴旭东:\n\t\t\t  http://tikiet.blog.163.com\n" +
	          "\t\t\t  wuxd@me.com\n" +
    		  "\t李伟:\n\t\t\t  http://mindlee.net\n" +
    		  "\t\t\t  chinawelon@gmail.com\n";
    		 
	        final String four =  "\t\t     Crabium & Mabbage Workshop\n\t\t\t\t  LiWei and WuXudong\n\t\t\t\t\t Copyleft 2011.";  
	        word.append(zero);//调整用空行
	        start = 0;
	        end = zero.length();
	        word.setSpan(new AbsoluteSizeSpan(4), start, end,         		  
	                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
	        
	        word.append(one);
	        start = end;
	        end += one.length();
	        word.setSpan(new AbsoluteSizeSpan(18), start, end,         		  
	                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
	        word.setSpan(new StyleSpan(Typeface.BOLD), start, end,
	        		Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
	
	        word.append(zero);
	        start = end;
	        end += zero.length();
	        word.setSpan(new AbsoluteSizeSpan(1), start, end,         		  
	                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
	        
	        word.append(two);  
	        start = end;  
	        end += two.length();  
	        word.setSpan(new AbsoluteSizeSpan(14), start, end,         		  
	                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
	        word.setSpan(new StyleSpan(Typeface.BOLD), start, end,
	        		Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
	        
	        word.append(zero);
	        start = end;
	        end += zero.length();
	        word.setSpan(new AbsoluteSizeSpan(2), start, end,         		  
	                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
	        
	        word.append(three);  
	        start = end;
	        end += three.length(); 
	        word.setSpan(new AbsoluteSizeSpan(13), start, end,         		  
	                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
	        
	        word.append(zero);
	        start = end;
	        end += zero.length();
	        word.setSpan(new AbsoluteSizeSpan(3), start, end,         		  
	                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
	        
	        word.append(four);  
	        start = end;
	        end += four.length(); 
	        word.setSpan(new AbsoluteSizeSpan(12), start, end,         		  
	                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
	        AboutTextView.setText(word); 
	}
	
}

