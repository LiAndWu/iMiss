package edu.crabium.android.widget;


import edu.crabium.android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChevronButtonThreePart extends LinearLayout {
	private ImageView iv;
	private TextView  tv, TitleWidget;

    
    public ChevronButtonThreePart(Context context) {
        this(context, null);
    }

    public ChevronButtonThreePart(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 导入布局
        LayoutInflater.from(context).inflate(R.layout.chevron_button_threepart, this, true);
        iv = (ImageView) findViewById(R.id.iv);
        tv = (TextView) findViewById(R.id.tv);
        tv.setText("");
        TitleWidget = (TextView) findViewById(R.id.title_of_widget);

    }

    
    /**
     * 设置图片资源
     */
    public void setImageResource(int resId) {
        iv.setImageResource(resId);
    }

    /**
     * 设置按钮标题的文字
     */
    public void setTitleTextViewText(String text) {
        TitleWidget.setText(text);
    }
    
    /**
     * 设置选择后的内容
     */
    public void setContentTextViewText(String text) {
        tv.setText(text);
    }

}