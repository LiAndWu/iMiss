package edu.crabium.android.widget;

import edu.crabium.android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChevronButtonTwopart extends LinearLayout {

	private ImageView iv;
	private TextView  TitleWidget;

    
    public ChevronButtonTwopart(Context context) {
        this(context, null);  
    }

    public ChevronButtonTwopart(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.chevron_button_twopart, this, true);
        iv = (ImageView) findViewById(R.id.iv);
        TitleWidget = (TextView) findViewById(R.id.title_of_widget);

    }
    
    public void setImageResource(int resId) {
        iv.setImageResource(resId);
    }

    public void setTitleTextViewText(String text) {
        TitleWidget.setText(text);
    }
    
}