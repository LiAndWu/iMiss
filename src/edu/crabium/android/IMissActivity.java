package edu.crabium.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class IMissActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        IMissData.init(this);
        startService(new Intent(this, IMissService.class));
    }
}                                      