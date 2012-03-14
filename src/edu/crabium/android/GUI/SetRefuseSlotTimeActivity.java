package edu.crabium.android.GUI;


import edu.crabium.android.GlobalVariable;
import edu.crabium.android.R;
import edu.crabium.android.wheel.NumericWheelAdapter;
import edu.crabium.android.wheel.OnWheelChangedListener;
import edu.crabium.android.wheel.OnWheelScrollListener;
import edu.crabium.android.wheel.WheelView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;


public class SetRefuseSlotTimeActivity extends Activity {
	private Button BackButton, StoreButton;
	private EditText StartTimeEditText, EndTimeEditText;
	@SuppressWarnings("unused")
	private boolean timeChanged = false;
	private boolean timeScrolled = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) { 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_refuse_slot_time);
		
		StartTimeEditText = (EditText) findViewById(R.id.startTime_editView);
		StartTimeEditText.setText(GlobalVariable.TargetStartTimeHour + " : " + GlobalVariable.TargetStartTimeMins);
		StartTimeEditText.setInputType(InputType.TYPE_NULL);
		StartTimeEditText.setFocusable(true);
		StartTimeEditText.setFocusableInTouchMode(true);
		
		EndTimeEditText = (EditText) findViewById(R.id.endTime_editView);
		EndTimeEditText.setText(GlobalVariable.TargetEndTimeHour + " : " + GlobalVariable.TargetEndTimeMins);
		EndTimeEditText.setInputType(InputType.TYPE_NULL);
		EndTimeEditText.setFocusable(true);
		EndTimeEditText.setFocusableInTouchMode(true);
		
		
		final WheelView hours = (WheelView) findViewById(R.id.hour);
		hours.setAdapter(new NumericWheelAdapter(0, 23));
		hours.setLabel("点");
	
		final WheelView mins = (WheelView) findViewById(R.id.mins);
		mins.setAdapter(new NumericWheelAdapter(0, 59, "%02d"));
		mins.setLabel("分");
		mins.setCyclic(true);
	

		OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (!timeScrolled) {
					timeChanged = true;
					if (StartTimeEditText.isFocused()) {
						GlobalVariable.TargetStartTimeHour = hours.getCurrentItem();
						GlobalVariable.TargetStartTimeMins = mins.getCurrentItem();
					    StartTimeEditText.setText(GlobalVariable.TargetStartTimeHour + " : " + GlobalVariable.TargetStartTimeMins);
					} else if (EndTimeEditText.isFocused()) {
						GlobalVariable.TargetEndTimeHour = hours.getCurrentItem();
						GlobalVariable.TargetEndTimeMins = mins.getCurrentItem();
						EndTimeEditText.setText(GlobalVariable.TargetEndTimeHour + " : " + GlobalVariable.TargetEndTimeMins);
					}
					timeChanged = false;
				}
			}
		};

		hours.addChangingListener(wheelListener);
		mins.addChangingListener(wheelListener);

		OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
			@Override
			public void onScrollingStarted(WheelView wheel) {
				timeScrolled = true;
			}
			@Override
			public void onScrollingFinished(WheelView wheel) {
				timeScrolled = false;
				timeChanged = true;
				if (StartTimeEditText.isFocused()) {
					GlobalVariable.TargetStartTimeHour = hours.getCurrentItem();
					GlobalVariable.TargetStartTimeMins = mins.getCurrentItem();
				    StartTimeEditText.setText(GlobalVariable.TargetStartTimeHour + " : " + GlobalVariable.TargetStartTimeMins);
				} else if (EndTimeEditText.isFocused()) {
					GlobalVariable.TargetEndTimeHour = hours.getCurrentItem();
					GlobalVariable.TargetEndTimeMins = mins.getCurrentItem();
					EndTimeEditText.setText(GlobalVariable.TargetEndTimeHour + " : " + GlobalVariable.TargetEndTimeMins);
				}
				timeChanged = false;
			}
		};
		
		hours.addScrollingListener(scrollListener);
		mins.addScrollingListener(scrollListener);	
		
		BackButton = (Button)findViewById(R.id.back_button);
		  BackButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(SetRefuseSlotTimeActivity.this, RefuseSlotActivity.class);
				startActivity(intent);
				SetRefuseSlotTimeActivity.this.finish();
			}
		});
		
		StoreButton = (Button)findViewById(R.id.store_button);
		StoreButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				StartTimeEditText.setFocusable(false);
				StartTimeEditText.setFocusableInTouchMode(false);
				EndTimeEditText.setFocusable(false);
				EndTimeEditText.setFocusableInTouchMode(false);
				Intent intent = new Intent(SetRefuseSlotTimeActivity.this, RefuseSlotActivity.class);
				startActivity(intent);
				SetRefuseSlotTimeActivity.this.finish();
			}
		});
	}
	
}
